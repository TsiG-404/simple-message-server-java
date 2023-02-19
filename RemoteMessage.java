import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RemoteMessage extends UnicastRemoteObject implements MessageC {
    public RemoteMessage() throws RemoteException {
        super();
    }

    //username and id auth
    HashMap<String, Integer> accounts = new HashMap<String, Integer>();
    //message id and the sender of the message
    HashMap<Integer, String> Msender = new HashMap<Integer, String>();
    //message id and reciver
    HashMap<Integer, String> Mreciver = new HashMap<Integer, String>();
    //message id and message
    HashMap<Integer, String> message = new HashMap<Integer, String>();
    //message id and if it is read or new
    HashMap<Integer, Boolean> Mread = new HashMap<Integer, Boolean>();

    //here we can add functions

    //fn id =1
    public Integer MessageId=0;
    public String CrateAccount(String username) throws RemoteException{
        //System.out.println("connected to fn=1 to server");
        int b=1; //this can stop exists later it is for debug

        //generate random auth token
        Random rand = new Random();
        // Obtain a number between [0 - 2999].
        int auth = rand.nextInt(3000);
        auth+=1;
        //end of auth generation

        //this checks if the username has invalid characters
        boolean Uinvalid=false;
        for(int i =0; i < username.length() ; i++){
            char current = username.charAt(i);
            if(current <=32 || current >=123)
            {
                Uinvalid=true;

            }
            //what do you need to do for each checked character
        }
        if(Uinvalid==true)
        {
            System.out.println("Invalid Username");
            //return -2;
            return "Invalid Username";
        }

        //checks if the username exists from an other account
        boolean Uexist = false;
        for (Map.Entry<String, Integer> e : accounts.entrySet()) {
            if (Objects.equals(username, e.getKey()) ) {
                Uexist = true;
            }
        }
        if (Uexist == true) {
            System.out.println("Sorry, the user already exists");
            //return -1;
            return "Sorry, the user already exists";
        }
        else{
            //if the username doesnt exists it crate an account
            accounts.put(username,auth);
            System.out.println(auth);
        }
        //break;
        //return b;
        return Integer.toString(auth);
    }
    // end of fn=1

    //start of fn=2
    public String ShowAccounts(int a) throws RemoteException{
        int n=1;
        String nn=null;
        for (String i : accounts.keySet()) {
            System.out.print(n);
            System.out.print(". ");
            System.out.println(i);
            nn=nn+(n+". "+i+"\n");

            n+=1;

        }
        return nn;
    }
    //end of fn=2


    //start of fn=3
    public String SendMessage(int sen1,String rec1, String msg) throws RemoteException{
        //transform auth to username
        String sen2 = null;
        for (Map.Entry<String, Integer> e : accounts.entrySet()) {
            if (Objects.equals(sen1, e.getValue())) {
                sen2=e.getKey();
            }
        }

        //for now they will work only local until they work with args
        int authRec=0;
        for (Map.Entry<String, Integer> e : accounts.entrySet()) {
            if (Objects.equals(rec1, e.getKey())) {
                authRec=e.getValue();
            }
        }
        if(authRec>0)
        {
            System.out.println("ok");
        }
        else
        {
            System.out.println("User does not exist");
            //return -3;
            return "User does not exist";
        }

        //creates message and adds it as unread
        MessageId+=1;
        message.put(MessageId,msg);
        Mreciver.put(MessageId,rec1);
        Msender.put(MessageId,sen2);
        Mread.put(MessageId,false);
        //System.out.println("Message successfully send to " +receive);

        //return 3;
        return "ok";
    }
    //end of fn=3

    //start of fn=4
    public String ShowInbox(int auth) throws RemoteException{
        //transforms auth to username
        String sen2 = null;
        for (Map.Entry<String, Integer> e : accounts.entrySet()) {
            if (Objects.equals(auth, e.getValue())) {
                sen2=e.getKey();
            }
        }
        //shows inbox
        String lm=null;
        for (Map.Entry<Integer, String> e : Mreciver.entrySet()) {
            if (Objects.equals(sen2, e.getValue())) {
                System.out.print(e.getKey());
                System.out.print(". from:");
                lm=lm+(e.getKey()+ ". from:");
                //search the map of message id and senders to find the sender from the id
                for (Map.Entry<Integer, String> eme : Mreciver.entrySet()) {
                    if (Objects.equals(e, eme.getKey())) {
                        System.out.print(eme.getValue());
                        lm=lm+eme.getValue();
                    }
                }
                //search the map of message id and read messages to find if it is read or not
                for (Map.Entry<Integer, Boolean> emeTF : Mread.entrySet()) {
                    if (Objects.equals(e, emeTF.getKey())) {
                        if (emeTF.getValue() != true) {
                            System.out.println("*");
                            lm=lm+("*"+"\n");
                        }
                    }
                }


            }
        }


       // return 4;
        return lm;
    }
    //end of fn=4

    //start of fn=5
    public String ReadMessage(int auth,int msg) throws RemoteException{
        boolean found=false;
        String rm=null;
        //prints the sender
        for (Map.Entry<Integer, String> eme : Msender.entrySet()) {
            if (Objects.equals(msg, eme.getKey())) {
                System.out.print("(");
                System.out.print(eme.getValue());
                System.out.print(")");
                rm="("+eme.getValue()+")";
                found=true;
            }
        }
        //prints the message
        for (Map.Entry<Integer, String> ms2 : message.entrySet()) {
            if (Objects.equals(msg, ms2.getKey())) {
                System.out.print(ms2.getValue());
                rm=rm+(ms2.getValue());
            }
        }
        //makes the message read
        for (Map.Entry<Integer, Boolean> ms2 : Mread.entrySet()) {
            if (Objects.equals(msg, ms2.getKey())) {

                Mread.remove(msg);
                Mread.put(msg,true);
            }
        }

        //if the message doesnt exist
        if(found==true)
        {
            System.out.println("Message ID does not exists");
            //return -4;
            return "Message ID does not exists";
        }


        //return 5;
        return rm;
    }
    //end of fn=5

    //start of fn=6
    public String DeleteMessage(int auth, int msg) throws RemoteException{
        boolean delete=false;
        //delete the message id from mread map
        for (Map.Entry<Integer, Boolean> ms2 : Mread.entrySet()) {
            if (Objects.equals(msg, ms2.getKey())) {
                Mread.remove(msg);
                delete=true;
                System.out.println("OK");
            }
        }
        //delete the message id from msender map
        for (Map.Entry<Integer, String> ms2 : Msender.entrySet()) {
            if (Objects.equals(msg, ms2.getKey())) {
                Msender.remove(msg);
            }
        }
        //delete the message id from mreciver map
        for (Map.Entry<Integer, String> ms2 : Mreciver.entrySet()) {
            if (Objects.equals(msg, ms2.getKey())) {
                Mreciver.remove(msg);
            }
        }
        //delete the message id from message map
        for (Map.Entry<Integer, String> ms2 : message.entrySet()) {
            if (Objects.equals(msg, ms2.getKey())) {
                message.remove(msg);
            }
        }

        //if the message id doesnt exist
        if(delete==false)
        {
            System.out.println("Message does not exist");
            //return -5;
            return "Message does not exist";
        }

        //return 6;
        return "OK";
    }


}