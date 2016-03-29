
#include<iostream>
#include"stdio.h"
using namespace std;

void Binary_Search(int arr[],int num,int first,int last)
{
   if(first>last)
   {
      cout<<"\nElement not Found...";
   }
   else
   {
      int mid;
      /*Calculate mid element*/
      mid=(first+last)/2;

      if(arr[mid]==num)
      {
	  cout<<"\nElement found at index:"<<mid+1;
      }
      else if(arr[mid]>num)
      {
	  Binary_Search(arr,num,first,mid-1);
      }
      else
      {
	  Binary_Search(arr,num,mid+1,last);
      }
   }
}

int main()
{
   int arr[100],beg,mid,end,num,i,j,n,temp;
   cout<<"\nEnter size of array:";
   cin>>n;

   cout<<"\nEnter Unsorted array:";
   for(i=0;i<n;i++)
    {
       cin>>arr[i];	
    }
    
   
   for(i=0;i<n;i++)             // Loop to sort elements
    {
    for(j=i+1;j<n;j++)
     {
      if(arr[i]>arr[j])
      {
         temp=arr[i];                      // swapping
         arr[i]=arr[j];
         arr[j]=temp;
      }
     }
    }
     cout<<"\nArray after sorting:";
     for(i=0;i<n;i++)
     {
    	cout<<arr[i]<<endl;
     }
     beg=0;
     end=n-1;
     cout<<"\nEnter a value to be search:";
     cin>>num;

     Binary_Search(arr,num,beg,end);
     return(0);
}


/* Output:

[exam2016@localhost ~]$ ./a.out

Enter size  of array:7

Enter Unsorted array:8
1
2
4
5
6
7

Array after sorting:1
2
4
5
6
7
8

Enter a value to be search:5

Element found at index:4[exam2016@localhost ~]$ ^C
[exam2016@localhost ~]$






*/
