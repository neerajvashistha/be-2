#include<iostream>
#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
using namespace std;

struct array
{
	int *arr;
	int first;
	int last;
};
int n;

void* quick(void *in)
{
	array* a=(array*)in;
	int i,j;
	pthread_t id=pthread_self();
	if(a->first < a->last)
	{
		int temp=0;
		int i=a->first;
		int j=a->last;
		int pivot=a->arr[a->first];
		while(i<j)
		{
			while(a->arr[i] <=pivot && i<j)
			i++;
			while(a->arr[j] > pivot && i<=j)
			j--;
			if(i<=j)
			{
				temp=a->arr[i];
				a->arr[i]=a->arr[j];
				a->arr[j]=temp;
			}
		}
		temp=a->arr[j];
		a->arr[j]=a->arr[a->first];
		a->arr[a->first]=temp;

		pthread_t threads[2];
		cout<<"Thread ID: "<<id<<" for pivot: "<<pivot<<endl;
		array a1,a2;
		a1.arr=new int[n];
		a2.arr=new int[n];
		a1.arr=a->arr;
		a2.arr=a->arr;
		a1.first=a->first;
		a1.last=j-1;
		a2.first=j+1;
		a2.last=a->last;
		pthread_create(&threads[0],NULL,&quick,(void *)&a1);
		pthread_create(&threads[1],NULL,&quick,(void *)&a2);
		pthread_join(threads[0],NULL);
		pthread_join(threads[1],NULL);
	}
}

int main()
{
	array a1;
	int n,i;
	cout<<"Enter size of array: ";
	cin>>n;
	a1.arr=new int[n];
	a1.first=0;
	a1.last=n-1;
	cout<<"Enter elements:"<<endl;
	for(i=0;i<n;i++)
	 cin>>a1.arr[i];
	quick(&a1);
	cout<<"Sorted array is:"<<endl;
	for(i=0;i<n;i++)
	 cout<<a1.arr[i]<<" ";
	cout<<endl;
	return 0;
}
/**
*
cipher@blackfury-HP-eNVy:~/be-2$ g++ concQuick.cpp -pthread -o concQuick
cipher@blackfury-HP-eNVy:~/be-2$ ./concQuick 
Enter size of array: 10
Enter elements:
8
9
07
66
33
78
23
98
11
86
Thread ID: 140225192675200 for pivot: 8
Thread ID: 140225167542016 for pivot: 9
Thread ID: 140225159149312 for pivot: 66
Thread ID: 140225175934720 for pivot: 23
Thread ID: 140225150756608 for pivot: 98
Thread ID: 140225133971200 for pivot: 86
Sorted array is:
7 8 9 11 23 33 66 78 86 98 
*
**/