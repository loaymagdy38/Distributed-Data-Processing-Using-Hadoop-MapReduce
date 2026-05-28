import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class OrderPartitioner extends Partitioner<Text, Text> {

    @Override
    public int getPartition(Text key, Text value, int numPartitions) {
        String status = key.toString().trim();
        switch (status) {
            case "Pending":    return 0;
            case "Completed":  return 1;
            case "Cancelled":  return 2;
            case "Processing": return 3;
            default:           return 0;
        }
    }
}
