package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Mainmenu {

    private JPanel menuPanel;
    private JLabel titleLabel;

    private JButton hostButton;
    private JButton joinButton;
    private JButton exitButton;

    public Mainmenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1));
        
        titleLabel = new JLabel("Battleship!");

        hostButton = new JButton("TEST GAME");
        joinButton = new JButton("JOIN GAME");
        exitButton = new JButton("EXIT");

        //Create server when pressed
        hostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               try {
                    Battleship.console.log("Attempting to join game...");
                    Battleship.network.hostGame();
               }
               catch(IOException except) {
                    Battleship.console.log("ERROR: Hosting game failed. Please try again.");
               }
            }          
        });
         

        //Search for existing server when pressed
        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               try {
                    Battleship.console.log("Attempting to join game...");
                    Battleship.network.joinGame();
               }
               catch (IOException except) {
                    Battleship.console.log("ERROR: Joining game failed. Please try again.");
               }
            }          
        });

        //Shutdown program when pressed.
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }          
        });


        menuPanel.add(titleLabel);
        menuPanel.add(hostButton);
        menuPanel.add(joinButton);
        menuPanel.add(exitButton);
    }

    public JPanel getPanel() {
        return menuPanel;
    }

    public void setVisible(Boolean value) {
        menuPanel.setVisible(value);
    }


}