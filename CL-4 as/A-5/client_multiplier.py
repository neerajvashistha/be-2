# calculate value P and send it back to server.

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
length_R, R= int(temp[0]), temp[1]

if R[0]=="-":
	R=R[3:]
	#origR=R
	R=twos_comp(R)
	R=[str(x) for x in R]
	R=''.join(R)
	for i in range (length_R-len(R)):
		R="1"+R
else:
	R=R[2:]
	for i in range (length_R-len(R)):
		R="0"+R
	#flag_R=2
	
P = []
for i in range(2*length_R + 1):
	P.append(0)

print "check length of P: ", P

for i in range(len(R)):
	P[length_R+i]=int(R[i])

P=[str (x) for x in P]
P="".join(P)
print P
s.send("P"+P)	

