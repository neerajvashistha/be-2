#include<stdio.h>
#include<mpi.h>
#include<stdlib.h>

struct BSTNode
{
    int data;
    struct BSTNode *left;
    struct BSTNode *right;
};

//Inserting element in BST
struct BSTNode *Insert(struct BSTNode *root, int data)
{
    if(root == NULL)
    {
        root = (struct BSTNode *) malloc (sizeof(struct BSTNode));
    
        if(root == NULL)
        {
            printf("Memory Error");
            return;
        }
        else
        {
            root -> data = data;
            root -> left = root -> right = NULL;
        }
    }
    else
    {
        if(data < root -> data)
            root -> left = Insert(root -> left, data);
        else if(data > root -> data)
            root -> right = Insert(root -> right, data);
    }
    return root;
}
//int p=0
//Inorder
void inorder(struct BSTNode *root,int *arr){
     if(root){
          inorder(root -> left,arr);
         printf("%d\t", root -> data);
  //        arr[p] = root -> data;
    //      p++;
          inorder(root -> right,arr);
     }
}

int main(int argc,char *argv[])
{

    int a[10] = {1,6,8,3,5,2,4,9,7,0};
    int i,rank,size,b[10],search;
    
    printf("\n Insert the key element to be searched : ");
    scanf("%d",&search);
    printf("\n You entered : %d\n",search);
    
    MPI_Request request;
    MPI_Status status;
    
  /*  int flag;
    int flag1=0;
    int flag2=0;
    */
    MPI_Init(&argc,&argv);
	MPI_Comm_rank(MPI_COMM_WORLD,&rank);
	MPI_Comm_size(MPI_COMM_WORLD,&size);
	
	MPI_Scatter(&a,5,MPI_INT,&b,5,MPI_INT,0,MPI_COMM_WORLD);
	
	if(rank == 0)
	{
	   
	    struct BSTNode *root_1 = NULL;
	    for(i=0;i<5;i++)
	    {
	         root_1=Insert(root_1, b[i]);
	    }
	    if (root_1 != NULL) 
	    {
	         printf("\nInorder at rank-%d processor:\t",rank);
            inorder(root_1,b);          
        }
        int flag=0;
        int flag1=0;
        MPI_Irecv(&flag,1,MPI_INT,1,3,MPI_COMM_WORLD,&request);
        while(root_1)
        {
            if(flag ==1)
            {
                break;
            }
            if(search == root_1 -> data)
            {
                printf("\nkey %d found at rank-%d processor\n",search,rank); 
                flag1=1;
                MPI_Send(&flag1,1,MPI_INT,1,2,MPI_COMM_WORLD); 
                break;
            }
            else if(search > root_1 -> data)
                root_1 = root_1 -> right;
            else
                root_1 = root_1 -> left;
        }
        MPI_Send(&flag1,1,MPI_INT,1,2,MPI_COMM_WORLD); 
        MPI_Wait(&request,&status);
        if(flag ==0 && flag1 ==0)
        {
            printf("\nKey %d not found\n",search);
        }
	} 
	
	if(rank == 1)
	{
	    struct BSTNode *root_2 = NULL;
	    for(i=0;i<5;i++)
	    {
	        root_2=Insert(root_2, b[i]);
	    }
	    if (root_2 != NULL) 
	    {
	        printf("\nInorder at rank-%d processor:\t",rank);
             inorder(root_2,b);          
        }
        int flag=0;
        int flag1=0;
        MPI_Irecv(&flag,1,MPI_INT,0,2,MPI_COMM_WORLD,&request);
        while(root_2)
        {
            if(flag ==1)
            {
                break;
            }
            if(search == root_2 -> data)
            {
                printf("\nkey %d found at rank-%d processor\n",search,rank);
                flag1=1;
                MPI_Send(&flag1,1,MPI_INT,0,3,MPI_COMM_WORLD);
                break;
            }
            else if(search > root_2 -> data)
                root_2 = root_2 -> right;
            else
                root_2 = root_2 -> left;  
        }     
        MPI_Send(&flag1,1,MPI_INT,0,3,MPI_COMM_WORLD);
	}
	
	MPI_Finalize();
	return 0;  
}

/*********Output*************
student@Student-OptiPlex-301:~/Documents$ vim bst_mpi.c
student@Student-OptiPlex-301:~/Documents$ mpicc bst__mpi.c
student@Student-OptiPlex-301:~/Documents$ mpiexec -n 2 -f machines.txt ./a.out 

 Insert the key element to be searched : 4
You entered : 4

Inorder at rank-0 processor:	1	3	5	6	8	
Inorder at rank-1 processor:	0	2	4	7	9	

key 4 found at rank-1 processor
student@Student-OptiPlex-301:~/Documents$
student@Student-OptiPlex-301:~/Documents$ mpicc bst__mpi.c
student@Student-OptiPlex-301:~/Documents$ mpiexec -n 2 -f machines.txt ./a.out 

 Insert the key element to be searched : 6
You entered : 6

Inorder at rank-0 processor:	1	3	5	6	8	
Inorder at rank-1 processor:	0	2	4	7	9	

key 6 found at rank-0 processor
student@Student-OptiPlex-301:~/Documents$
*/
