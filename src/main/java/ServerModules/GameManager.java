package ServerModules;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by cristi on 5/19/17.
 */
public class GameManager {
    private HashMap<String, Socket> socket = new HashMap<String, Socket>();
    private HashMap<String, String> username = new HashMap<String, String>();
    private HashMap<String, String> opponent = new HashMap<String, String>();
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

        if( username.get( name ) != null ) {
            return "used";
        }else{
            username.put(address, name);
        }

        return "username assigned";
    }

}
