package ClientModules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by cristi on 5/22/17.
 */
public class Game {
    private static Game game;
    private Board board;
    private char symbol;
    private char opponentSymbol;

    private Game(){
        this.board = new Board();
    }

    public static Game instance(){

        if ( game == null )
            return new Game();
        return game;

    }

    //add ux object
    public void start(PrintWriter out, BufferedReader in, char symbol){
        this.opponentSymbol = symbol == 'X' ? '0' : 'X';

    }
//
//
//    void parseOponentMessage(String message){
//
//    }
//
//    public void hit(){
//
//        System.out.println("Get hit from fe");
//        //ux.getMove();
//        //update hit in local matrix
//        //send hit to server
//
//    }
//
//    public void getHit(){
//
//    }
//
//    public void playGame(String flagMessage) throws IOException {
//
//        String response;
//
//        if( flagMessage.toLowerCase().trim().equals("flag") ) {
//            hit();
//
//        }else{
//            getHit();
//        }
//
//
//    }

}
