package ClientModules;

/**
 * Created by cristi on 5/22/17.
 */
public class Board {

    private int lines;
    private int columns;

    private char[][] board;

    Board(int lines, int columns){

        this.board = new char[lines][columns];
        this.lines = lines;
        this.columns = columns;

        for(int i = 0; i < lines; i++){
            for(int j = 0; j < columns; j++){
                board[i][j]='#';
            }
        }

        System.out.println("Board initialized");

    }

    public void update(Integer linie, Integer coloana, char symbol){
        board[linie][coloana] = symbol;
    }

    // verify if it exists a column in board with equal values on it
    public boolean hasCol(char symbol){

        boolean check;

        for( int col = 0; col < columns; col++){
            check = true;

            for( int row = 1; row < lines; row++){
                if( board[row][col] != board[row-1][col] )
                    check = false;
            }

            if( check && board[0][col] == symbol ) return check;
        }

        return false;

    }

    //verify if it exists a row in board with equal values on it
    public boolean hasRow( char symbol ){

        boolean check;

        for( int row = 0; row < lines; row++){
            check = true;

            for( int col = 1; col < lines; col++){
                if ( board[row][col] != board[row][col-1] ) check = false;
            }

            if( check && board[row][0] == symbol ) return check;
        }

        return false;
    }

    //verify if there are only equal values on the first diagonal of board matrix

    public boolean hasDiagonal( char symbol ){

        for( int row = 0; row < lines; row++){
            if( board[row][row] != symbol ) return false;
        }

        return true;
    }

    //verify if there are only equals values on the second diagonal of board matrix
    public boolean hasSecDiagonal( char symbol ){

        for( int row = 0; row < lines; row++){
            if( board[row][columns-row-1] != symbol ) return false;
        }

        return true;
    }

    public char[][] getMatrixRepresentation(){
        return board;
    }

}
