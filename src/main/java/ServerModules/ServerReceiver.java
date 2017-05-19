package ServerModules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by cristi on 5/19/17.
 */
public class ServerReceiver implements Runnable {

    private final Socket clientSocket;
    private Thread thread;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private final RequestDispatcher dispatcher;

    ServerReceiver(Socket clientSocket){

        String clientAddress = clientSocket.getRemoteSocketAddress().toString();
        this.dispatcher = new RequestDispatcher( clientAddress );
        this.thread = new Thread(this, "ServerReceiver thread for "+clientAddress);
        this.clientSocket = clientSocket;

        try{
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter( clientSocket.getOutputStream(), true );
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            readMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeInput();
    }

    private void closeInput(){
        if( in != null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readMessages() throws Exception{
        while(true){
            String message = in.readLine();

            if(message != null && !message.isEmpty() ){
                System.out.println(message);
                String response = dispatcher.dispatch(message);
                out.println(response);
            }

        }
    }

}
