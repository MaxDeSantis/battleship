package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gamemenu {

    private JPanel gameMenu;
    private JLabel titleLabel;
    private JButton fireButton;
    private JTextField targetCell;

    private Insets insets = new Insets(1, 1, 1, 1);

    public Gamemenu() {
        gameMenu = new JPanel();

        titleLabel = new JLabel("Battleship!");

        fireButton = new JButton("Fire GAME");;

        //Create server when pressed
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