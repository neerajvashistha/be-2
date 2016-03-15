import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Client {
    private static Booth stub = null;
    private Client() {}

    public static void main(String[] args) {

        int n1=7,n2=-7;
        try {
            Registry reg = LocateRegistry.getRegistry("localhost");
            stub = (Booth) reg.lookup("boothmul");

        } catch (Exception e) {
            System.err.println("Client exception thrown: " + e.toString());
            e.printStackTrace();
        }
        if (args.length == 2) {
            try {
                n1 = Integer.parseInt(args[0]);
                n2 = Integer.parseInt(args[1]);
            }
            catch (Exception e) {
                System.out.println("Wrong input " + e.getMessage() );
                System.exit(0);
            }
            print(n1,n2);

        } else {
            System.out.println("java Client int1<-7 to 7> int2<-7 to 7>");
            System.out.println("\nFor example: java Client 7 -7 ");
            System.out.println("\nYou will get the output like the following: \n");
            print(n1,n2);
            System.exit(0);
        }        
        
    }
    
    public static void print(int n1,int n2){
        TreeMap<String, int[]> result;
        try {
            result = stub.multiply(n1,n2);
            for(Map.Entry m:result.entrySet()){  
            System.out.print(m.getKey()); 
            System.out.println(Arrays.toString(result.get(m.getKey())));      
        }
        }catch(Exception e) {
            System.out.println("Remote method exception thrown: " + e.getMessage());
        }        
    }
}
/*
cipher@blackfury-HP-eNVy:~/be-2/boothmul$ java Client
Usage: java Client int1<-7 to 7> int2<-7 to 7>

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
cipher@blackfury-HP-eNVy:~/be-2/A3$ java Client 5 6
A[0, 1, 0, 1, 0, 0, 0, 0, 0]
O[1, 0, 1, 1, 0, 0, 0, 0, 0]
P[0, 0, 0, 1, 1, 1, 1, 0, 0]
P0[0, 0, 0, 1, 1, 1, 1, 0, 0]
P1[0, 0, 0, 1, 1, 1, 1, 0, 0]
P2[0, 0, 0, 1, 1, 1, 1, 0, 0]
P3[0, 0, 0, 1, 1, 1, 1, 0, 0]
Result[30]

*/
