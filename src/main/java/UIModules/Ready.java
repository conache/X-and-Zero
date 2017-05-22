package UIModules;

import javax.swing.*;

/**
 * Created by Vlad Stefan on 5/22/2017.
 */
public class Ready {

    public boolean ready(String msg) {



        JFrame frame = new JFrame("Start");
        Object[] options = {"Yes, please",
                "No way!"};
        int n = JOptionPane.showOptionDialog(frame,
                msg,
                "START",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,
                options[0]);

        return n == 0 ? true : false;
    }

}
