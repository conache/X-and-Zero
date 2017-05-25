package ClientModules;

/**
 * Created by cristi on 5/22/17.
 */
public class Game {

    private Board board;

    public Game(int lines, int columns, char symbol){
        this.board = new Board( lines, columns);
    }

    public void set(Integer linie, Integer coloana, char symbol){
        board.update(linie,coloana, symbol);
    }

    public boolean won(char symbol){
        return board.hasDiagonal(symbol) || board.hasRow(symbol) || board.hasCol(symbol) || board.hasSecDiagonal(symbol);
    }

    //verify if board matrix is full of valid symbols( not '#' )
    public boolean full() {

        char[][] b = board.getMatrixRepresentation();

        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (b[i][j] == '#') return false;
            }
        }

        return true;
    }

    public void showBoard(){
        char[][] b = board.getMatrixRepresentation();

        for( int i=0; i < b.length; i++){
            for( int j=0; j < b.length; j++){
                System.out.print( b[i][j]+" ");
            }
            System.out.println();
        }
    }

}
