
import java.io.IOException; 
import java.util.Date; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.Text; 
import org.apache.hadoop.mapreduce.Reducer; 
public class PriceTypeReducer extends Reducer<Text,LongWritable,Text,Text> 
{ 
	@SuppressWarnings("deprecation") 
	@Override 
	protected void reduce(Text key, Iterable<LongWritable> values,Context context) 
	throws IOException, InterruptedException 
	{ 
		String line = key.toString(); 
		String [] words = line.split("\\|"); 
		Date startDate = new Date(words[3]); 
		long differ = (System.currentTimeMillis() - startDate.getTime()); 
		long diff_h=differ/(60*60*1000); 
		long days=diff_h/24; 
		if(days > 365) 
		{ 
			context.write(new Text(line), new Text(" ")); 
		} 
	} 
} 
