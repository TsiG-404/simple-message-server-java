import java.rmi.*;

public interface MessageC extends Remote {

    //here the project functions
    public String CrateAccount(String username) throws RemoteException;

    public String ShowAccounts(int a) throws RemoteException;

    public String SendMessage(int sen1,String rec1,String msg) throws RemoteException;

    public String ShowInbox(int auth) throws RemoteException;

    public String ReadMessage(int auth,int msg) throws RemoteException;

    public String DeleteMessage(int auth, int msg) throws RemoteException;

}