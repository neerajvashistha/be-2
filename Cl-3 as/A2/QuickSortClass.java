
public class QuickSortClass implements Runnable {

	int a[];
	int start,end;
	int pivot;
	final int thrsholdSize=10;

	public QuickSortClass(int a[],int start,int end)
	{
		this.start=start;
		this.end=end;
		this.a=a;
	}
	
	void sort(int a[],int start,int end) throws InterruptedException{

		int i=start;
		int j=end;
		int pivot=a[start];

		while(i<=j){
			while(a[i]<pivot){
				i++;
			}
			while(a[j]>pivot){
				j--;
			}
			if(i<=j){
					//swapping
				int t=a[i];
				a[i]=a[j];
				a[j]=t;
				
				i++;
				j--;
			}
			
			if(end>i&& start<j){
					sort(a, start, j);

					if(end-i>=thrsholdSize&&start-j>=thrsholdSize){
						
						Thread t=new Thread(new QuickSortClass(a, i, end));
						t.start();
						t.join();						
					}else{
						sort(a, i, end);
					}
			}
		}

	}
	
	@Override
	public void run() {
		try {
			sort(a,start,end);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
