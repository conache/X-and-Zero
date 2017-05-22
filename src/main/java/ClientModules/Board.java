package ClientModules;

/**
 * Created by cristi on 5/22/17.
 */
public class Board {

    private char[][] board = new char[3][3];

    Board(){

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                board[i][j]='#';
            }
        }

        System.out.println("Board initialized");

    }


}
