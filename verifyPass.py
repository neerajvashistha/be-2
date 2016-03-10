import hashlib
import sys
import os
import binascii

try:
    hash_name = sys.argv[1]
except IndexError:
    print('Specify the hash name as the first argument.')
else:
    try:
        username = sys.argv[2] 
        passwd = sys.argv[3]
        password = bytes(passwd,'UTF-8')      
    except IndexError:    
        print('using default password: password')
        password = b'password'

    #password = bytes(passwd,'UTF-8')
    salT_file = open(username+"_salt.key")
    chef_salt = salT_file.read()
    print("Salt\n"+chef_salt)
    dk = hashlib.pbkdf2_hmac(hash_name, password, bytes(chef_salt,'UTF-8'), 100000,128)
    hashk = binascii.hexlify(dk)
    new_hash = hashk.decode('utf-8')
    hash_file = open(username+"_hash.hash")
    old_hash  = hash_file.read()
    print("hash\n"+old_hash)
    if new_hash == old_hash:
    	print("The user "+username+" has used valid password")
    else:
    	print("Incorrect username or password")

'''
envy@envy-vm:~/$ sudo apt-get install libssl-dev openssl
envy@envy-vm:~/$ virtualenv -p python3 be-2/
envy@envy-vm:~/$ source be-2/bin/activate
(be-2) envy@envy-vm:~/ pip install hashlib
(be-2) envy@envy-vm:~/ cd be-2/
(be-2) envy@envy-vm:~/be-2$ python encryptPass.py
1st arg
{'sha1', 'md5', 'sha384', 'sha224', 'sha512', 'sha256'}
2nd arg username 
3rd arg password
(be-2) envy@envy-vm:~/be-2$ python encryptPass.py sha256 cybersecurity 1l0v3cy83453cu41ty
Salt
becf85585f32a32fa21e2c600ec1b90ee91e71407cd47c20fc661afa96d7837e
hash
9040251af5493315fdecb02661e44a321cebba914fa1e540d507e748f2f36525f918471baa12c9f48c62d8f7ba2c13a94e76c3a7e06819f236104e91618058b330135466106e28ccdb67c95d494d87edbe252768d4e9885ff012dcde01fab9ae0c04e1f14380eb3e0ac54e6dd8387e2bcc291b5560eff3b8b8074a8d3957f9df
(be-2) envy@envy-vm:~/be-2$
(be-2) envy@envy-vm:~/be-2$
(be-2) envy@envy-vm:~/be-2$ python verifyPass.py sha256 cybersecurity 1l0v3cy83453cu41ty
Salt
becf85585f32a32fa21e2c600ec1b90ee91e71407cd47c20fc661afa96d7837e
hash
9040251af5493315fdecb02661e44a321cebba914fa1e540d507e748f2f36525f918471baa12c9f48c62d8f7ba2c13a94e76c3a7e06819f236104e91618058b330135466106e28ccdb67c95d494d87edbe252768d4e9885ff012dcde01fab9ae0c04e1f14380eb3e0ac54e6dd8387e2bcc291b5560eff3b8b8074a8d3957f9df
The user cybersecurity has used valid password
(be-2) envy@envy-vm:~/be-2$ python verifyPass.py sha256 cybersecurity l0v3cy83453cu41ty
Salt
becf85585f32a32fa21e2c600ec1b90ee91e71407cd47c20fc661afa96d7837e
hash
9040251af5493315fdecb02661e44a321cebba914fa1e540d507e748f2f36525f918471baa12c9f48c62d8f7ba2c13a94e76c3a7e06819f236104e91618058b330135466106e28ccdb67c95d494d87edbe252768d4e9885ff012dcde01fab9ae0c04e1f14380eb3e0ac54e6dd8387e2bcc291b5560eff3b8b8074a8d3957f9df
Incorrect username or password
(be-2) envy@envy-vm:~/be-2$ deactivate
envy@envy-vm:~/be-2$ 
'''