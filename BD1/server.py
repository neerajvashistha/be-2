import socket
import sha1
ss = socket.socket(socket.AF_INET, socket.SOCK_STREAM,0)
ss.bind(("", 5008))
ss.listen(5)

print "TCPServer1 Waiting for client on port 7998"

while 1:
        client_socket, address = ss.accept()
        
        print "I got a connection from ", address
        
        stri = client_socket.recv(1024)
        
        alist = stri.split('\n')
        print alist[0]
        checkHash=sha1.sha1(str(alist[0]))
        print checkHash
        if checkHash == alist[1]:
                print "Message is valid"
                client_socket.send("Message is Verified")
        else:
                client_socket.send("Message could not be verified")
        client_socket.close()
        ss.close()
        print "Data sent!"

        break;


'''
cipher@blackfury-HP-eNVy:~/be-2/BE1$ python server.py 
TCPServer1 Waiting for client on port 7998
I got a connection from  ('127.0.0.1', 44144)
Hello World!
2b72790bf888c4427287b3d79a8c8a3320c61986
Message is valid
Data sent!

'''
