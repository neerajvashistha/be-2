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
            System.out.println("Usage: java Client int1 int2 ");
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
