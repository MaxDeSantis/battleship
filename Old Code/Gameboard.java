package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.GridBagLayout;

public class Gameboard {

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel enemyBoard;
    private JPanel playerBoard;
    private JPanel playerInputs;

    private JLabel titleLabel;
    private JLabel enemyBoardLabel;
    private JLabel playerBoardLabel;
    private JLabel instructionsTitleLabel;
    private JLabel instructions;

    private JLabel[][] playerFieldSpaces = new JLabel[11][11];;
    private JLabel[][] enemyFieldSpaces = new JLabel[11][11];;

    private JButton fireButton;
    private JTextField targetCell;

    private Insets insets = new Insets(10, 50, 10, 50);

    Shiplog playerShips;
    Shiplog enemyShips;

    public Gameboard() {
        playerShips = new Shiplog();
        enemyShips = new Shiplog();
    }

    public void start(Network network) {
        initPanels();

        initBoards();

        createWindow();

        fireButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               /*FIXME UNFINISHED BUTTON HERE */
            }          
         });
    }

    private void initPanels() {
        //create and init main frame
        frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create and init inner panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        //create and init players actions
        playerInputs = new JPanel();
        playerInputs.setLayout(new GridBagLayout());
        targetCell = new JTextField("", 3);
        fireButton = new JButton("Fire!");
        
        GridBagConstraints actionConstraints = new GridBagConstraints();
        actionConstraints.gridx = 0;
        actionConstraints.gridy = 0;
        
        playerInputs.add(targetCell, actionConstraints);
        
        actionConstraints.gridx = 1;
        playerInputs.add(fireButton, actionConstraints);

        //create and init labels
        titleLabel = new JLabel("BATTLESHIP", SwingConstants.CENTER);

        enemyBoardLabel = new JLabel("ENEMY FIELD", SwingConstants.CENTER);
        playerBoardLabel = new JLabel("YOUR FIELD", SwingConstants.CENTER);
        
        instructionsTitleLabel = new JLabel("YOUR ACTIONS", SwingConstants.CENTER);
        instructions = new JLabel();
        instructions.setText("<html>&lt===> is a ship.<br>Red X is a hit.<br>White O is a miss.</html>");
    }

    private void initBoards() {
        enemyBoard = new JPanel();
        enemyBoard.setLayout(new GridLayout(11, 11, 12, 12));
        playerBoard = new JPanel();
        playerBoard.setLayout(new GridLayout(11, 11, 12, 12));

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

        return;
    }

    public void getShipLocations() {
        titleLabel.setText("thing");
        return;
    }

    public void drawShip(int column, int row, boolean horizontal, int shipLength) {
        if(horizontal) {

            playerFieldSpaces[row][column].setText("<");

            for(int i = 1; i < shipLength; ++i) {
                playerFieldSpaces[row][column + i].setText("=");
            }
            playerFieldSpaces[row][column + shipLength].setText(">");
        }
        else {

            playerFieldSpaces[row][column].setText("^");

            for(int i = 1; i < shipLength; ++i) {
                playerFieldSpaces[row + i][column].setText("||");
            }
            playerFieldSpaces[row + shipLength][column].setText("v");
        }

    }

    private void createWindow() {
        GridBagConstraints mainPanelConstraints = new GridBagConstraints();
        mainPanelConstraints.insets = insets;
        mainPanelConstraints.gridx = 0;
        mainPanelConstraints.gridy = 0;
        mainPanel.add(titleLabel, mainPanelConstraints);

        mainPanelConstraints.gridy = 1;
        mainPanel.add(enemyBoardLabel, mainPanelConstraints);

        mainPanelConstraints.gridy = 2;
        mainPanel.add(enemyBoard, mainPanelConstraints);

        mainPanelConstraints.gridy = 3;
        mainPanel.add(playerBoardLabel, mainPanelConstraints);

        mainPanelConstraints.gridy = 4;
        mainPanel.add(playerBoard, mainPanelConstraints);

        mainPanelConstraints.gridy = 5;
        mainPanel.add(instructionsTitleLabel, mainPanelConstraints);

        mainPanelConstraints.gridy = 7;
        mainPanel.add(playerInputs, mainPanelConstraints);

        mainPanelConstraints.gridy = 8;
        mainPanel.add(instructions, mainPanelConstraints);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
        return;
    }

}