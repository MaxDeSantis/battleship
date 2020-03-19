
//Refactored as of 3/4/2020 7:06pm by Max DeSantis

package battleship;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Insets;



public class Gameboard {

    JFrame frame;
    JPanel board;
    JPanel actions;
    JPanel playerField;
    JPanel enemyField;

    public Gameboard() {

        
        //Main window 
        frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel titleCard = new JLabel("BATTLESHIP");
        JLabel legend = new JLabel("<html>&lt===> is a ship.<br>Red X is a hit.<br>White O is a miss.<br>Enter cell below to strike.</html>");
        JLabel messageHistory = new JLabel("You were hit on E8!");
        JTextField userEntry = new JTextField("Testing");
        
        Insets insets = new Insets(1, 1, 10, 1);
        //Overall game board
        board = new JPanel();
        board.setLayout(new GridBagLayout());

        //Leftside menu
        actions = new JPanel();
        actions.setLayout(new GridBagLayout());
        GridBagConstraints leftConstraints = new GridBagConstraints();
        leftConstraints.gridx = 0;
        leftConstraints.gridy = 0;
        leftConstraints.weightx = 0.5;
        leftConstraints.gridheight = 2;

        GridBagConstraints actionsConstraints = new GridBagConstraints();
        actionsConstraints.gridx = 0;
        actionsConstraints.gridy = 0;
        actionsConstraints.insets = insets;
        actions.add(titleCard, actionsConstraints);

        actionsConstraints.gridx = 0;
        actionsConstraints.gridy = 1;
        actions.add(legend, actionsConstraints);

        actionsConstraints.gridx = 0;
        actionsConstraints.gridy = 2;
        actions.add(userEntry, actionsConstraints);

        actionsConstraints.gridx = 0;
        actionsConstraints.gridy = 3;
        actions.add(messageHistory, actionsConstraints);
        
        board.add(actions, leftConstraints);

        /* NEEDED:
            field to enter where we want to strike
            Button to activate strike
            Label to show miss vs hit
            Label to show turn count
            */

        

        //Rightside playerField display
        playerField = new JPanel();
        playerField.setLayout(new GridLayout(11, 11, 12 ,12));
        GridBagConstraints playerConstraints = new GridBagConstraints();
        playerConstraints.gridx = 1;
        playerConstraints.gridy = 1;
        playerConstraints.weightx = 0.5;
        playerField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel[][] playerFieldSpaces = new JLabel[11][11];
        char rowLabels = 'A';
        for(int i = 0; i < 11; ++i) {
            for(int j = 0; j < 11; ++j) {
                playerFieldSpaces[i][j] = new JLabel();

                if(i == 0 && j > 0) {
                    playerFieldSpaces[i][j].setText("" + rowLabels);
                    ++rowLabels;
                }
                else if(j == 0 && i > 0) {
                    playerFieldSpaces[i][j].setText("" + i);
                }
                else {
                    playerFieldSpaces[i][j].setText("-");
                }
                playerField.add(playerFieldSpaces[i][j]);
            }
        }

        board.add(playerField, playerConstraints);
        /* 10 tall 12 wide field. Labeled 1 - 10 on left. Labeled A - J on bottom.
            Use red dot to show a hit.
            5 ship types:
            Carrier: 5 holes
            Battleship: 4 holes
            Cruiser: 3 holes
            Submarine: 3 holes
            Destroyer: 2 holes*/

        enemyField = new JPanel();
        enemyField.setLayout(new GridLayout(11, 11, 12, 12));
        GridBagConstraints enemyConstraints = new GridBagConstraints();
        enemyConstraints.gridx = 1;
        enemyConstraints.gridy = 0;
        enemyConstraints.weightx = 0.5;
        enemyField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel[][] enemyFieldSpaces = new JLabel[11][11];
        rowLabels = 'A';
        for(int i = 0; i < 11; ++i) {
            for(int j = 0; j < 11; ++j) {
                enemyFieldSpaces[i][j] = new JLabel();
                if(i == 0 && j > 0) {
                    enemyFieldSpaces[i][j].setText("" + rowLabels);
                    ++rowLabels;
                }
                else if(j == 0 && i > 0) {
                    enemyFieldSpaces[i][j].setText("" + i);
                }
                else {
    
                    enemyFieldSpaces[i][j].setText("-");
                }
                
                enemyField.add(enemyFieldSpaces[i][j]);
            }
        }




        board.add(enemyField, enemyConstraints);

        frame.add(board);
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
    }
}