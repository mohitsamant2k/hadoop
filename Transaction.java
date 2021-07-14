
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Transaction {
	
	public static void main(String [] args) throws Exception
	{
		Configuration configuration = new Configuration();
		Path inputFolder = new Path(args[0]);
		Path outputFolder = new Path(args[1]);
		
		Job job = new Job(configuration, "Transaction Segregation");
		job.setJarByClass(Transaction.class);
		job.setMapperClass(assignmentMapper.class);
		job.setReducerClass(assignmentReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		FileInputFormat.addInputPath(job, inputFolder);
		FileOutputFormat.setOutputPath(job, outputFolder);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
	public static class assignmentMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		
		public void map(LongWritable key, Text value, Context con) throws IOException, InterruptedException
		{
			String line = value.toString();
			String[] words = line.split(" ");
			long cnt = 0;
			for(int i = 1; i < words.length; i++)
			{
				int parsevalue = Integer.parseInt(words[i]);
				cnt += parsevalue;
			}
			con.write(new Text("class" + String.valueOf((words.length - 2)/10 + 1) + ","), new LongWritable(cnt));
		}
	}
	
	public static class assignmentReducer extends Reducer<Text, LongWritable, Text, LongWritable>
	{
		public void reduce(Text word, Iterable<LongWritable> values, Context con) throws IOException, InterruptedException
		{
			long sum = 0;
			for(LongWritable value : values)
			{
				sum += value.get();
			}
			con.write(word, new LongWritable(sum));
		}
	}
}
