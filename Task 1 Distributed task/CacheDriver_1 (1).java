package task1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;

/**
 * CacheDriver
 *
 * Usage (with Combiner – default):
 *   hadoop jar task1.jar task1.CacheDriver \
 *       hdfs:///user/cloudera/dept_lookup.txt \
 *       hdfs:///user/cloudera/input/employee_activity.txt \
 *       hdfs:///user/cloudera/output/task1_with_combiner
 *
 * Usage (without Combiner – pass flag):
 *   hadoop jar task1.jar task1.CacheDriver \
 *       hdfs:///user/cloudera/dept_lookup.txt \
 *       hdfs:///user/cloudera/input/employee_activity.txt \
 *       hdfs:///user/cloudera/output/task1_no_combiner \
 *       --no-combiner
 *
 * Arguments:
 *   args[0]  HDFS path to dept_lookup.txt   (Distributed Cache file)
 *   args[1]  HDFS path to input directory / file
 *   args[2]  HDFS path to output directory  (auto-deleted if exists)
 *   args[3]  (optional) --no-combiner
 */
public class CacheDriver extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {

        // ── Argument validation ───────────────────────────────────────────
        if (args.length < 3) {
            System.err.println("Usage: CacheDriver <cache-file> <input> <output> [--no-combiner]");
            return 1;
        }

        String cachePath  = args[0];
        String inputPath  = args[1];
        String outputPath = args[2];
        boolean useCombiner = !(args.length >= 4 && args[3].equalsIgnoreCase("--no-combiner"));

        Configuration conf = getConf();
        FileSystem fs = FileSystem.get(conf);

        // ── Auto-delete existing output ───────────────────────────────────
        Path outPath = new Path(outputPath);
        if (fs.exists(outPath)) {
            System.out.println("[CacheDriver] Output path exists – deleting: " + outputPath);
            fs.delete(outPath, true);
        }

        // ── Job configuration ─────────────────────────────────────────────
        Job job = Job.getInstance(conf, "Task1 – Employee Department Enrichment"
                + (useCombiner ? " (with combiner)" : " (no combiner)"));

        job.setJarByClass(CacheDriver.class);

        // Classes
        job.setMapperClass(CacheMapper.class);
        job.setReducerClass(CacheReducer.class);

        if (useCombiner) {
            job.setCombinerClass(CacheCombiner.class);
            System.out.println("[CacheDriver] Combiner ENABLED  (CacheCombiner)");
        } else {
            System.out.println("[CacheDriver] Combiner DISABLED");
        }

        // Single reducer – all departments go to one output file
        job.setNumReduceTasks(1);

        // Key / Value types
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Input / Output formats
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        // Paths
        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, outPath);

        // ── Distributed Cache ─────────────────────────────────────────────
        // Add the small lookup file so every Mapper can access it locally.
        job.addCacheFile(new URI(cachePath + "#dept_lookup.txt"));
        System.out.println("[CacheDriver] Added to Distributed Cache: " + cachePath);

        // ── Submit & wait ─────────────────────────────────────────────────
        System.out.println("[CacheDriver] Submitting job …");
        boolean success = job.waitForCompletion(true);

        if (success) {
            // Print key counters for the report
            System.out.println("\n=== Data Quality Counters ===");
            System.out.println("Malformed Lines  : "
                    + job.getCounters().findCounter("DataQuality", "MalformedLines").getValue());
            System.out.println("Invalid Hours    : "
                    + job.getCounters().findCounter("DataQuality", "InvalidHours").getValue());
            System.out.println("Unknown Employees: "
                    + job.getCounters().findCounter("DataQuality", "UnknownEmployee").getValue());
        }

        return success ? 0 : 1;
    }

    // ── Entry point ───────────────────────────────────────────────────────────
    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Configuration(), new CacheDriver(), args);
        System.exit(exitCode);
    }
}
