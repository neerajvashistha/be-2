import java.rmi.RemoteException;
import java.util.*;

public class BoothImpl implements Booth {

    public TreeMap<String, int[]> multiply(int n1, int n2)
    {
        int[] m = binary(n1);
        int[] m1 = binary(-n1);
        int[] r = binary(n2);        
        int[] A = new int[9];
        int[] O = new int[9];
        int[] P = new int[9];        
        for (int i = 0; i < 4; i++)
        {
            A[i] = m[i];
            O[i] = m1[i];
            P[i + 4] = r[i];
        }



        TreeMap<String, int[]> map = new TreeMap<String, int[]>();
        map.put("A", A);

        map.put("O", O);
        map.put("P", P);



        for (int i = 0; i < 4; i++)
        {
            if (P[7] == 0 && P[8] == 0);
                // do nothing            
            else if (P[7] == 1 && P[8] == 0)
                add(P, O);                            
            else if (P[7] == 0 && P[8] == 1)
                add(P, A);            
            else if (P[7] == 1 && P[8] == 1);
                // do nothing
 
            rightShift(P);
            map.put("P"+Integer.toString(i), P);
        }
        int [] res = {getDecimal(P)};
        map.put("Result",res);
        return map;
    }
    /** Function to get Decimal equivalent of P **/
    public int getDecimal(int[] B)
    {
        int p = 0;
        int t = 1;
        for (int i = 7; i >= 0; i--, t *= 2)
            p += (B[i] * t);
        if (p > 64)
            p = -(256 - p);
        return p;        
    }
    /** Function to right shift array **/
    public void rightShift(int[] A)
    {        
        for (int i = 8; i >= 1; i--)
            A[i] = A[i - 1];        
    }
    /** Function to add two binary arrays **/
    public void add(int[] A, int[] B)
    {
        int carry = 0;
        for (int i = 8; i >= 0; i--)
        {
            int temp = A[i] + B[i] + carry;
            A[i] = temp % 2;
            carry = temp / 2;
        }        
    }
    /** Function to get binary of a number **/
    public int[] binary(int n)
    {
        int[] bin = new int[4];
        int ctr = 3;
        int num = n;
        /** for negative numbers 2 complment **/
        if (n < 0)
            num = 16 + n;
        while (num != 0)
        {
            bin[ctr--] = num % 2;
            num /= 2;
        }
        return bin;
    }
}
