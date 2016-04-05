#include<iostream>
#include<omp.h>
using namespace std;
int k=0;
class sort
{
	int a[20];
	int n;
public:
	void getdata();
	void Quicksort();
	void Quicksort(int low, int high);
	int partition(int low, int high);
	void putdata();
};
void sort::getdata()
{
	cout<<"Enter the no. of elements in array\t";
	cin>>n;
	cout<<"Enter the elements of array:"<<endl;
	for(int i=0;i<n;i++)
	{
	cin>>a[i];
	}
}
void sort::Quicksort()
{
Quicksort(0,n-1);
}

void sort::Quicksort(int low, int high)
{
if(low<high)
{
	int partn;
	partn=partition(low,high);

cout<<"\n\nThread Number: "<<k<<"  pivot element selected : "<<a[partn];
	#pragma omp parallel sections
	{
	 #pragma omp section
		{
		k=k+1;
		Quicksort(low, partn-1);
		}
	 #pragma omp section
		{
		k=k+1;
		Quicksort(partn+1, high);
		}
	}//pragma_omp Parallel_end
}

}
int sort::partition(int low ,int high)
{
	int pvt;
	pvt=a[high];
	int i;
	i=low-1;
	int j;
	for(j=low;j<high;j++)
	{
		if(a[j]<=pvt)
		{
			int tem=0;
			tem=a[j];
			a[j]=a[i+1];
			a[i+1]=tem;
			i=i+1;
		}
	}
	int te;
	te=a[high];
	a[high]=a[i+1];
	a[i+1]=te;
	return i+1;
}
void sort::putdata()
{
	cout<<endl<<"\nThe Array is:"<<endl;
	for(int i=0;i<n;i++)
	cout<<" "<<a[i];
}
int main()
{
	int n;
	sort s1;
	int ch;
do
{
		s1.getdata();
		s1.putdata();

		cout<<"\nUsing Quick Sort";
    double start = omp_get_wtime(); 
		s1.Quicksort();
    double end = omp_get_wtime(); 
		cout<<"\nThe Sorted  ";
		s1.putdata();
    cout<<"\nExcecution time : "<<end - start<<" seconds "; 

cout<<"Would you like to continue? (1/0 y/n)"<<endl;
cin>>ch;
}while(ch==1);
}


