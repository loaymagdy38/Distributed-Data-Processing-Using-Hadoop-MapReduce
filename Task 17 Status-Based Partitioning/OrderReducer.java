import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class OrderReducer extends Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        String status    = key.toString();
        long   count     = 0;
        double total     = 0.0;
        double maxAmount = Double.MIN_VALUE;
        double minAmount = Double.MAX_VALUE;

        for (Text val : values) {
            String[] parts = val.toString().split(",", -1);
            if (parts.length < 2) continue;

            try {
                double amount = Double.parseDouble(parts[1].trim());
                total     += amount;
                count++;
                if (amount > maxAmount) maxAmount = amount;
                if (amount < minAmount) minAmount = amount;
            } catch (NumberFormatException e) {
                System.err.println("[OrderReducer] WARNING - unparseable amount: " + val);
            }
        }

        double avg = count > 0 ? total / count : 0.0;

        String output = String.format(
            "Count=%d | Total=%.2f | Avg=%.2f | Min=%.2f | Max=%.2f",
            count, total, avg, minAmount, maxAmount
        );

        result.set(output);
        context.write(key, result);

        System.out.println("[OrderReducer] " + status + " -> " + output);
    }
}
