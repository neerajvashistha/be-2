import java.io.File;
import java.util.Arrays;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 



import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class QuickSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {0,0,0,0,0};
		readFromXML(arr);
		System.out.println("Read data from xml" + Arrays.toString(arr));
		int low = 0;
		int high = arr.length - 1;
		quicksort(arr, low, high);
		System.out.println("Array after sorting" + Arrays.toString(arr));
		
	}
	
	private static void quicksort(int[] arr, int low, int high){
		// TODO Auto-generated method stub
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		// pick the pivot
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];
 
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j)//joparyant i ha less ahe j peksha or j ha greter ahe i peksha
			{
				while (arr[i] < pivot){
					i++;
				}
 
				while (arr[j] > pivot){
					j--;
				}
 
			if (i <= j){
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (low < j)
			quicksort(arr, low, j);
 
		if (high > i)
			quicksort(arr, i, high);
	}
	
	public static void readFromXML(int[] arr)
	{
		try {

				File fXmlFile = new File("input.xml");//1

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();//2				
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();//3
				Document doc = dBuilder.parse(fXmlFile);//4
				doc.getDocumentElement().normalize();//5
				NodeList nList = doc.getElementsByTagName("number");//6
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						arr[temp] = Integer.parseInt(eElement.getElementsByTagName("value").item(0).getTextContent());
					}
				}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
}
/*
cipher@blackfury-HP-eNVy:~/be-2/A2$ javac QuickSort.java

cipher@blackfury-HP-eNVy:~/be-2/A2$ java QuickSort
Read data from xml[88, 10, 34, 67, 56]
Array after sorting[10, 34, 56, 67, 88]

cipher@blackfury-HP-eNVy:~/be-2/A2$ 
*/