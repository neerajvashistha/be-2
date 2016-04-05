import hashlib
import sys
import os
import binascii

try:
    hash_name = sys.argv[1]
except IndexError:
    print('1st arg\n'+ str(hashlib.algorithms_guaranteed)+'\n2nd arg username','\n3rd arg password')
else:
    try:
        username = sys.argv[2] 
        passwd = sys.argv[3]
        password = bytes(passwd,'UTF-8')      
    except IndexError:    
        print('using default password: password')
        password = b'password'



    salt = os.urandom(32)
    chef_salt = binascii.hexlify(salt)
    filename = username+"_salt.key"
    salT_file = open(username+"_salt.key","w")
    print("Salt\n"+chef_salt.decode('utf-8'))
    salT_file.write(chef_salt.decode('utf-8'))
    salT_file.close()
    dk = hashlib.pbkdf2_hmac(hash_name, password, chef_salt, 100000,128)
    store_hash = binascii.hexlify(dk)
    filename = username+"_hash.hash"
    hash_file = open(username+"_hash.hash","w")
    print("hash\n"+store_hash.decode('utf-8'))
    hash_file.write(store_hash.decode('utf-8'))
    hash_file.close()
