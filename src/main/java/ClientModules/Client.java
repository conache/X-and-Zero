package ClientModules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private Socket socket;
    private String username;
    private final Thread th;
    private PrintWriter out;
    private BufferedReader in;


    public Client(String server, Integer serverPort) throws Exception {

        this.th = new Thread(this);
        socket = new Socket(server, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        th.start();

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void run() {

        String response;
        response = sendUsername();
        System.out.println(response);
        System.out.println("Waiting for opponent...");
        response = getOpponentUsername();
        System.out.println("Opponent: "+response);
        System.out.println("Starting game...");
        response = requestStartGame();
        System.out.println(response);
        beginGame(response);

    }

    private void beginGame(String initialData ){

        String[] components = initialData.split(" ");
        Game.instance().start(out,in,components[0].charAt(0));

    }

    private String sendUsername(){

        Scanner sc = new Scanner(System.in);
        String response;

        do {
            //String username ux.getUserName();
            String username = sc.nextLine();
            response = sendMessage( "username "+username);

        } while( response.equals("used") );

        this.setUsername( username );

        return response;
    }

    private String sendMessage(String message){

        out.println(message);
        return getResponseFromServer(message);

    }

    private String getOpponentUsername(){

        String message = "opponent username";
        return sendMessage(message);

    }

    private String requestStartGame(){

        String message = "start game";
        return sendMessage(message);

    }

    private String getResponseFromServer(String message){

        String response = null;
        try {
            response = in.readLine();
        } catch (IOException e) {
            System.out.println("Error reading response for message:" + message);
            e.printStackTrace();
        }
        return response;

    };


}
