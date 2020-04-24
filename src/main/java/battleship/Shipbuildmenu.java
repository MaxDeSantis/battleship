package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Shipbuildmenu {

    //General GUI
    private JPanel buildMenu;
    private JLabel instructionsLabel;
    private JLabel currentShip;
    private JTextField shipFrontLocation;
    private JButton orientation;
    private JButton confirm;

    //Tracks whether the ship is vertical or not. True = horizontal. False = vertical.
    private boolean orientationValue = true;

    //Used to track which ship is currently being placed and how long it should be.
    private String[] shipDatabase = {"Carrier, length 5",
                                    "Battleship, length 4",
                                    "Submarine, length 3",
                                    "Cruiser, length 3",
                                    "Destroyer, length 2"};
    private int[] shipLength = {5, 4, 3, 3, 2};
    private int dataTracker = 0;

    public Shipbuildmenu() {
        buildMenu = new JPanel();
        buildMenu.setLayout(new GridLayout(0, 1, 0, 20));
        instructionsLabel = new JLabel("<html><font color = 'blue'>THE GAME HAS BEGUN</font><br>You are placing your ships.<br>(1)The cell entered is the front cell of the ship e.g. A3, C5<br>(2)Orientation is to the right<br>or below front cell.<br>(3)Confirm ship placement.</html>");
        currentShip = new JLabel("Placing ship: Carrier, length 5");
        shipFrontLocation = new JTextField("", 3);
        orientation = new JButton("Horizontal");
        confirm = new JButton("CONFIRM");

        //Swap orientation when pressed
        orientation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(orientationValue) {
                   orientation.setText("Vertical");
               }
               else {
                   orientation.setText("Horizontal");
               }
               orientationValue = !orientationValue;
            }          
        });

        buildMenu.add(instructionsLabel);
        buildMenu.add(currentShip);
        buildMenu.add(shipFrontLocation);
        buildMenu.add(orientation);
        buildMenu.add(confirm);
        buildMenu.setVisible(false);

        //Draw newly defined ship when pressed
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buildShip(); 
            }          
        });
    }

    public void clear() {
        dataTracker = 0;
        currentShip.setText("Placing Ship: Carrier, length 5");
        orientation.setText("Horizontal");
        orientationValue = true;
    }

    //Will verify location to be placed, draw the ship on the board, and update the Shiplog
    public void buildShip() {

        try {
            Ship ship = new Ship(new Cell(shipFrontLocation.getText()), orientationValue, shipLength[dataTracker]);
            Battleship.playerShips.verifyPlacement(ship);
            Battleship.playerShips.addShip(ship, dataTracker);
            Battleship.mainBoard.drawShip(ship);

            dataTracker = dataTracker + 1; //Keeps track of which ship we're on
            shipFrontLocation.setText("");

            if(dataTracker > 4) {
                Battleship.playGame();
            }
            else {
                currentShip.setText("Placing ship: " + shipDatabase[dataTracker]);
            }
            
        }
        
        catch(Exception except) {
            Battleship.console.log("ERROR " + except.getMessage());
        }
    }

    public JPanel getPanel() {
        return buildMenu;
    }

    public void setVisible(Boolean value) {
        buildMenu.setVisible(value);
    }
}