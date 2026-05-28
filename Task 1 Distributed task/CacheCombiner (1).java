package task1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * CacheCombiner  (local pre-aggregation on each Mapper node)
 *
 * Why a Combiner is valid here:
 *   The Reducer sums IntWritable values per Text key.
 *   Integer addition is both associative and commutative, so partial sums
 *   computed locally are safe to merge later in the Reducer.
 *
 * Benefit:
 *   Instead of shuffling every individual hoursWorked value across the
 *   network, each Mapper emits only one (departmentName, partialSum) pair
 *   per department it saw.  With 500 000 records and 10 departments this
 *   can cut shuffle volume by ~50 000×.
 *
 * Usage:
 *   CacheDriver enables this class with  job.setCombinerClass(CacheCombiner.class).
 *   To compare with / without combiner run CacheDriver twice and diff the
 *   job counters  (Map output records  vs  Combine output records).
 */
public class CacheCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {

    private final IntWritable result = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws java.io.IOException, InterruptedException {

        int partialSum = 0;

        for (IntWritable val : values) {
            partialSum += val.get();
        }

        result.set(partialSum);
        context.write(key, result);
    }
}
