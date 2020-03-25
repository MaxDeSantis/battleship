package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Shipbuildmenu {
    private JPanel buildMenu;
    private JLabel instructionsLabel;
    private JTextField shipFrontLocation;
    private JButton orientation;
    private JButton confirm;

    private boolean orientationValue = false; //false means vertical. True means horizontal. Used a boolean for code simplicity over readability.

    public Shipbuildmenu() {
        buildMenu = new JPanel();
        buildMenu.setLayout(new GridLayout(0, 1));
        instructionsLabel = new JLabel("<html>You are placing your ships.</br>(1)Enter the cell of the front of the ship e.g. A3, C5</br>(2)Choose orientation of the ship.</br>(3)Confirm ship placement.</html>");
        shipFrontLocation = new JTextField("", 3);
        orientation = new JButton("Horizontal");
        confirm = new JButton("CONFIRM");

        //Swap orientation when pressed
        orientation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(orientationValue) {
                   
                   orientation.setText("Horizontal");
               }
               else {
                   orientation.setText("Vertical");
               }
               orientationValue = !orientationValue;
            }          
        });

        //Draw newly defined ship when pressed
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               try {
                    //FIXME create confirm action.
               }
               catch(Exception except) {
                    Battleship.console.log(except.getMessage() + "Please try again.");
               }
            }          
        });


        buildMenu.add(instructionsLabel);
        buildMenu.add(shipFrontLocation);
        buildMenu.add(orientation);
        buildMenu.add(confirm);
        buildMenu.setVisible(false);
    }

    public JPanel getPanel() {
        return buildMenu;
    }

    public void setVisible(Boolean value) {
        buildMenu.setVisible(value);
    }
}