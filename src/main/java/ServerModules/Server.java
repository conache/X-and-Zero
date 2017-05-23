package ServerModules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by me
 */
public class Server {

    public static void main(String[] args) throws Exception {

        ServerSocket server = null;
        int port = 8100;
        PrintWriter out;

        try{
            server = new ServerSocket(port);
            System.out.println("Started server");

            while(true){

                Socket clientSocket = server.accept();
                out = new PrintWriter( clientSocket.getOutputStream(), true);
                GameManager.instance().setSocket( clientSocket.getRemoteSocketAddress().toString(), clientSocket);
                new ServerReceiver(clientSocket);
                out.println("Connected");
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(server != null )
                server.close();
        }

    }

}
