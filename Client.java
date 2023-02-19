import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
// To change the 2 input parameters (double numbers) in Intellij go to: Edit Configurations -> Client -> Built and Run

public class Client {
    public static void main(String args[]) {
        try {


            String ip;
            int port;
            int fn;

            Scanner ss1 = new Scanner(System.in);
            ip = ss1.nextLine();
            Scanner ss2 = new Scanner(System.in);
            port = ss2.nextInt();
            Scanner ss3 = new Scanner(System.in);
            fn = ss3.nextInt();

            // connect to the RMI registry
            Registry rmiRegistry = LocateRegistry.getRegistry(port);
            System.out.println("Connected to message server with ip: "+ip+" and port: "+port);
            // get reference for remote object
            MessageC stub = (MessageC) rmiRegistry.lookup("messanger");
            //end of server connect stuff

            switch (fn) {
                case 1: {
                    System.out.println("fn=1 selected");
                    String username;
                    Scanner fn1 = new Scanner(System.in);
                    username = fn1.nextLine();
                    System.out.println(stub.CrateAccount(username));


                }
                case 2:{
                    int auth;
                    Scanner fn2 = new Scanner(System.in);
                    auth = fn2.nextInt();
                    System.out.println(stub.ShowAccounts(auth));

                }
                case 3:{
                    String reciver;
                    int auth;
                    Scanner fn3 = new Scanner(System.in);
                    auth = fn3.nextInt();
                    Scanner fn4 = new Scanner(System.in);
                    reciver = fn4.nextLine();
                    String message;
                    Scanner fn5 = new Scanner(System.in);
                    message = fn5.nextLine();


                    System.out.println(stub.SendMessage(auth,reciver,message));

                }
                case 4:{
                    int auth;
                    Scanner fn6 = new Scanner(System.in);
                    auth = fn6.nextInt();

                    System.out.println(stub.ShowInbox(auth));

                }
                case 5:{
                    int  idm;
                    int auth;
                    Scanner fn7 = new Scanner(System.in);
                    auth = fn7.nextInt();
                    Scanner fn8 = new Scanner(System.in);
                    idm = fn8.nextInt();
                    System.out.println(stub.ReadMessage(auth,idm));

                }
                case 6:{
                    int  idm;
                    int auth;
                    Scanner fn9 = new Scanner(System.in);
                    auth = fn9.nextInt();
                    Scanner fn10 = new Scanner(System.in);
                    idm = fn10.nextInt();

                    System.out.println(stub.DeleteMessage(auth,idm));

                }
                default:{
                    System.out.println("Unavailable FN_ID Choice");
                }
                System.out.println("changes saved closing");
                break;
            }

            //System.out.println("Addition result      : " + stub.add(a, b));

            System.out.println(ip +"\n" +port);
            //end of client program


        } catch (Exception e) {
            //in case the server doesnt work
            System.out.println(e);
        }
    }
}