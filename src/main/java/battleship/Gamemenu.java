package battleship;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Gamemenu {

    private JPanel gameMenu;
    private JLabel readyLabel;
    private JLabel titleLabel;
    private JButton fireButton;
    private JTextField targetCell;
    private JButton repeatButton;
    private JButton returnButton;

    public Gamemenu() {
        gameMenu = new JPanel();
        gameMenu.setLayout(new GridLayout(0, 1, 0, 20));

        readyLabel = new JLabel("Waiting on other player...");
        titleLabel = new JLabel();
        
        updateTurnLabel();

        targetCell = new JTextField("", 3);

        fireButton = new JButton("Fire!");;

        repeatButton = new JButton("Play again?");
        returnButton = new JButton("Return to main menu");

        //Sends targetted cell to other player to determine outcome.
        fireButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(Battleship.myTurn) {
                   try {
                        Cell choice = new Cell(targetCell.getText());

                        if(!Battleship.enemyShips.checkHitCell(choice)) {
                            Battleship.network.transmitCell(choice);
                            updateTurnLabel();
                        }
                        else {
                            Battleship.console.log("You've already hit that cell.");
                        }
                        
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

        //Players opted to play again.
        repeatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Battleship.network.wantToPlayAgain = true;
                Battleship.network.transmitInformation("REPEAT");
            }
        });

        //Players returned to menu.
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Battleship.returnToMainMenu();
            }
        });

        //Format buttons for UX
        returnButton.setVisible(false);
        repeatButton.setVisible(false);
        fireButton.setEnabled(false);
        titleLabel.setVisible(false);

        //Add to main panel.
        gameMenu.add(readyLabel);
        gameMenu.add(titleLabel);
        gameMenu.add(targetCell);
        gameMenu.add(fireButton);
        gameMenu.setVisible(false);
    }

    public void updateTurnLabel() {
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

    public void setReady() {
        readyLabel.setVisible(false);
        titleLabel.setVisible(true);
        fireButton.setEnabled(true);
    }

    //Formats GUI for end of game options.
    public void gameOver(boolean displayButtons) {
        if(displayButtons) {
            fireButton.setEnabled(false);
            titleLabel.setText("LOSER");
            returnButton.setVisible(true);
            repeatButton.setVisible(true);
        }
        else {
            fireButton.setEnabled(true);
            returnButton.setVisible(false);
            repeatButton.setVisible(false);
        }
    }
}