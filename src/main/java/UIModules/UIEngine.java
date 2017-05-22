package UIModules;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by Vlad Stefan on 5/22/2017.
 */
public class UIEngine {

    Board board;
    UserNameInput nameInput;
    Ready ready;

    public UIEngine()
    {
        nameInput = new UserNameInput();
        board = new Board();
        ready = new  Ready();
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

    public boolean ready(String enem)
    {
        return ready.ready(enem);
    }

    public void exit() {
        board.exit();
    }
}
