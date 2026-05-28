import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class OrderMapper extends Mapper<LongWritable, Text, Text, Text> {

    private final Text outKey   = new Text();
    private final Text outValue = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();
        if (line.isEmpty()) return;

        String[] parts = line.split(",", -1);

        if (parts.length < 5) {
            System.err.println("[OrderMapper] WARNING - malformed line: " + line);
            context.getCounter("DataQuality", "MalformedLines").increment(1);
            return;
        }

        String orderId   = parts[0].trim();
        String status    = parts[1].trim();
        String amountStr = parts[2].trim();

        // Validate amount
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            System.err.println("[OrderMapper] WARNING - invalid amount: " + amountStr);
            context.getCounter("DataQuality", "InvalidAmount").increment(1);
            return;
        }

        // Validate amount is positive
        if (amount <= 0) {
            System.err.println("[OrderMapper] WARNING - negative amount: " + amount);
            context.getCounter("DataQuality", "NegativeAmount").increment(1);
            return;
        }

        // Validate status
        if (!status.equals("Pending") && !status.equals("Completed")
                && !status.equals("Cancelled") && !status.equals("Processing")) {
            System.err.println("[OrderMapper] WARNING - unknown status: " + status);
            context.getCounter("DataQuality", "UnknownStatus").increment(1);
            return;
        }

        outKey.set(status);
        outValue.set(orderId + "," + amount);
        context.write(outKey, outValue);
    }
}
