package task1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * CacheReducer
 *
 * Receives:  (departmentName, [hoursWorked, hoursWorked, ...])
 * Emits:     (departmentName, totalHoursWorked)
 *
 * Works correctly both:
 *   • WITHOUT Combiner – receives individual hoursWorked integers.
 *   • WITH    Combiner – receives partial sums from CacheCombiner; the final
 *                        sum is still correct because addition is associative.
 */
public class CacheReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private final IntWritable totalHours = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws java.io.IOException, InterruptedException {

        int sum = 0;

        for (IntWritable val : values) {
            sum += val.get();
        }

        totalHours.set(sum);
        context.write(key, totalHours);

        System.out.println("[CacheReducer] Department: " + key.toString()
                + "  →  Total Hours: " + sum);
    }
}
