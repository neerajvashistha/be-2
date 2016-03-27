/* sha1Test.java
#- Copyright (c) 2013, HerongYang.com, All Rights Reserved.
 */
import java.security.*;
import java.io.*;
class sha1Test {
   public static void main(String[] a) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA1");
         System.out.println("Message digest object info: ");
         System.out.println("   Algorithm = "+md.getAlgorithm());
         System.out.println("   Provider = "+md.getProvider());
         System.out.println("   toString = "+md.toString());

         String input = "";
         md.update(input.getBytes()); 
      	 byte[] output = md.digest();
         System.out.println();
         System.out.println("SHA1(\""+input+"\") =");
         System.out.println("   "+bytesToHex(output));

         input = "abc";
         md.update(input.getBytes()); 
      	 output = md.digest();
         System.out.println();
         System.out.println("SHA1(\""+input+"\") =");
         System.out.println("   "+bytesToHex(output));

         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         input = br.readLine();
         md.update(input.getBytes()); 
      	 output = md.digest();
         System.out.println();
         System.out.println("SHA1(\""+input+"\") =");
         System.out.println("   "+bytesToHex(output));
         
      } catch (Exception e) {
         System.out.println("Exception: "+e);
      }
   }
   public static String bytesToHex(byte[] b) {
      char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                         '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
      StringBuffer buf = new StringBuffer();
      for (int j=0; j<b.length; j++) {
         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
         buf.append(hexDigit[b[j] & 0x0f]);
      }
      return buf.toString();
   }
}
/*
cipher@blackfury-HP-eNVy:~/be-2/DE1$ javac sha1Test.java 
cipher@blackfury-HP-eNVy:~/be-2/DE1$ java sha1Test A
Message digest object info: 
   Algorithm = SHA1
   Provider = SUN version 1.7
   toString = SHA1 Message Digest from SUN, <initialized>


SHA1("") =
   DA39A3EE5E6B4B0D3255BFEF95601890AFD80709

SHA1("abc") =
   A9993E364706816ABA3E25717850C26C9CD0D89D
000005fab4534d05api_key9a0554259914a86fb9e7eb014e4e5d52permswrite

SHA1("000005fab4534d05api_key9a0554259914a86fb9e7eb014e4e5d52permswrite") =
   C682EE25D86598A06BEAC017A8E6083671217B68

*/