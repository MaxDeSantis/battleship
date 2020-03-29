package battleship;

import javax.swing.*;
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

        //Sends targetted cell to other player to determine outcome.
        fireButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(Battleship.myTurn) {
                   try {
                        Cell choice = new Cell(targetCell.getText());
                        Battleship.network.transmitCell(choice);
                        Battleship.myTurn = !Battleship.myTurn;
                   }
                   catch(Exception except) {
                       Battleship.console.log(except.getMessage());
                   }
               }
               else {
                   Battleship.console.log("It is not your turn.");
               }

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