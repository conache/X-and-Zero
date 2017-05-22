package UIModules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Vlad Stefan on 5/22/2017.
 */

public class UserNameInput {


    public String getUserName() {

        JFrame frame = new JFrame("username");

        String name = JOptionPane.showInputDialog(frame, "What's your name?");

        return name;
    }

}

