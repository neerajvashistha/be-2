#include<stdio.h>
#include<mpi.h>

int main(int argc, char* argv[])
{
    int i,j;
    int m1,m2,m3,m4,m5,m6,m7;
    int rank,size;
    
    MPI_Request request;
    MPI_Status status;
    
    MPI_Init(&argc,&argv);
	MPI_Comm_rank(MPI_COMM_WORLD,&rank);
	MPI_Comm_size(MPI_COMM_WORLD,&size);
    if(rank == 0)
    {
          int a[2][2],b[2][2];
          printf("Enter the 4 elements of first matrix: ");
          for(i=0;i<2;i++)
              for(j=0;j<2;j++)
                   scanf("%d",&a[i][j]);

          printf("Enter the 4 elements of second matrix: ");
          for(i=0;i<2;i++)
              for(j=0;j<2;j++)
                   scanf("%d",&b[i][j]);

          printf("\nThe first matrix is\n");
          for(i=0;i<2;i++)
          {
              printf("\n");
              for(j=0;j<2;j++)
              {
                   printf("%d\t",a[i][j]);
              }
          }

          printf("\nThe second matrix is\n");
          for(i=0;i<2;i++)
          {
              printf("\n");
              for(j=0;j<2;j++)
              {
                   printf("%d\t",b[i][j]);
              }
          }

          m1= (a[0][0] + a[1][1])*(b[0][0]+b[1][1]);
          MPI_Isend(&m1,1,MPI_INT,1,2,MPI_COMM_WORLD,&request);
          
          m2= (a[1][0]+a[1][1])*b[0][0];
          MPI_Isend(&m2,1,MPI_INT,1,3,MPI_COMM_WORLD,&request);
          
          m3= a[0][0]*(b[0][1]-b[1][1]);
          MPI_Isend(&m3,1,MPI_INT,1,4,MPI_COMM_WORLD,&request);
          
          m4= a[1][1]*(b[1][0]-b[0][0]);
          MPI_Isend(&m4,1,MPI_INT,1,5,MPI_COMM_WORLD,&request);
          
          m5= (a[0][0]+a[0][1])*b[1][1];
          MPI_Isend(&m5,1,MPI_INT,1,6,MPI_COMM_WORLD,&request);
          
          m6= (a[1][0]-a[0][0])*(b[0][0]+b[0][1]);
          MPI_Isend(&m6,1,MPI_INT,1,7,MPI_COMM_WORLD,&request);
          
          m7= (a[0][1]-a[1][1])*(b[1][0]+b[1][1]);
          MPI_Isend(&m7,1,MPI_INT,1,8,MPI_COMM_WORLD,&request);
      
    }
    
    if(rank == 1)
    {
        int c[2][2];
        
        MPI_Irecv(&m1,1,MPI_INT,0,2,MPI_COMM_WORLD,&request);
        MPI_Irecv(&m2,1,MPI_INT,0,3,MPI_COMM_WORLD,&request);
        MPI_Irecv(&m4,1,MPI_INT,0,5,MPI_COMM_WORLD,&request);
        
        MPI_Wait(&request,&status);
        c[1][0]=m2+m4;
        
        MPI_Irecv(&m3,1,MPI_INT,0,4,MPI_COMM_WORLD,&request);
        MPI_Irecv(&m5,1,MPI_INT,0,6,MPI_COMM_WORLD,&request);
        MPI_Wait(&request,&status);
        c[0][1]=m3+m5;
        
   
        MPI_Irecv(&m6,1,MPI_INT,0,7,MPI_COMM_WORLD,&request);
        
        MPI_Wait(&request,&status);
        c[1][1]=m1-m2+m3+m6;
        
        MPI_Irecv(&m7,1,MPI_INT,0,8,MPI_COMM_WORLD,&request);
        
        MPI_Wait(&request,&status);
        c[0][0]=m1+m4-m5+m7;
          
        printf("\nAfter multiplication using \n");
        for(i=0;i<2;i++)
        {      
            printf("\n");
            for(j=0;j<2;j++)
            {
                   printf("%d\t",c[i][j]);
            }
        }
      printf("\n");
    }
       
    MPI_Finalize();
    return 0;
}
