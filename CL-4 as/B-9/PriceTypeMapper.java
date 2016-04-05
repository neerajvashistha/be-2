
import java.io.IOException; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.Text; 
import org.apache.hadoop.mapreduce.Mapper; 
public class PriceTypeMapper extends Mapper<LongWritable,Text,Text,LongWritable> 
{ 
	@Override 
	protected void map(LongWritable key, Text value, Context context)throws 
	IOException, InterruptedException 
	{ 
		LongWritable one = new LongWritable(1); 
		String line = value.toString(); 
		String [] words = line.split("\\|"); 
		if(words.length>=3) 
		{
			if(words[1].equals("R")) 
			{ 
				context.write(value,one); 
			} 
		} 
	} 
} 