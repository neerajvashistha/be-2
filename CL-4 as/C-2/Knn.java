import java.io.*;
import java.util.*;
class Knn
{
	ArrayList<String> transactions = new ArrayList<String>();
	ArrayList<Double> transCount = new ArrayList<Double>();
	String testData;
	int count,k;
	void print()
	{
		for(int i=0;i<k;i++)
		{
			int index=transCount.indexOf(Collections.min(transCount));
			System.out.println(transactions.get(i));
			transactions.remove(index);
			transCount.remove(index);
		}
	}
	void operate()
	{
		String tData[]=testData.split(" ");
		for(String input:transactions)
		{
			double cost=0;
			String train[]=input.split(" ");
			for(int i=0;i<tData.length;i++)
			{
				try
				{
					cost+=Math.abs(Integer.parseInt(train[i])-Integer.parseInt(tData[i]));
				}
				catch(NumberFormatException e)
				{
					if(train[i].equals(tData[i]))
					cost+=1;
				}
			}
			cost/=tData.length;
			transCount.add(cost);
		}
	}
	void input()throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//Input of training data can also be taken from file in case of large input
		/*
		BufferefReader fr = new Bufferedreader(new FileReader(filename));
		String input;
		while((input=fr.readLine())!=null){
		transactions.add(input);
		count++;
	}
	And replace lines below, directly take testing data.
	*/
		System.out.println("Enter No of transactions");
		count=Integer.parseInt(br.readLine());
		System.out.println("Enter the training data in (FirstYearMarks-SecondYearMarks-ThirdYearMarks-CompanyAssigned)");
		for(int i=0;i<count;i++)
		{
			transactions.add(br.readLine());
		}
		System.out.println("Enter the testing data in (FirstYearMarks-SecondYearMarks-ThirdYearMarks-enter)");
		testData=br.readLine();
		System.out.println("Enter the value of k");
		k=Integer.parseInt(br.readLine());
	}
	public static void main(String []args)throws Exception
	{
		Knn knn=new Knn();
		knn.input();
		knn.operate();
		knn.print();
	}
}

/*
Enter No of transactions
5
Enter the training data in (FirstYearMarks-SecondYearMarks-ThirdYearMarks-CompanyAssigned)
890 980 850 Accenture
1040 1030 990 Accenture
1033 1034 1017 TechMahindra
890 870 1015 Infosys
950 1000 990 Congnizant
Enter the testing data in (FirstYearMarks-SecondYearMarks-ThirdYearMarks-enter)
980 990 1080
Enter the value of k
3
890 980 850 Accenture
1040 1030 990 Accenture
890 870 1015 Infosys
*/