package battleship;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Gamemenu {

    private JPanel gameMenu;
    private JLabel titleLabel;
    private JButton fireButton;
    private JTextField targetCell;

    public Gamemenu() {
        gameMenu = new JPanel();
        gameMenu.setLayout(new GridLayout(0, 1, 0, 20));

        titleLabel = new JLabel();
        
        updateTurnLabel();

        targetCell = new JTextField("", 3);

        fireButton = new JButton("Fire!");;

        //Sends targetted cell to other player to determine outcome.
        fireButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(Battleship.myTurn) {
                   try {
                        Cell choice = new Cell(targetCell.getText());
                        Battleship.network.transmitCell(choice);
                        Battleship.myTurn = !Battleship.myTurn;
                        updateTurnLabel();
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
        gameMenu.add(targetCell);
        gameMenu.add(fireButton);
        gameMenu.setVisible(false);
    }

    private void updateTurnLabel() {
        if(Battleship.myTurn) {
            titleLabel.setText("It is your turn");
        }
        else {
            titleLabel.setText("It is the enemy's turn");
        }
    }

    public JPanel getPanel() {
        return gameMenu;
    }

    public void setVisible(Boolean value) {
        gameMenu.setVisible(value);
        return;
    }
}