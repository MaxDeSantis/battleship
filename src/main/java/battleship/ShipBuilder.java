package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.GridBagLayout;
import java.awt.Dimension;

public class ShipBuilder {
    private JFrame frame;
    private JPanel shipBuilderPanel;
    private JLabel instructionsLabel;
    private JTextField shipLocation;
    private JButton confirmShip;
    private JLabel currentShip;
    private JLabel warningLabel;
    private JButton horizontalButton;
    private boolean horizontalVal = false;

    private String[] shipDatabase = {"Carrier",
                                    "Battleship",
                                    "Submarine",
                                    "Cruiser",
                                    "Destroyer"};
    private int[] shipLengths = {4, 3, 2, 2, 1}; //add 1 to length - these are just inner components.
    private int dataBaseTracker = 0;

    public ShipBuilder() {

        frame = new JFrame("Place your ships");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        shipBuilderPanel = new JPanel();
        shipBuilderPanel.setLayout(new GridBagLayout());

        instructionsLabel = new JLabel("<html>Enter the cell of the front of the ship.<br/>Then choose if it is vertical or horizontally oriented.</html>");
        shipLocation = new JTextField("", 3);
        confirmShip = new JButton("Confirm");
        currentShip = new JLabel(shipDatabase[dataBaseTracker]);
        horizontalButton = new JButton("Vertical");
        warningLabel = new JLabel("");
        
    }

    public void start(final Gameboard board) {
        GridBagConstraints panelConstraints = new GridBagConstraints();

        panelConstraints.gridx = 0;
        panelConstraints.gridy = 0;
        panelConstraints.gridheight = 1;
        panelConstraints.gridwidth = 1;
        panelConstraints.weightx = 1;
        panelConstraints.weighty = 1;
        panelConstraints.ipadx = 30;
        panelConstraints.ipady = 10;
        //0,0 item
        shipBuilderPanel.add(currentShip, panelConstraints);
        //1, 0 item
        panelConstraints.gridx = 1;
        shipBuilderPanel.add(shipLocation, panelConstraints);
        //2, 0 item
        panelConstraints.gridx = 2;
        shipBuilderPanel.add(horizontalButton, panelConstraints);
        //0, 1 item
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 1;
        shipBuilderPanel.add(instructionsLabel, panelConstraints);
        //1, 1 item
        panelConstraints.gridx = 1;
        shipBuilderPanel.add(confirmShip, panelConstraints);
        //2, 1 item
        panelConstraints.gridx = 2;
        shipBuilderPanel.add(warningLabel, panelConstraints);

        

        horizontalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(horizontalVal) {
                    horizontalVal = false;
                    horizontalButton.setText("Vertical");
                }
                else {
                    horizontalVal = true;
                    horizontalButton.setText("Horizontal");
                }
            }
        });

        confirmShip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    if(shipLocation.getText().trim().isEmpty()) {
                        warningLabel.setText("ERROR: You must enter a cell.");
                    }
                    else {
                        draw(board, shipLocation.getText().charAt(0), Integer.parseInt(shipLocation.getText().substring(1)), horizontalVal);
                    }

                    if(dataBaseTracker > 4) {
                        closeShipBuilder();
                    }
                    else {
                        currentShip.setText(shipDatabase[dataBaseTracker]);
                        shipLocation.setText("");
                    }
                    
                
            }
         });
        frame.setMinimumSize(new Dimension(900, 130));
        frame.add(shipBuilderPanel);
        frame.pack();
        frame.setVisible(true);

        return;
    }

    public void draw(Gameboard board, char col, int row, boolean horizontal) {
            int length = shipLengths[dataBaseTracker];
            col = Character.toUpperCase(col);
            //detecting validity of entry
            if(col >= 'A' && col <= 'J' && row >= 1 && row <= 10) {
                int colActual = col - 'A' + 1;
                if(horizontal && (colActual + length) > 10) {
                    warningLabel.setText("ERROR: Ship out of bounds!");
                    return;

                }
                else if(!horizontal && (row + length) > 10) {
                    warningLabel.setText("ERROR: Ship out of bounds!");
                    return;
                }
                else {
                    board.drawShip(colActual, row, horizontal, length);
                    board.playerShips.add(shipLocation.getText());
                    dataBaseTracker++;
                }
            }
            else {
                warningLabel.setText("ERROR: Ship out of bounds!");
                return;
            }
    }

    public void closeShipBuilder() {
        frame.setVisible(false);
    }

}