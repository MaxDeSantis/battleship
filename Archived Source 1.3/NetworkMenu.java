package battleship;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.GridLayout;

public class NetworkMenu {

    private JPanel networkPanel;
    private JLabel ipLabel;
    private JTextField ipTextField;
    private JButton confirmButton;
    private String ip;

    public NetworkMenu() {

        networkPanel = new JPanel();
        networkPanel.setLayout(new GridLayout(0, 1, 0, 20));

        ipLabel = new JLabel("Enter IP of other player:");

        ipTextField = new JTextField("");
        confirmButton = new JButton("Confirm IP");

        //Sends targetted cell to other player to determine outcome.
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ip = ipTextField.getText();
                Battleship.network.setIP(ip);
                    Battleship.console.log("Attempting to join game...");
                    Battleship.network.joinGame();
            }          
        });

        networkPanel.add(ipLabel);
        networkPanel.add(ipTextField);
        networkPanel.add(confirmButton);
        networkPanel.setVisible(false);

    }

    public void cancelJoin() {
        Battleship.console.log("<ERROR>IP unreachable.");
    }

    public void setVisible(boolean value) {
        networkPanel.setVisible(value);
    }

    public JPanel getPanel() {
        return networkPanel;
    }

    public void clearTextField() {
        ipTextField.setText("");
    }
}