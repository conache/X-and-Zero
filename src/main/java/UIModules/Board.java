package UIModules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

class Board extends JFrame {

    private JFrame frame = new JFrame("TicTacToe");
    private JButton[][] buttons = new JButton[3][3];
    private String symbol;
    private String enemy;
    private String username;
    private ArrayList<Integer> position = new ArrayList<Integer>();
    boolean moved = false;


    public Board()                                        //Tic tac default constructor which adds and dimensions Jframe
    {
        super();
        this.symbol = symbol;
        this.enemy = enemy;
        this.username = username;
        frame.setSize(350, 450);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(false);
        frame.setResizable(false);
    }


    public void setUsername(String un) {
    }

    public void setSymbol(String symbol) {

        this.symbol = symbol;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public boolean moved(){
        return moved;
    }
    public void setMoved(){
        moved = false;
    }

    public void setMove(String s, int i, int j)
    {
        buttons[i][j].setText(s);
        buttons[i][j].setEnabled(false);

    }

    public ArrayList<Integer> getPosition()
    {
        ArrayList<Integer> temp = new ArrayList<>(position);
        position.clear();
        return temp;
    }


    public void initialize(){             //Initialize tic tac toe game board
        frame.setVisible(true);
        JPanel mainPanel = new JPanel(new BorderLayout());         //create main panel container to put layer others on top
        JPanel menu = new JPanel(new BorderLayout());
        JPanel game = new JPanel(new GridLayout(3, 3));                     //Create two more panels with layouts for buttons

        frame.add(mainPanel);                                         //add main container panel to frame

        mainPanel.setPreferredSize(new Dimension(325, 425));
        menu.setPreferredSize(new Dimension(300, 50));                     //Setting dimensions of panels
        game.setPreferredSize(new Dimension(300, 300));

        mainPanel.add(menu, BorderLayout.NORTH);                   //Add two panels to the main container panel
        mainPanel.add(game, BorderLayout.SOUTH);

        menu.add(new JLabel(username), BorderLayout.WEST);
        menu.add(new JLabel("Opponent: " + enemy), BorderLayout.EAST);

        for (int i = 0; i < 3; i++)                      //Create grid of buttons for tic tac toe game
        {
            for (int j = 0; j < 3; j++) {

                buttons[i][j] = new JButton();                //Instantiating buttons
                buttons[i][j].setText("");
                buttons[i][j].setVisible(true);

                game.add(buttons[i][j]);
                buttons[i][j].addActionListener(new myActionListener());        //Adding response event to buttons
            }
        }

    }

    private class myActionListener implements ActionListener {      //Implementing action listener for buttons
        public void actionPerformed(ActionEvent a) {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (a.getSource().equals(buttons[i][j])) {
                        buttons[i][j].setText(symbol);
                        buttons[i][j].setEnabled(false);
                        position.add(i);
                        position.add(j);
                        moved = true;
                    }
        }
    }

    public void lockButtons() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                    buttons[i][j].setEnabled(false);

    }

    public void unlockButtons() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText() == "")
                    buttons[i][j].setEnabled(true);

    }

    public void reset()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    public void exit()
    {
    //  frame.setVisible(false); //you can't see me!
      frame.dispose();
    }
}