Refer here: [https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/hello/hello-world.html]
[https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/hello/hello-world.html]: https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/hello/hello-world.html

on tty1

cipher@blackfury-HP-eNVy:~/be-2/Cl-3 as/A3/RMI distributed,client-server$ javac *.java
cipher@blackfury-HP-eNVy:~/be-2/Cl-3 as/A3/RMI distributed,client-server$ java Server
Server is ready to listen... 

on tty2
cipher@blackfury-HP-eNVy:~/be-2/Cl-3 as/A3/RMI distributed,client-server$ java Client 
java Client int1<-7 to 7> int2<-7 to 7>

For example: java Client 7 -7 

You will get the output like the following: 

A[0, 1, 1, 1, 0, 0, 0, 0, 0]
O[1, 0, 0, 1, 0, 0, 0, 0, 0]
P[1, 1, 0, 0, 1, 1, 1, 1, 1]
P0[1, 1, 0, 0, 1, 1, 1, 1, 1]
P1[1, 1, 0, 0, 1, 1, 1, 1, 1]
P2[1, 1, 0, 0, 1, 1, 1, 1, 1]
P3[1, 1, 0, 0, 1, 1, 1, 1, 1]
Result[-49]
