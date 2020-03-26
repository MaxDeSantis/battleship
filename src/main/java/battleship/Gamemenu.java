package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gamemenu {

    private JPanel gameMenu;
    private JLabel titleLabel;
    private JButton fireButton;
    private JTextField targetCell;

    public Gamemenu() {
        gameMenu = new JPanel();

        titleLabel = new JLabel("Battleship!");

        fireButton = new JButton("Fire GAME");;

        //Checks to see if a ship is in location selected, then notifies of result
        fireButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //FIXME Give hostbutton functionality
            }          
        });

        gameMenu.add(titleLabel);
        gameMenu.add(fireButton);
        gameMenu.setVisible(false);

    }

    public JPanel getPanel() {
        return gameMenu;
    }

    public void setVisible(Boolean value) {
        gameMenu.setVisible(value);
        return;
    }
}