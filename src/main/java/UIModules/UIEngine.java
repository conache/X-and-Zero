package UIModules;

import java.util.ArrayList;

/**
 * Created by Vlad Stefan on 5/22/2017.
 */
public class UIEngine {

    Board board;
    UserNameInput nameInput;

    public UIEngine()
    {
        nameInput = new UserNameInput();
        board = new Board();
    }
    public String getUserName()
    {
        return nameInput.getUserName();
    }

    public void setUserName(String userName)
    {
        board.setUsername(userName);
    }

    public void setSymbol(String s)
    {
        board.setSymbol(s);
    }

    public ArrayList<Integer> getMove()
    {
        board.setMoved();
        ArrayList<Integer> result = null;

        int i = 0;
        board.unlockButtons();

        while(true) {
            System.out.print("");
            if (board.moved()) {
                result = board.getPosition();
                break;
            }
        }
        board.lockButtons();
        board.repaint();
        return result;

    }

     public void setMove(String symbol, int i, int j){

        board.setMove(symbol, i, j);
    }

    public String[][] getMatrix()
    {
        return board.getBoard();
    }

    public void init()
    {
        board.initialize();
        setMove("X",2,2);
        reset();
    }

    public void setEnemy(String enem)
    {
        board.setEnemy(enem);
    }

    public void reset()
    {
        board.reset();
    }

    public static void main(String[] args) {
        UIEngine e = new UIEngine();

        String name = e.getUserName();
        e.setUserName(name);
        e.setSymbol("0");
        e.setEnemy("enem");
        e.init();
        System.out.println(e.getMove());
        e.setMove("X",0,0);
        System.out.println(e.getMove());
        e.setMove("X",0,1);
        System.out.println(e.getMove());
        e.setMove("X",0,2);
        System.out.println(e.getMove());
        e.setMove("X",1,0);
        System.out.println(e.getMove());
        String[][] matrix = e.getMatrix();

        int a,b;
        for (a=0;a<3;a++)
        {
            for (b=0;b<3;b++)
                System.out.print(matrix[a][b] + " ");
            System.out.println();
        }


    }



}
