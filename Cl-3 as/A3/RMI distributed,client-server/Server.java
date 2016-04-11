import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
	
public class Server {
	
    public Server() {}

    public static void main(String args[]) {
	
	try {
		LocateRegistry.createRegistry(1099);
	    BoothImpl robj = new BoothImpl();
	    Booth stub = (Booth) UnicastRemoteObject.exportObject(robj, 0);

	    Registry registry = LocateRegistry.getRegistry();
	    registry.bind("boothmul", stub);
            System.out.println("Server is ready to listen... ");

	} catch (Exception e) {
	    System.err.println("Server exception thrown: " + e.toString());
	    e.printStackTrace();
	}
    }
}

