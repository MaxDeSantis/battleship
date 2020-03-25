package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
               //FIXME Give hostbutton functionality
            }          
        });
         

        //Search for existing server when pressed
        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //FIXME Give joinbutton functionality
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