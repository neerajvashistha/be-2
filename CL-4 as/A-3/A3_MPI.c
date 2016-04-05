#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
#define v 1 /* verbose flag, output if 1, no output if 0 */
int main ( int argc, char *argv[] )
{
	int myid,j,*data,tosum[25],sums[5],sums2[5];
	MPI_Init(&argc,&argv);
	MPI_Comm_rank(MPI_COMM_WORLD,&myid);
	if(myid==0) /* manager allocates and initializes the data */
	{

		data = (int*)calloc(100,sizeof(int));
		for (j=0; j<100; j++) data[j] = j+1;
		if(v>0)
		{
			printf("The data to sum : ");
			for (j=0; j<100; j++) 
			printf(" %d",data[j]);
			 printf("\n");
		}
	}
	MPI_Scatter(data,25,MPI_INT,tosum,25,MPI_INT,0,MPI_COMM_WORLD);
	if(v>0) /* after the scatter, every node has 25 numbers to sum*/
	{
		printf("Node %d has numbers to sum :",myid);
		for(j=0; j<25; j++) printf(" %d", tosum[j]);
		printf("\n");
	}
	sums[myid] = 0;
	for(j=0; j<25; j++) sums[myid] += tosum[j];
	if(v>0) printf("Node %d computes the sum %d\n",myid,sums[
	myid]);

	MPI_Gather(&sums[myid]	,1,MPI_INT,&sums2[myid],1,MPI_INT,0,MPI_COMM_WORLD);

	if(myid==0) /* after the gather, sums contains the four sums*/
	{
		printf("The four sums : ");
		printf("%d",sums2[0]);
		for(j=1; j<4; j++) printf(" + %d", sums2[j]);
		for(j=1; j<4; j++) sums2[0] += sums2[j];
		printf(" = %d, which should be 5050.\n",sums2[0]);
	}

	MPI_Finalize();
	return 0;
}
