package ClientModules;

import UIModules.UIEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class Client implements Runnable {

    private Socket socket;
    private String username;
    private Thread th = null;
    private PrintWriter out;
    private BufferedReader in;
    private Game game;
    private char symbol;
    private char opponentSymbol;
    private UIEngine ui;
    private boolean hasFlag;
    private boolean won;

    public Client(String server, Integer serverPort) throws Exception {

        socket = new Socket(server, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.ui = new UIEngine();
        System.out.println("My ui "+this.ui);
        System.out.println(in.readLine());

       if ( initUsername() ) {
            this.th = new Thread(this);
            th.start();
       }else endGame();

    }

    public void endGame(){
        ui.exit();
        disconnectUser();
        System.out.println("Game closed");
    }

    public void setUsername(String username) {

        System.out.println("setting username to "+username);
        this.username = username;
        ui.setUserName(username);

    }

    private boolean wantsEnemy(){

        String response;

        if( ui.ready("Do you want to play against a random user?") ){

            System.out.println("Waiting for opponent...");
            response = getOpponentUsername();
            System.out.println("Set enemy to: "+response);
            ui.setEnemy(response);

            return true;

        }else{

            disconnectUser();
        }

        return false;
    }

    private void disconnectUser(){

        System.out.println("Disconnecting..");
        System.out.println( sendMessage( "disconnect user") );
        try{

            System.out.println("closed socket");
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startNewGameSession(){

        String response;

        System.out.println("Starting game...");
        response = requestStartGame();
        System.out.println(response);
        ui.init();

        try {
            playGame(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void restartGameSession(){

        String response;

        System.out.println("Starting game...");
        response = requestStartGame();
        System.out.println(response);

        ui.reset();

        try {
            playGame(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        while( wantsEnemy() ){

            startNewGameSession();
            if( !game.full() ){

                String message = game.won(symbol) ? "Congatulations, "+username+"!! You won! " : "We are sorry, "+username+" :( You lost! ";

                if(  !ui.ready(message+"Do you want to play again?") ){
                    break;
                }

            }else{

                if ( ui.ready("This game has no winner! Do you want to play again?") ) {
                    restartGameSession();
                }

            }

            System.out.println(sendMessage("replay game"));

        }

        endGame();
        System.exit(0);
    }

    private void assignUserData( String initialData){

        won = false;
        String[] components = initialData.split(" ");
        this.symbol = components[0].charAt(0);
        this.opponentSymbol = symbol == 'X' ? '0' : 'X' ;
        hasFlag = components[1].equals("flag");
        this.game = new Game( 3,3, symbol );

    }

    private void move() throws IOException {

        ArrayList<Integer> positions;
        String response;
        ui.unlockBoard();
        System.out.println("Your turn, "+ this.username );
        System.out.println("Get move for "+this.username+" "+this.ui);
        positions = this.ui.getMove();
        out.println("hit "+positions.get(0)+" "+positions.get(1)+" flag");
        game.hit( positions.get(0), positions.get(1), symbol);
        ui.setMove(String.valueOf(symbol), positions.get(0), positions.get(1));
        response = in.readLine();
        System.out.println( "Server response:" + response );
        hasFlag = false;

    }


    private void waitMove() throws IOException {

        Integer linie, coloana;
        String response;
        String[] components;

        ui.lockBoard();
        System.out.println("Waiting for opponent's move...");

        response = in.readLine();
        System.out.println("Opponent:"+response);

        components = response.split(" ");
        linie = Integer.parseInt(components[1]);
        coloana = Integer.parseInt(components[2]);

        ui.setMove(String.valueOf(opponentSymbol), linie, coloana);
        game.hit(linie,coloana, opponentSymbol);

    }

    private void playGame(String initialData ) throws IOException {

        assignUserData(initialData);


        do{
            if( hasFlag ) {
                game.showBoard();
                move();
                won = game.won(symbol);
            }else {
                game.showBoard();
                waitMove();
                won = game.won(opponentSymbol);
                hasFlag = true;

            }

        } while( !won  && !game.full() );

    }

    private String sendUsername(){

        String response;

        do {

            username = ui.getUserName();
            if( username != null) {
                response = sendMessage( "username "+ username);
            }else {
                return "game closed";
            }

        } while( response.equals("used") );

        this.setUsername( username );

        return response;
    }

    private boolean initUsername(){

        String response;
        response = sendUsername();
        System.out.println(response);
        return !response.equals("game closed");

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

            if( message.equals("disconnect user") ){
                response = "Disconnected from server";
            }else{
                e.printStackTrace();
                response = "Error reading response for message:" + message;

            }

        }

        return response;

    };


}
