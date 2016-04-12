# To Store a Password
1.Generate a long random salt using a CSPRNG.
2.generate hash using standard cryptographic hash function such as SHA256
3.key stretching using PBKDF2 inpust name = standard cryptographic hash function such as SHA256, password = hash generated, salt = salt, round  10000 aleast, dklen = 64
atleast length of derived key
4.Save both the salt and the hash in the user's file record.

# To Validate a Password
1.Retrieve the user's salt and hash from the file.
2.Prepend the salt to the given password and hash it using the same hash function.
3.Compare the hash of the given password with the hash from the file. If they match, the password is correct. Otherwise, the password is incorrect.