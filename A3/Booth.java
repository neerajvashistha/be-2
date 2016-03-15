import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface Booth extends Remote {
    public TreeMap<String, int[]> multiply(int n1, int n2)
    				throws RemoteException;
   	public int getDecimal(int[] B)
   					throws RemoteException;
   	public void rightShift(int[] A)
   					throws RemoteException;
   	public void add(int[] A, int[] B)
   					throws RemoteException;
   	public int[] binary(int n)
   					throws RemoteException;
}	

