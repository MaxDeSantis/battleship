package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mainmenu {

    private JPanel menuPanel;
    private JLabel titleLabel;

    public JButton hostButton;
    public JButton joinButton;
    private JButton exitButton;

    public Mainmenu() {

        //Overall GUI
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1, 0, 20));
        titleLabel = new JLabel("Battleship!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Button initialization
        hostButton = new JButton("HOST GAME");
        joinButton = new JButton("JOIN GAME");
        exitButton = new JButton("EXIT");

        //Create server when pressed
        hostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hostButton.setEnabled(false);
                Battleship.console.log("Attempting to host game...");
                Battleship.network.hostGame();
            }          
        });
         

        //Search for existing server when pressed
        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    Battleship.networkMenu();
            }          
        });

        //Shutdown program when pressed.
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeGame();
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

    public void closeGame() {
        if(Battleship.network.connection) {
            Battleship.network.transmitInformation("EXIT");
            Battleship.network.closeConnections();
        }
        
        System.exit(0);
    }
}