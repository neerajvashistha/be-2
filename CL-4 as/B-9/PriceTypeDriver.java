
import java.io.IOException; 

import org.apache.hadoop.conf.Configuration; 
import org.apache.hadoop.fs.Path; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.Text; 
import org.apache.hadoop.mapreduce.Job; 
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; 
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat; 
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat; 
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat; 
import com.pricetype.mapper.PriceTypeMapper; 
import com.pricetype.reducer.PriceTypeReducer; 

public class PriceTypeDriver 
{ 
	public static void main (String[] args) throws IOException, 
	ClassNotFoundException, InterruptedException 
	{ 
		Configuration conf = new Configuration(); 
		Job j=new Job(conf); 
		j.setJarByClass(PriceTypeDriver.class); 
		j.setMapperClass(PriceTypeMapper.class); 
		j.setReducerClass(PriceTypeReducer.class); 
		j.setMapOutputKeyClass(Text.class); 

		j.setMapOutputValueClass(LongWritable.class); 
		j.setOutputKeyClass(Text.class); 
		j.setOutputValueClass(LongWritable.class); 
		j.setInputFormatClass(TextInputFormat.class); 
		j.setOutputFormatClass(TextOutputFormat.class); 
		Path input = new Path(args[0]); 
		Path output = new Path(args[1]); 
		FileInputFormat.addInputPath(j, input); 
		FileOutputFormat.setOutputPath(j, output);  
		boolean isJobRunning = j.waitForCompletion(true); 
		System.exit(isJobRunning ? 0 : 1); 
	} 
} 

