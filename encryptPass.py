import hashlib
import sys
import os
import binascii
try:
    hash_name = sys.argv[1]
except IndexError:
    print 'Specify the hash name as the first argument.'
else:
    try:
        data = sys.argv[2]
    except IndexError:    
        data = '''Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do 
eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim
veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum
dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident,
sunt in culpa qui officia deserunt mollit anim id est laborum.'''

    salt = os.urandom(64)
    print salt
    h = hashlib.new(hash_name)
    h.update(data)
    print h.hexdigest()
    dk = hashlib.pbkdf2_hmac(hash_name, binascii.b2a_base64(h), binascii.b2a_base64(salt), 100000,128)
    print binascii.hexlify(dk)