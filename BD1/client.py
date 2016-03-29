# TCP client example
import socket
import sha1
TCP_IP = '127.0.0.1'
TCP_PORT = 5008
BUFFER_SIZE = 1024

MESSAGE = raw_input("Enter a message: ")
digest = sha1.sha1(MESSAGE)

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))
s.send(MESSAGE+"\n")
s.send(digest)
data = s.recv(BUFFER_SIZE)
s.close()

print "received data:", data