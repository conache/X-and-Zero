package ClientModules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private Socket socket;
    private String symbol;
    private String username;
    private final Thread th;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String server, Integer serverPort) throws IOException {

        this.th = new Thread(this, username + " thread");
        socket = new Socket(server, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        in.readLine();
        th.start();

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        String response;

         do {
             this.setUsername( sc.nextLine() );
             response = sendUsername();
         }

        while( response.equals("used") );
        System.out.println(response);
        System.out.println("Waiting for opponent...");
        response = getOpponentUsername();
        System.out.println("Opponent: "+response);

    }

    private String sendUsername(){
        out.println("username "+this.getUsername());

        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println("Error sending username");
            e.printStackTrace();
        }

        return null;
    }

    private String getOpponentUsername(){
        try {
            out.println("opponent username");
            return in.readLine();
        } catch (IOException e) {
            System.out.println("Error getting opponent username");
            e.printStackTrace();
        }
        return null;
    }
}
