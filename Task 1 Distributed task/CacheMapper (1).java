package task1;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * CacheMapper
 *
 * Distributed-Cache pattern:
 *   setup()  → loads dept_lookup.txt into a HashMap (employeeId → departmentName)
 *   map()    → looks up each activity record and emits (departmentName, hoursWorked)
 *              If employeeId is not found, emits ("UNKNOWN_", hoursWorked).
 *              Skips malformed lines (missing fields, non-integer hoursWorked).
 */
public class CacheMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    // In-memory lookup: employeeId → departmentName
    private final Map<String, String> deptMap = new HashMap<>();

    private final Text        outKey   = new Text();
    private final IntWritable outValue = new IntWritable();

    // ------------------------------------------------------------------ setup
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        // The Distributed Cache places the file in the task's local working dir.
        // We iterate over every cached URI and load the one named "dept_lookup.txt".
        URI[] cacheFiles = context.getCacheFiles();

        if (cacheFiles == null || cacheFiles.length == 0) {
            throw new IOException("Distributed Cache is empty – dept_lookup.txt not found.");
        }

        for (URI uri : cacheFiles) {
            Path filePath = new Path(uri.getPath());
            String fileName = filePath.getName();

            if (fileName.equals("dept_lookup.txt")) {
                loadDeptLookup(fileName);   // local copy is just the base name
            }
        }

        System.out.println("[CacheMapper.setup] Loaded " + deptMap.size()
                + " department mappings.");
    }

    /**
     * Reads the local copy of dept_lookup.txt into deptMap.
     * Expected format per line:  employeeId,departmentName
     */
    private void loadDeptLookup(String localFileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(localFileName))) {
            String line;
            int lineNum = 0;

            while ((line = br.readLine()) != null) {
                lineNum++;
                line = line.trim();

                // Skip blank lines
                if (line.isEmpty()) continue;

                String[] parts = line.split(",", -1);

                if (parts.length < 2) {
                    System.err.println("[CacheMapper.setup] WARNING – malformed lookup line "
                            + lineNum + ": \"" + line + "\" – skipped.");
                    continue;
                }

                String empId    = parts[0].trim();
                String deptName = parts[1].trim();

                if (empId.isEmpty() || deptName.isEmpty()) {
                    System.err.println("[CacheMapper.setup] WARNING – empty field at line "
                            + lineNum + " – skipped.");
                    continue;
                }

                deptMap.put(empId, deptName);
            }
        }
    }

    // ------------------------------------------------------------------- map
    /**
     * Processes one activity record.
     * Expected format:  employeeId,hoursWorked,date
     *
     * Emit:  (departmentName | "UNKNOWN_", hoursWorked)
     * Skip lines whose hoursWorked field is non-integer (log a warning).
     */
    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        // Skip blank lines
        if (line.isEmpty()) return;

        String[] parts = line.split(",", -1);

        // Must have at least 3 fields: employeeId, hoursWorked, date
        if (parts.length < 3) {
            System.err.println("[CacheMapper.map] WARNING – malformed line (expected 3 fields): \""
                    + line + "\" – skipped.");
            context.getCounter("DataQuality", "MalformedLines").increment(1);
            return;
        }

        String empId        = parts[0].trim();
        String hoursStr     = parts[1].trim();
        // parts[2] is date – used for partitioning/context only, not needed here

        // ── Validate hoursWorked ──────────────────────────────────────────
        int hoursWorked;
        try {
            hoursWorked = Integer.parseInt(hoursStr);
        } catch (NumberFormatException e) {
            System.err.println("[CacheMapper.map] WARNING – non-integer hoursWorked \""
                    + hoursStr + "\" in line: \"" + line + "\" – skipped.");
            context.getCounter("DataQuality", "InvalidHours").increment(1);
            return;
        }

        // ── Look up department ────────────────────────────────────────────
        String dept = deptMap.getOrDefault(empId, "UNKNOWN_");

        if (dept.equals("UNKNOWN_")) {
            context.getCounter("DataQuality", "UnknownEmployee").increment(1);
        }

        // ── Emit ──────────────────────────────────────────────────────────
        outKey.set(dept);
        outValue.set(hoursWorked);
        context.write(outKey, outValue);
    }
}
