**\[cloudera@quickstart \~]$ hdfs dfs -mkdir -p /user/cloudera/cache/task1**

**\[cloudera@quickstart \~]$ hdfs dfs -mkdir -p /user/cloudera/input/task1**

**\[cloudera@quickstart \~]$ hdfs dfs -put /home/cloudera/dept\_lookup.txt /user/cloudera/cache/task1/**

**\[cloudera@quickstart \~]$ hdfs dfs -put /home/cloudera/employee\_activity.txt /user/cloudera/input/task1/**

**\[cloudera@quickstart \~]$ hdfs dfs -ls /user/cloudera/cache/task1/**

**Found 1 items**

**-rw-r--r--   1 cloudera cloudera      69865 2026-05-04 01:19 /user/cloudera/cache/task1/dept\_lookup.txt**

**\[cloudera@quickstart \~]$ hdfs dfs -ls /user/cloudera/input/task1/**

**Found 1 items**

**-rw-r--r--   1 cloudera cloudera 1610612736 2026-05-04 01:19 /user/cloudera/input/task1/employee\_activity.txt**

**\[cloudera@quickstart \~]$ mkdir -p /home/cloudera/task1**

**\[cloudera@quickstart \~]$ cd /home/cloudera/task1**

**\[cloudera@quickstart task1]$ nano CacheMapper.java**

**\[cloudera@quickstart task1]$ nano CacheMapper.java**

**\[cloudera@quickstart task1]$ ls -lh /home/cloudera/task1/**

**total 4.0K**

**-rw-rw-r-- 1 cloudera cloudera 3.5K May  4 02:46 CacheMapper.java**

**\[cloudera@quickstart task1]$** 

**\[cloudera@quickstart task1]$ nano CacheCombiner.java**

**\[cloudera@quickstart task1]$ ls -lh /home/cloudera/task1/**

**total 8.0K**

**-rw-rw-r-- 1 cloudera cloudera  634 May  4 02:56 CacheCombiner.java**

**-rw-rw-r-- 1 cloudera cloudera 3.5K May  4 02:46 CacheMapper.java**

**\[cloudera@quickstart task1]$ nano CacheCombiner.java**

**\[cloudera@quickstart task1]$ nano CacheReducer.java**

**\[cloudera@quickstart task1]$ ls -lh /home/cloudera/task1/**

**total 12K**

**-rw-rw-r-- 1 cloudera cloudera  634 May  4 02:56 CacheCombiner.java**

**-rw-rw-r-- 1 cloudera cloudera 3.5K May  4 02:46 CacheMapper.java**

**-rw-rw-r-- 1 cloudera cloudera  745 May  4 03:00 CacheReducer.java**

**\[cloudera@quickstart task1]$ nano CacheDriver.java**

**\[cloudera@quickstart task1]$ ls -lh /home/cloudera/task1/**

**total 16K**

**-rw-rw-r-- 1 cloudera cloudera  618 May  4 03:24 CacheCombiner.java**

**-rw-rw-r-- 1 cloudera cloudera 3.4K May  4 03:32 CacheDriver.java**

**-rw-rw-r-- 1 cloudera cloudera 3.5K May  4 03:24 CacheMapper.java**

**-rw-rw-r-- 1 cloudera cloudera  729 May  4 03:25 CacheReducer.java**

**\[cloudera@quickstart task1]$ mkdir out**

**\[cloudera@quickstart task1]$ javac -classpath `hadoop classpath` -d out \*.java**

**\[cloudera@quickstart task1]$ ls out**

**CacheCombiner.class  CacheDriver.class  CacheMapper.class  CacheReducer.class**

**\[cloudera@quickstart task1]$ jar -cvf task1.jar -C out .**

**added manifest**

**adding: CacheReducer.class(in = 2118) (out= 931)(deflated 56%)**

**adding: CacheDriver.class(in = 4134) (out= 2001)(deflated 51%)**

**adding: CacheMapper.class(in = 4989) (out= 2357)(deflated 52%)**

**adding: CacheCombiner.class(in = 1694) (out= 716)(deflated 57%)**

**\[cloudera@quickstart task1]$ ls**

**CacheCombiner.java  CacheDriver.java  CacheMapper.java  CacheReducer.java  out  task1.jar**

**\[cloudera@quickstart task1]$ ls out**

**CacheCombiner.class  CacheDriver.class  CacheMapper.class  CacheReducer.class**

**\[cloudera@quickstart task1]$ jar -cvf task1.jar -C out .**

**added manifest**

**adding: CacheReducer.class(in = 2118) (out= 931)(deflated 56%)**

**adding: CacheDriver.class(in = 4134) (out= 2001)(deflated 51%)**

**adding: CacheMapper.class(in = 4989) (out= 2357)(deflated 52%)**

**adding: CacheCombiner.class(in = 1694) (out= 716)(deflated 57%)**

**\[cloudera@quickstart task1]$ hadoop jar task1.jar CacheDriver hdfs:///user/cloudera/cache/task1/dept\_lookup.txt hdfs:///user/cloudera/input/task1/employee\_activity.txt hdfs:///user/cloudera/output/task1\_with\_combiner**

**\[CacheDriver] Combiner ENABLED**

**\[CacheDriver] Added to Distributed Cache: hdfs:///user/cloudera/cache/task1/dept\_lookup.txt**

**\[CacheDriver] Submitting job ...**

**26/05/04 04:01:59 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032**

**26/05/04 04:02:00 INFO input.FileInputFormat: Total input paths to process : 1**

**26/05/04 04:02:01 INFO mapreduce.JobSubmitter: number of splits:12**

**26/05/04 04:02:01 INFO mapreduce.JobSubmitter: Submitting tokens for job: job\_1777824810916\_0001**

**26/05/04 04:02:01 INFO impl.YarnClientImpl: Submitted application application\_1777824810916\_0001**

**26/05/04 04:02:01 INFO mapreduce.Job: The url to track the job: http://quickstart.cloudera:8088/proxy/application\_1777824810916\_0001/**

**26/05/04 04:02:01 INFO mapreduce.Job: Running job: job\_1777824810916\_0001**

**26/05/04 04:02:10 INFO mapreduce.Job: Job job\_1777824810916\_0001 running in uber mode : false**

**26/05/04 04:02:10 INFO mapreduce.Job:  map 0% reduce 0%**

**26/05/04 04:02:26 INFO mapreduce.Job:  map 1% reduce 0%**

**26/05/04 04:02:29 INFO mapreduce.Job:  map 2% reduce 0%**

**26/05/04 04:02:31 INFO mapreduce.Job:  map 3% reduce 0%**

**26/05/04 04:02:33 INFO mapreduce.Job:  map 4% reduce 0%**

**26/05/04 04:14:39 INFO mapreduce.Job:  map 1% reduce 0%**

**26/05/04 04:19:37 INFO mapreduce.Job:  map 0% reduce 0%**

**26/05/04 04:19:50 INFO mapreduce.Job:  map 1% reduce 0%**

**26/05/04 04:19:53 INFO mapreduce.Job:  map 2% reduce 0%**

**26/05/04 04:19:56 INFO mapreduce.Job:  map 3% reduce 0%**

**26/05/04 04:19:58 INFO mapreduce.Job:  map 4% reduce 0%**

**26/05/04 04:19:59 INFO mapreduce.Job:  map 5% reduce 0%**

**26/05/04 04:20:03 INFO mapreduce.Job:  map 6% reduce 0%**

**26/05/04 04:20:04 INFO mapreduce.Job:  map 7% reduce 0%**

**26/05/04 04:20:06 INFO mapreduce.Job:  map 8% reduce 0%**

**26/05/04 04:20:09 INFO mapreduce.Job:  map 9% reduce 0%**

**26/05/04 04:20:10 INFO mapreduce.Job:  map 10% reduce 0%**

**26/05/04 04:20:12 INFO mapreduce.Job:  map 12% reduce 0%**

**26/05/04 04:20:16 INFO mapreduce.Job:  map 14% reduce 0%**

**26/05/04 04:20:27 INFO mapreduce.Job:  map 15% reduce 0%**

**26/05/04 04:20:31 INFO mapreduce.Job:  map 16% reduce 0%**

**26/05/04 04:20:32 INFO mapreduce.Job:  map 17% reduce 0%**

**26/05/04 04:20:34 INFO mapreduce.Job:  map 18% reduce 0%**

**26/05/04 04:20:35 INFO mapreduce.Job:  map 19% reduce 0%**

**26/05/04 04:20:36 INFO mapreduce.Job:  map 20% reduce 0%**

**26/05/04 04:20:38 INFO mapreduce.Job:  map 22% reduce 0%**

**26/05/04 04:20:40 INFO mapreduce.Job:  map 23% reduce 0%**

**26/05/04 04:20:41 INFO mapreduce.Job:  map 25% reduce 0%**

**26/05/04 04:20:43 INFO mapreduce.Job:  map 26% reduce 0%**

**26/05/04 04:20:44 INFO mapreduce.Job:  map 28% reduce 0%**

**26/05/04 04:20:45 INFO mapreduce.Job:  map 29% reduce 0%**

**26/05/04 04:20:46 INFO mapreduce.Job:  map 30% reduce 0%**

**26/05/04 04:20:47 INFO mapreduce.Job:  map 40% reduce 0%**

**26/05/04 04:20:49 INFO mapreduce.Job:  map 50% reduce 0%**

**26/05/04 04:21:08 INFO mapreduce.Job:  map 50% reduce 17%**

**26/05/04 04:21:10 INFO mapreduce.Job:  map 51% reduce 17%**

**26/05/04 04:21:11 INFO mapreduce.Job:  map 56% reduce 17%**

**26/05/04 04:21:13 INFO mapreduce.Job:  map 57% reduce 17%**

**26/05/04 04:21:14 INFO mapreduce.Job:  map 60% reduce 17%**

**26/05/04 04:21:17 INFO mapreduce.Job:  map 61% reduce 17%**

**26/05/04 04:21:18 INFO mapreduce.Job:  map 64% reduce 17%**

**26/05/04 04:21:21 INFO mapreduce.Job:  map 66% reduce 17%**

**26/05/04 04:21:23 INFO mapreduce.Job:  map 67% reduce 17%**

**26/05/04 04:21:24 INFO mapreduce.Job:  map 70% reduce 17%**

**26/05/04 04:21:26 INFO mapreduce.Job:  map 71% reduce 17%**

**26/05/04 04:21:27 INFO mapreduce.Job:  map 75% reduce 17%**

**26/05/04 04:21:29 INFO mapreduce.Job:  map 76% reduce 17%**

**26/05/04 04:21:30 INFO mapreduce.Job:  map 92% reduce 17%**

**26/05/04 04:21:33 INFO mapreduce.Job:  map 92% reduce 31%**

**26/05/04 04:21:39 INFO mapreduce.Job:  map 95% reduce 31%**

**26/05/04 04:21:44 INFO mapreduce.Job:  map 100% reduce 31%**

**26/05/04 04:21:45 INFO mapreduce.Job:  map 100% reduce 100%**

**26/05/04 04:21:46 INFO mapreduce.Job: Job job\_1777824810916\_0001 completed successfully**

**26/05/04 04:21:46 INFO mapreduce.Job: Counters: 51**

&#x09;**File System Counters**

&#x09;	**FILE: Number of bytes read=3750**

&#x09;	**FILE: Number of bytes written=1543313**

&#x09;	**FILE: Number of read operations=0**

&#x09;	**FILE: Number of large read operations=0**

&#x09;	**FILE: Number of write operations=0**

&#x09;	**HDFS: Number of bytes read=1610659520**

&#x09;	**HDFS: Number of bytes written=93**

&#x09;	**HDFS: Number of read operations=39**

&#x09;	**HDFS: Number of large read operations=0**

&#x09;	**HDFS: Number of write operations=2**

&#x09;**Job Counters** 

&#x09;	**Launched map tasks=12**

&#x09;	**Launched reduce tasks=1**

&#x09;	**Data-local map tasks=12**

&#x09;	**Total time spent by all maps in occupied slots (ms)=604760**

&#x09;	**Total time spent by all reduces in occupied slots (ms)=56276**

&#x09;	**Total time spent by all map tasks (ms)=604760**

&#x09;	**Total time spent by all reduce tasks (ms)=56276**

&#x09;	**Total vcore-seconds taken by all map tasks=604760**

&#x09;	**Total vcore-seconds taken by all reduce tasks=56276**

&#x09;	**Total megabyte-seconds taken by all map tasks=619274240**

&#x09;	**Total megabyte-seconds taken by all reduce tasks=57626624**

&#x09;**Map-Reduce Framework**

&#x09;	**Map input records=75731437**

&#x09;	**Map output records=74973845**

&#x09;	**Map output bytes=785527188**

&#x09;	**Map output materialized bytes=1872**

&#x09;	**Input split bytes=1728**

&#x09;	**Combine input records=74973845**

&#x09;	**Combine output records=144**

&#x09;	**Reduce input groups=6**

&#x09;	**Reduce shuffle bytes=1872**

&#x09;	**Reduce input records=144**

&#x09;	**Reduce output records=6**

&#x09;	**Spilled Records=432**

&#x09;	**Shuffled Maps =12**

&#x09;	**Failed Shuffles=0**

&#x09;	**Merged Map outputs=12**

&#x09;	**GC time elapsed (ms)=22773**

&#x09;	**CPU time spent (ms)=135310**

&#x09;	**Physical memory (bytes) snapshot=5457760256**

&#x09;	**Virtual memory (bytes) snapshot=20243730432**

&#x09;	**Total committed heap usage (bytes)=4599054336**

&#x09;**DataQuality**

&#x09;	**InvalidHours=757592**

&#x09;	**UnknownEmployee=12491986**

&#x09;**Shuffle Errors**

&#x09;	**BAD\_ID=0**

&#x09;	**CONNECTION=0**

&#x09;	**IO\_ERROR=0**

&#x09;	**WRONG\_LENGTH=0**

&#x09;	**WRONG\_MAP=0**

&#x09;	**WRONG\_REDUCE=0**

&#x09;**File Input Format Counters** 

&#x09;	**Bytes Read=1610657792**

&#x09;**File Output Format Counters** 

&#x09;	**Bytes Written=93**



**=== Data Quality Counters ===**

**Malformed Lines  : 0**

**Invalid Hours    : 757592**

**Unknown Employees: 12491986**

**\[cloudera@quickstart task1]$ hdfs dfs -cat /user/cloudera/output/task1\_with\_combiner/part-r-00000**

**Finance	81727290**

**HR	80848145**

**IT	84261985**

**Marketing	80235274**

**Sales	79000104**

**UNKNOWN\_	81191941**











**\[cloudera@quickstart task1]$ hadoop jar task1.jar CacheDriver hdfs:///user/cloudera/cache/task1/dept\_lookup.txt hdfs:///user/cloudera/input/task1/employee\_activity.txt hdfs:///user/cloudera/output/task1\_no\_combiner --no-combiner**

**\[CacheDriver] Combiner DISABLED**

**\[CacheDriver] Added to Distributed Cache: hdfs:///user/cloudera/cache/task1/dept\_lookup.txt**

**\[CacheDriver] Submitting job ...**

**26/05/04 04:29:29 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032**

**26/05/04 04:29:30 INFO input.FileInputFormat: Total input paths to process : 1**

**26/05/04 04:29:30 INFO mapreduce.JobSubmitter: number of splits:12**

**26/05/04 04:29:31 INFO mapreduce.JobSubmitter: Submitting tokens for job: job\_1777824810916\_0002**

**26/05/04 04:29:31 INFO impl.YarnClientImpl: Submitted application application\_1777824810916\_0002**

**26/05/04 04:29:31 INFO mapreduce.Job: The url to track the job: http://quickstart.cloudera:8088/proxy/application\_1777824810916\_0002/**

**26/05/04 04:29:31 INFO mapreduce.Job: Running job: job\_1777824810916\_0002**

**26/05/04 04:30:38 INFO mapreduce.Job: Job job\_1777824810916\_0002 running in uber mode : false**

**26/05/04 04:30:38 INFO mapreduce.Job:  map 0% reduce 0%**

**26/05/04 04:30:51 INFO mapreduce.Job:  map 1% reduce 0%**

**26/05/04 04:30:56 INFO mapreduce.Job:  map 2% reduce 0%**

**26/05/04 04:30:57 INFO mapreduce.Job:  map 3% reduce 0%**

**26/05/04 04:30:59 INFO mapreduce.Job:  map 6% reduce 0%**

**26/05/04 04:31:00 INFO mapreduce.Job:  map 7% reduce 0%**

**26/05/04 04:31:02 INFO mapreduce.Job:  map 10% reduce 0%**

**26/05/04 04:31:03 INFO mapreduce.Job:  map 11% reduce 0%**

**26/05/04 04:31:05 INFO mapreduce.Job:  map 13% reduce 0%**

**26/05/04 04:31:06 INFO mapreduce.Job:  map 14% reduce 0%**

**26/05/04 04:31:08 INFO mapreduce.Job:  map 16% reduce 0%**

**26/05/04 04:31:09 INFO mapreduce.Job:  map 17% reduce 0%**

**26/05/04 04:31:11 INFO mapreduce.Job:  map 18% reduce 0%**

**26/05/04 04:31:13 INFO mapreduce.Job:  map 19% reduce 0%**

**26/05/04 04:31:14 INFO mapreduce.Job:  map 20% reduce 0%**

**26/05/04 04:31:16 INFO mapreduce.Job:  map 21% reduce 0%**

**26/05/04 04:31:17 INFO mapreduce.Job:  map 22% reduce 0%**

**26/05/04 04:31:20 INFO mapreduce.Job:  map 24% reduce 0%**

**26/05/04 04:31:21 INFO mapreduce.Job:  map 26% reduce 0%**

**26/05/04 04:31:23 INFO mapreduce.Job:  map 28% reduce 0%**

**26/05/04 04:31:24 INFO mapreduce.Job:  map 30% reduce 0%**

**26/05/04 04:31:26 INFO mapreduce.Job:  map 32% reduce 0%**

**26/05/04 04:31:27 INFO mapreduce.Job:  map 33% reduce 0%**

**26/05/04 04:31:32 INFO mapreduce.Job:  map 34% reduce 0%**

**26/05/04 04:31:36 INFO mapreduce.Job:  map 35% reduce 0%**

**26/05/04 04:31:39 INFO mapreduce.Job:  map 37% reduce 0%**

**26/05/04 04:31:42 INFO mapreduce.Job:  map 38% reduce 0%**

**26/05/04 04:31:44 INFO mapreduce.Job:  map 39% reduce 0%**

**26/05/04 04:31:45 INFO mapreduce.Job:  map 40% reduce 0%**

**26/05/04 04:31:47 INFO mapreduce.Job:  map 41% reduce 0%**

**26/05/04 04:31:48 INFO mapreduce.Job:  map 42% reduce 0%**

**26/05/04 04:31:50 INFO mapreduce.Job:  map 43% reduce 0%**

**26/05/04 04:31:51 INFO mapreduce.Job:  map 44% reduce 0%**

**26/05/04 04:31:53 INFO mapreduce.Job:  map 45% reduce 0%**

**26/05/04 04:31:54 INFO mapreduce.Job:  map 46% reduce 0%**

**26/05/04 04:31:55 INFO mapreduce.Job:  map 47% reduce 0%**

**26/05/04 04:31:57 INFO mapreduce.Job:  map 48% reduce 0%**

**26/05/04 04:31:58 INFO mapreduce.Job:  map 49% reduce 0%**

**26/05/04 04:32:01 INFO mapreduce.Job:  map 50% reduce 0%**

**26/05/04 04:32:21 INFO mapreduce.Job:  map 51% reduce 0%**

**26/05/04 04:32:22 INFO mapreduce.Job:  map 52% reduce 3%**

**26/05/04 04:32:24 INFO mapreduce.Job:  map 53% reduce 3%**

**26/05/04 04:32:25 INFO mapreduce.Job:  map 54% reduce 3%**

**26/05/04 04:32:27 INFO mapreduce.Job:  map 55% reduce 3%**

**26/05/04 04:32:28 INFO mapreduce.Job:  map 57% reduce 6%**

**26/05/04 04:32:31 INFO mapreduce.Job:  map 58% reduce 6%**

**26/05/04 04:32:32 INFO mapreduce.Job:  map 59% reduce 6%**

**26/05/04 04:32:34 INFO mapreduce.Job:  map 60% reduce 6%**

**26/05/04 04:32:35 INFO mapreduce.Job:  map 61% reduce 8%**

**26/05/04 04:32:37 INFO mapreduce.Job:  map 62% reduce 8%**

**26/05/04 04:32:38 INFO mapreduce.Job:  map 64% reduce 8%**

**26/05/04 04:32:41 INFO mapreduce.Job:  map 66% reduce 8%**

**26/05/04 04:32:44 INFO mapreduce.Job:  map 67% reduce 11%**

**26/05/04 04:32:47 INFO mapreduce.Job:  map 67% reduce 14%**

**26/05/04 04:32:51 INFO mapreduce.Job:  map 67% reduce 17%**

**26/05/04 04:32:53 INFO mapreduce.Job:  map 70% reduce 17%**

**26/05/04 04:32:56 INFO mapreduce.Job:  map 74% reduce 17%**

**26/05/04 04:32:58 INFO mapreduce.Job:  map 75% reduce 17%**

**26/05/04 04:32:59 INFO mapreduce.Job:  map 77% reduce 17%**

**26/05/04 04:33:03 INFO mapreduce.Job:  map 78% reduce 17%**

**26/05/04 04:33:11 INFO mapreduce.Job:  map 79% reduce 17%**

**26/05/04 04:33:12 INFO mapreduce.Job:  map 80% reduce 17%**

**26/05/04 04:33:14 INFO mapreduce.Job:  map 81% reduce 17%**

**26/05/04 04:33:17 INFO mapreduce.Job:  map 82% reduce 17%**

**26/05/04 04:33:18 INFO mapreduce.Job:  map 83% reduce 17%**

**26/05/04 04:33:20 INFO mapreduce.Job:  map 84% reduce 17%**

**26/05/04 04:33:23 INFO mapreduce.Job:  map 85% reduce 17%**

**26/05/04 04:33:24 INFO mapreduce.Job:  map 86% reduce 17%**

**26/05/04 04:33:27 INFO mapreduce.Job:  map 87% reduce 17%**

**26/05/04 04:33:30 INFO mapreduce.Job:  map 88% reduce 17%**

**26/05/04 04:33:32 INFO mapreduce.Job:  map 89% reduce 17%**

**26/05/04 04:33:33 INFO mapreduce.Job:  map 90% reduce 17%**

**26/05/04 04:33:36 INFO mapreduce.Job:  map 91% reduce 17%**

**26/05/04 04:33:38 INFO mapreduce.Job:  map 92% reduce 17%**

**26/05/04 04:33:40 INFO mapreduce.Job:  map 92% reduce 19%**

**26/05/04 04:33:43 INFO mapreduce.Job:  map 92% reduce 25%**

**26/05/04 04:33:46 INFO mapreduce.Job:  map 92% reduce 28%**

**26/05/04 04:33:49 INFO mapreduce.Job:  map 92% reduce 31%**

**26/05/04 04:34:01 INFO mapreduce.Job:  map 95% reduce 31%**

**26/05/04 04:34:08 INFO mapreduce.Job:  map 97% reduce 31%**

**26/05/04 04:34:11 INFO mapreduce.Job:  map 99% reduce 31%**

**26/05/04 04:34:17 INFO mapreduce.Job:  map 100% reduce 34%**

**26/05/04 04:34:19 INFO mapreduce.Job:  map 100% reduce 67%**

**26/05/04 04:34:22 INFO mapreduce.Job:  map 100% reduce 68%**

**26/05/04 04:34:24 INFO mapreduce.Job:  map 100% reduce 71%**

**26/05/04 04:34:27 INFO mapreduce.Job:  map 100% reduce 73%**

**26/05/04 04:34:30 INFO mapreduce.Job:  map 100% reduce 75%**

**26/05/04 04:34:33 INFO mapreduce.Job:  map 100% reduce 77%**

**26/05/04 04:34:36 INFO mapreduce.Job:  map 100% reduce 79%**

**26/05/04 04:34:39 INFO mapreduce.Job:  map 100% reduce 82%**

**26/05/04 04:34:43 INFO mapreduce.Job:  map 100% reduce 85%**

**26/05/04 04:34:46 INFO mapreduce.Job:  map 100% reduce 88%**

**26/05/04 04:34:49 INFO mapreduce.Job:  map 100% reduce 90%**

**26/05/04 04:34:54 INFO mapreduce.Job:  map 100% reduce 93%**

**26/05/04 04:34:56 INFO mapreduce.Job:  map 100% reduce 97%**

**26/05/04 04:34:58 INFO mapreduce.Job:  map 100% reduce 100%**

**26/05/04 04:34:59 INFO mapreduce.Job: Job job\_1777824810916\_0002 completed successfully**

**26/05/04 04:34:59 INFO mapreduce.Job: Counters: 52**

&#x09;**File System Counters**

&#x09;	**FILE: Number of bytes read=1870949918**

&#x09;	**FILE: Number of bytes written=2807960544**

&#x09;	**FILE: Number of read operations=0**

&#x09;	**FILE: Number of large read operations=0**

&#x09;	**FILE: Number of write operations=0**

&#x09;	**HDFS: Number of bytes read=1610659520**

&#x09;	**HDFS: Number of bytes written=93**

&#x09;	**HDFS: Number of read operations=39**

&#x09;	**HDFS: Number of large read operations=0**

&#x09;	**HDFS: Number of write operations=2**

&#x09;**Job Counters** 

&#x09;	**Killed map tasks=1**

&#x09;	**Launched map tasks=13**

&#x09;	**Launched reduce tasks=1**

&#x09;	**Data-local map tasks=13**

&#x09;	**Total time spent by all maps in occupied slots (ms)=979611**

&#x09;	**Total time spent by all reduces in occupied slots (ms)=177088**

&#x09;	**Total time spent by all map tasks (ms)=979611**

&#x09;	**Total time spent by all reduce tasks (ms)=177088**

&#x09;	**Total vcore-seconds taken by all map tasks=979611**

&#x09;	**Total vcore-seconds taken by all reduce tasks=177088**

&#x09;	**Total megabyte-seconds taken by all map tasks=1003121664**

&#x09;	**Total megabyte-seconds taken by all reduce tasks=181338112**

&#x09;**Map-Reduce Framework**

&#x09;	**Map input records=75731437**

&#x09;	**Map output records=74973845**

&#x09;	**Map output bytes=785527188**

&#x09;	**Map output materialized bytes=935474950**

&#x09;	**Input split bytes=1728**

&#x09;	**Combine input records=0**

&#x09;	**Combine output records=0**

&#x09;	**Reduce input groups=6**

&#x09;	**Reduce shuffle bytes=935474950**

&#x09;	**Reduce input records=74973845**

&#x09;	**Reduce output records=6**

&#x09;	**Spilled Records=224921535**

&#x09;	**Shuffled Maps =12**

&#x09;	**Failed Shuffles=0**

&#x09;	**Merged Map outputs=12**

&#x09;	**GC time elapsed (ms)=10162**

&#x09;	**CPU time spent (ms)=318300**

&#x09;	**Physical memory (bytes) snapshot=6090723328**

&#x09;	**Virtual memory (bytes) snapshot=20308484096**

&#x09;	**Total committed heap usage (bytes)=5607784448**

&#x09;**DataQuality**

&#x09;	**InvalidHours=757592**

&#x09;	**UnknownEmployee=12491986**

&#x09;**Shuffle Errors**

&#x09;	**BAD\_ID=0**

&#x09;	**CONNECTION=0**

&#x09;	**IO\_ERROR=0**

&#x09;	**WRONG\_LENGTH=0**

&#x09;	**WRONG\_MAP=0**

&#x09;	**WRONG\_REDUCE=0**

&#x09;**File Input Format Counters** 

&#x09;	**Bytes Read=1610657792**

&#x09;**File Output Format Counters** 

&#x09;	**Bytes Written=93**



**=== Data Quality Counters ===**

**Malformed Lines  : 0**

**Invalid Hours    : 757592**

**Unknown Employees: 12491986**





**\[cloudera@quickstart task1]$ hdfs dfs -cat /user/cloudera/output/task1\_no\_combiner/part-r-00000**

**Finance	81727290**

**HR	80848145**

**IT	84261985**

**Marketing	80235274**

**Sales	79000104**

**UNKNOWN\_	81191941**

**\[cloudera@quickstart task1]$** 

&#x20;







