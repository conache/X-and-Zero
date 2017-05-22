package ServerModules;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cristi on 5/19/17.
 */
public class GameManager {

    private Set<String> waitingThreads = new HashSet<String>();
    private HashMap<String, Socket> socket = new HashMap<String, Socket>();
    private HashMap<String, String> username = new HashMap<String, String>();
    private HashMap<String, String> opponent = new HashMap<String, String>();
    private HashMap<String, Thread> thread = new HashMap<>();

    private String waiting;
    private Boolean assigned = false ;

    private static GameManager gameManager;

    public static synchronized GameManager instance(){
        if( gameManager == null )
            gameManager = new GameManager();
        return gameManager;
    }

    public void setSocket(String address, Socket s){
        socket.put(address,s);
    }

    public synchronized String assignOpponent( String address ) throws InterruptedException {

        String user = username.get(address);

        while( waiting == null ){
            waiting = user;
            wait();
            waiting = null;
            break;
        }

        if( opponent.get(user) == null ) {
            opponent.put(user, waiting);
            opponent.put(waiting, user);
            assigned = true;
            notify();
        }

        return opponent.get(user);
    }


    public synchronized String addUser(String address, String name){

        for(String key: username.keySet() ){
            if( username.get(key).equals(name) ) return "used";
        }

        thread.put(name, Thread.currentThread());
        username.put(address, name);

        return "username assigned";
    }

    public String startGame(String address) throws InterruptedException {

        String user = username.get(address);
        String op = opponent.get(user);

        System.out.println(thread.toString());

        if( waitingThreads.contains(op) ){
            Thread t = thread.get(op);

            synchronized (t) {
                t.notify();
                return "0 noflag";
            }

        }else{

            waitingThreads.add(user);

            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait();
            }

            waitingThreads.remove(user);
            return "X flag";
        }

    }

    public String moveFrom(String address, String message){

        String user = username.get(address);
        String op = opponent.get(user);
        Socket opSocket = getSocketOf(op);
        PrintWriter out = null;

        try {
            out = new PrintWriter( opSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println(message);

        return "Move sent to other user";
    }

    private Socket getSocketOf(String user){

        for(String key: socket.keySet() ){
            if( username.get(key).equals(user) ) return socket.get(key);
        }

        return null;
    }
}
