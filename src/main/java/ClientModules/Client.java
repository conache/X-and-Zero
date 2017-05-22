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
    private Game game;
    private char symbol;
    private char opponentSymbol;

    public Client(String server, Integer serverPort) throws Exception {

        this.th = new Thread(this);
        socket = new Socket(server, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        in.readLine();
        initUsername();
        th.start();

    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void run() {

        String response;
        System.out.println("Waiting for opponent...");
        response = getOpponentUsername();
        System.out.println("Opponent: "+response);
        System.out.println("Starting game...");
        response = requestStartGame();
        System.out.println(response);
        try {
            playGame(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void playGame(String initialData ) throws IOException {
        boolean won;
        String response;
        String[] components = initialData.split(" ");
        this.symbol = components[0].charAt(0);
        this.opponentSymbol = symbol == 'X' ? '0' : 'X' ;
        boolean hasFlag = components[1].equals("flag");
        this.game = new Game( 3,3, symbol );

        int[] positions;
        Integer linie, coloana;

        do{
            if( hasFlag ) {
                game.showBoard();
                System.out.println("Your turn");
                positions = getMove();
                out.println("hit "+positions[0]+" "+positions[1]+" flag");
                game.hit( positions[0], positions[1], symbol);
                response = in.readLine();
                System.out.println( "Server response:" + response );
                hasFlag = false;
                won = game.won(symbol);
                if(won){
                    System.out.println("Ai castigat");
                }
                System.out.println(won);
            }else{
                game.showBoard();
                System.out.println("Waiting for opponent's move...");
                response = in.readLine();
                components = response.split(" ");
                System.out.println("Opponent:"+response);
                linie = Integer.parseInt(components[1]);
                coloana = Integer.parseInt(components[2]);
                game.hit(linie,coloana, opponentSymbol);
                won = game.won( opponentSymbol );
                if( won ) System.out.println("Ai pierdut");
                hasFlag = true;

            }

        } while( !won  && !game.full() );

    }


    private int[] getMove(){
        Scanner sc = new Scanner(System.in);
        int[] positions = new int[2];
        System.out.println("Linie");
        positions[0] = sc.nextInt();
        System.out.println("Coloana");
        positions[1] = sc.nextInt();
        return positions;
    }

    private String sendUsername(){

        Scanner sc = new Scanner(System.in);
        String response;

        do {
            //String username ux.getUserName();
            String username = sc.nextLine();
            response = sendMessage( "username "+ username);

        } while( response.equals("used") );

        this.setUsername( username );

        return response;
    }

    private void initUsername(){

        String response;
        response = sendUsername();
        System.out.println(response);

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
