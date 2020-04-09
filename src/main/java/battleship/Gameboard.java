package battleship;

import javax.swing.*;
import java.awt.*;

public class Gameboard {
    private JPanel boardPanel;
    private JLabel playerLabel;
    private JLabel enemyLabel;

    private JPanel enemyBoard;
    private JPanel playerBoard;

    private JLabel[][] playerFieldSpaces = new JLabel[11][11];;
    private JLabel[][] enemyFieldSpaces = new JLabel[11][11];;

    private Insets insets = new Insets(10, 50, 10, 50);

    public Gameboard() {
        //Setup. labelBoards method creates and fills both gameboards initially.
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridBagLayout());
        labelBoards();

        //Provides proper formatting for components
        GridBagConstraints boardPanelConstraints = new GridBagConstraints();
        boardPanelConstraints.insets = insets;
        boardPanelConstraints.gridx = 0;
        boardPanelConstraints.gridy = 0;
        boardPanel.add(enemyLabel, boardPanelConstraints);

        boardPanelConstraints.gridy = 1;
        boardPanel.add(enemyBoard, boardPanelConstraints);

        boardPanelConstraints.gridy = 2;
        boardPanel.add(playerLabel, boardPanelConstraints);

        boardPanelConstraints.gridy = 3;
        boardPanel.add(playerBoard, boardPanelConstraints);
    }

    public JPanel getPanel() {
        return boardPanel;
    }

    public void clear() {
        labelBoards();
    }

    public void updatePlayerField(Cell cell, boolean hit) {
        //Creates new red X JLabel to replace previous.
        if(hit && !Battleship.playerShips.checkHitCell(cell)) {
            playerFieldSpaces[cell.getRow()][cell.getColActual()].setText("<html><font color = 'red'>X</font></html>");
            Battleship.playerShips.addHitCell(cell);
            
            Battleship.console.log("Enemy hit on " + cell.getValue());

            if(Battleship.playerShips.allHit()) {
                Battleship.network.transmitInformation("OVER");
                Battleship.lostGame();
            }
        }
        //creates new white O JLabel to replace previous.
        else {
            playerFieldSpaces[cell.getRow()][cell.getColActual()].setText("<html><font color = 'blue'>O</font></html>");
            Battleship.console.log("Enemy miss on " + cell.getValue());
        }
    }

    public void updateEnemyField(Cell cell, boolean hit) {
        if(hit && !Battleship.enemyShips.checkHitCell(cell)) {
            enemyFieldSpaces[cell.getRow()][cell.getColActual()].setText("<html><font color = 'red'>X</font></html>");
            Battleship.enemyShips.addHitCell(cell);
            Battleship.console.log("You scored a hit on " + cell.getValue());
        }
        else {
            enemyFieldSpaces[cell.getRow()][cell.getColActual()].setText("<html><font color = 'blue'>O</font></html>");
            Battleship.console.log("You missed on " + cell.getValue());
        }
    }

    public void drawShip(Ship ship) {

        //Converts cell into row and column formatting using char features
        int row = ship.getRow();
        int column = ship.getColActual();
        int length = ship.getLength();

        //Iterates differently if horizontal vs vertical. Orientation = false means vertical.
        if(ship.getOrientation()) {
            //Horizontal ship
            playerFieldSpaces[row][column].setText("<");
            for(int i = 1; i < length - 1; ++i) {
                playerFieldSpaces[row][column + i].setText("=");
            }
            playerFieldSpaces[row][column + length - 1].setText(">");
        }
        else {
            //Vertical ship
            playerFieldSpaces[row][column].setText("^");
            for(int i = 1; i < length - 1; ++i) {
                playerFieldSpaces[row + i][column].setText("||");
            }
            playerFieldSpaces[row + length - 1][column].setText("v");
        }


    }

    private void labelBoards() {
        
        enemyLabel = new JLabel("ENEMY FIELD");
        enemyBoard = new JPanel();
        enemyBoard.setLayout(new GridLayout(11, 11, 12, 12));
        enemyBoard.setBorder(BorderFactory.createLineBorder(Color.black));

        playerLabel = new JLabel("YOUR FIELD");
        playerBoard = new JPanel();
        playerBoard.setLayout(new GridLayout(11, 11, 12, 12));
        playerBoard.setBorder(BorderFactory.createLineBorder(Color.black));

        //Iterates through two 11 by 12 grids, labeling the rows and columns and placing a '-' character in each blank space
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
                
                playerBoard.add(playerFieldSpaces[i][j]);
            }
        }

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
                
                enemyBoard.add(enemyFieldSpaces[i][j]);
            }
        }
    }

}