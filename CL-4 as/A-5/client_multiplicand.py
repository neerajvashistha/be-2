# calculate values A, S and send it back to server.

import socket

def twos_comp(binM):
	S = [int(x) for x in binM]
	flag = 0
	for i in range(len(S)-1, -1, -1):
		if flag==1:
			#invert
			if S[i]==1:
				S[i]=0
			else:
				S[i]=1
			continue
		
		if S[i]==1:
			flag=1
	return S

s = socket.socket()         # Create a socket object
s.connect(("192.168.6.80", 9001))
temp=s.recv(1024)
temp=temp.split()
M, R=temp[0], temp[1]
origM, origR="", ""
Max_length=0

flag,flag_R=0,0			# flag=1: -M, flag=2: M. flag_R=1: -R, flag_R=2: R
if M[0]=="-":
	M=M[3:]
	origM=M
	M=twos_comp(M)
	M=[str(x) for x in M]
	M=''.join(M)
	flag=1
else:
	M=M[2:]
	flag=2	

if R[0]=="-":
	R=R[3:]
	origR=R
	R=twos_comp(R)
	R=[str(x) for x in R]
	R=''.join(R)
	flag_R=1
else:
	R=R[2:]
	flag_R=2	
	
if len(M)>= len(R):
	padding=len(M)-len(R)+1 #+1 for sign bit
	if flag==1:
		M="1"+M
	else:
		M="0"+M
	for i in range (padding):
		if flag_R==1:
			R="1"+R
		else:
			R="0"+R
	Max_length=len(M)
else:
	padding=len(R)-len(M)+1
	if flag_R==1:
		R="1"+R
	else:
		R="0"+R
	for i in range (padding):
		if flag==1:
			M="1"+M
		else:
			M="0"+M
	Max_length=len(R)
			
print M, R

#now calc A, S using the length of M and R and 1 (lenM+lenR+1)
A = []
for i in range(len(M)+len(R)+1):
	A.append(0)

for i in range(len(M)):
	A[i]=int(M[i])
A=[str(x) for x in A]
print "A: ", A
#A is ready at this point

if flag==1:		# orignal M was -ve. So we need origM with the minus sign eliinated
	for i in range(Max_length-len(origM)):
		origM="0"+origM
	S=[str(x) for x in origM]
	
else:
	S=twos_comp(M)

for i in range(len(M)+len(R)+1-len(S)):
	S.append(0)

S=[str(x) for x in S]

#S is ready at this point
print "S: ", S

#pack the A ans S in a buffer string
Send_AS= str(len(R))+"A"+''.join(A)	#secret- length of both operands is same. So u can replace R with M
Send_AS += "S"+''.join(S)
print Send_AS

#send the A and S to server and the job here is done
s.send(Send_AS)		

