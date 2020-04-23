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
    private JLabel enemyShips;
    private JLabel carrier;
    private JLabel battleship;
    private JLabel submarine;
    private JLabel cruiser;
    private JLabel destroyer;


    public Gamemenu() {
        gameMenu = new JPanel();
        gameMenu.setLayout(new GridLayout(0, 1, 0, 10));

        readyLabel = new JLabel("Waiting on other player...");
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

                        if(!Battleship.enemyShips.checkEnemyCells(choice)) {
                            System.out.println("Checkhit cell false");
                            Battleship.network.transmitCell(choice);
                            targetCell.setText("");
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

        //Format buttons for UX
        fireButton.setEnabled(false);
        titleLabel.setVisible(false);

        //Labels for which ships have been destroyed
        enemyShips = new JLabel("Enemy Ships Remaining:");
        carrier = new JLabel("<html><font color = 'green'>Carrier</font></html>");
        battleship = new JLabel("<html><font color = 'green'>Battleship</font></html>");
        submarine = new JLabel("<html><font color = 'green'>Submarine</font></html>");
        cruiser = new JLabel("<html><font color = 'green'>Cruiser</font></html>");
        destroyer = new JLabel("<html><font color = 'green'>Destroyer</font></html>");

        //Add to main panel.
        gameMenu.add(readyLabel);
        gameMenu.add(titleLabel);
        gameMenu.add(targetCell);
        gameMenu.add(fireButton);

        gameMenu.add(enemyShips);
        gameMenu.add(carrier);
        gameMenu.add(battleship);
        gameMenu.add(submarine);
        gameMenu.add(cruiser);
        gameMenu.add(destroyer);

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

    public void setLabel(String label) {
        titleLabel.setText(label);
    }

    //Strike out label if you've destroyed one of their ships.
    public void destroy(String ship) {
        if(ship.equals("carrier")) {
            Battleship.console.log("Enemy carrier destroyed.");
            carrier.setText("<html><font color = 'red'><s>Carrier</s></fonnt></html>");
        }
        if(ship.equals("battleship")) {
            Battleship.console.log("Enemy battleship destroyed");
            battleship.setText("<html><font color = 'red'><s>Battleship</s></font></html>");
        }
        if(ship.equals("submarine")) {
            Battleship.console.log("Enemy submarine destroyed");
            submarine.setText("<html><font color = 'red'><s>Submarine</s></font></html>");
        }
        if(ship.equals("cruiser")) {
            Battleship.console.log("Enemy cruiser destroyed");
            cruiser.setText("<html><font color = 'red'><s>Cruiser</s></font></html>");
        }
        if(ship.equals("destroyer")) {
            Battleship.console.log("Enemy destroyer destroyed");
            destroyer.setText("<html><font color = 'red'><s>Destroyer</s></font></html>");
        }
    }

    //Formats GUI for end of game options.
    public void gameOver(boolean displayButtons) {
        gameMenu.setVisible(false);
    }
}