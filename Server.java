import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Server {
    public static void main(String args[]) {
        try {
            RemoteMessage stub = new RemoteMessage();
            //here comes the code to give the server arguments
            String ip;
            int port;
            //Scanner ss1 = new Scanner(System.in);
            //ip = ss1.nextLine();
            ip="localhost";
            Scanner ss2 = new Scanner(System.in);
            port = ss2.nextInt();

            // create the RMI registry on port
            Registry rmiRegistry = LocateRegistry.createRegistry(port);
            // path to access is rmi://localhost:5000/messanger
            rmiRegistry.rebind("messanger", stub);
            System.out.println("Message Server is ready in ip: "+ ip+ " and port: "+port);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}