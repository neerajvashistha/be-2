# take values A,S from clients and run the main loop for calculating P
#TODO- change the code for accomodating different sequence of exec(client2 rus before client1)

import socket

def addition(op1, op2):
	# length of P and A and S is same.
	result="" 
	carry="0"
	for i in range(len(op1)-1, -1, -1):		#run reverse loop
		if op1[i]=="1" and op2[i]=="1":
			if carry=="1":
				result="1"+result
				carry="1"
			else:					#carry = 0
				result="0"+result
				carry="1"
			
		elif op1[i]=="0" and op2[i]=="0":
			if carry=="1":
				result="1"+result
				carry="0"
			else:					#carry = 0
				result="0"+result
				carry="0"
		
		elif op1[i]=="0" and op2[i]=="1":
			if carry=="1":
				result="0"+result
				carry="1"
			else:					#carry = 0
				result="1"+result
				carry="0"
		
		else:						# 1, 0
			if carry=="1":
				result="0"+result
				carry="1"
			else:					#carry = 0
				result="1"+result
				carry="0"
	return result

s = socket.socket()         # Create a socket object
s.bind(("192.168.6.80", 9001))        # Bind to the port


M=int(input("Enter a multiplicant:"))
R=int(input("Enter a  multiplier:"))
M, R=bin(M), bin(R)
print "Binary representation: ", M, R

s.listen(2)                   # Now wait for client connection.
client, addr = s.accept()     # Establish connection with client.
print 'Got connection from', addr

client2, addr2 = s.accept()
print 'Got connection from', addr2

'''
Send the value of both. Client will return A, S. It will also return length_R as first param. <Length_R>A<A>S<S>
Send the value of length of R and value of R. Client will return P.	P<P>
'''
client.send(M+" "+R)

AS=client.recv(1024)	# recv A, S
index_A=AS.index("A")
index_S=AS.index("S")
A=AS[index_A+1:index_S]
S=AS[index_S+1:]

length_R=int(AS[:index_A])
client2.send(str(length_R)+" "+R)				
P=client2.recv(1024)	# recv P
index_P=P.index("P")
P=P[index_P+1:]
P_length=len(P)

#we've got A,S,P in strings
for i in range(length_R):
	
	last_two_digits=P[P_length-2:P_length]
	
	if last_two_digits == "01":
		#add A in P and store that in P and ignore overflows
		P=addition(A, P)
	elif last_two_digits == "10":
		#add S in P aND store the result in P and IGNORE OVerflows	
		P=addition(S, P)

	#print "After addn", P
	#arithmetic right shift (copy the sign bit as well). Start looping from the right most digits
	P=P[0]+P[0:P_length-1]

P=P[:P_length-1]
print P
