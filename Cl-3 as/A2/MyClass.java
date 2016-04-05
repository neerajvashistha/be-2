import java.util.Arrays;
import java.util.Arrays.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.naming.ldap.SortControl;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

public class MyClass {

	public static void main(String[] args) {
		
		try{
			File f=new File("myxml.xml");
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			DocumentBuilder db=dbf.newDocumentBuilder();
			Document doc=db.parse(f);
			doc.getDocumentElement().normalize();
			System.out.println(" Root Element: "+doc.getDocumentElement().getNodeName());
			NodeList nl=doc.getElementsByTagName("item");
	
			int num[]=new int[nl.getLength()];
			
			for(int i=0;i<nl.getLength();i++){
				Node n=nl.item(i);
				num[i]=Integer.parseInt(n.getTextContent());
			}
			int[] num2=num;
			System.out.println("File read!");
			long startTime=System.nanoTime();
			Thread t = new Thread(new QuickSortClass(num, 0, num.length-1));
			t.start();
			t.join();
			long endTime=System.nanoTime();
			System.out.println("Time: "+(endTime-startTime));
			for(int i:num){
				System.out.print(i+" ");
			}
			System.out.println();
			//startTime=System.nanoTime();
			//Arrays.sort(num2);
			//endTime=System.nanoTime();
			System.out.println("Time: "+(endTime-startTime));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}

