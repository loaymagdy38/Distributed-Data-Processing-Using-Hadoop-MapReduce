import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class OrderDriver extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {

        if (args.length < 2) {
            System.err.println("Usage: OrderDriver <input> <output>");
            return 1;
        }

        String inputPath  = args[0];
        String outputPath = args[1];

        Configuration conf = getConf();
        FileSystem fs = FileSystem.get(conf);

        // Auto-delete output if exists
        Path outPath = new Path(outputPath);
        if (fs.exists(outPath)) {
            System.out.println("[OrderDriver] Deleting existing output: " + outputPath);
            fs.delete(outPath, true);
        }

        Job job = Job.getInstance(conf, "Task17 - Status-Based Partitioning");

        job.setJarByClass(OrderDriver.class);
        job.setMapperClass(OrderMapper.class);
        job.setReducerClass(OrderReducer.class);
        job.setPartitionerClass(OrderPartitioner.class);

        // 4 reducers - one per status
        job.setNumReduceTasks(4);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, outPath);

        System.out.println("[OrderDriver] Submitting job ...");
        boolean success = job.waitForCompletion(true);

        if (success) {
            System.out.println("\n=== Data Quality Counters ===");
            System.out.println("Malformed Lines : " +
                job.getCounters().findCounter("DataQuality", "MalformedLines").getValue());
            System.out.println("Invalid Amount  : " +
                job.getCounters().findCounter("DataQuality", "InvalidAmount").getValue());
            System.out.println("Negative Amount : " +
                job.getCounters().findCounter("DataQuality", "NegativeAmount").getValue());
            System.out.println("Unknown Status  : " +
                job.getCounters().findCounter("DataQuality", "UnknownStatus").getValue());
        }

        return success ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Configuration(), new OrderDriver(), args);
        System.exit(exitCode);
    }
}
