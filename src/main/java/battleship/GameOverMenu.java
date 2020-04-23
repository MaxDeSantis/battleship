package battleship;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GameOverMenu {

    private JPanel gameOverPanel;
    private JLabel winStatusLabel;
    private JButton repeatButton;
    private JButton returnButton;

    public GameOverMenu() {

        gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new GridLayout(0, 1, 0, 20));

        winStatusLabel = new JLabel();

        repeatButton = new JButton("Play again?");
        returnButton = new JButton("Return to main menu");

        //Players opted to play again.
        repeatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(Battleship.network.theyWantRepeat) {
                    Battleship.network.transmitInformation("REPEAT2");
                    Battleship.reset();
                }
                else {
                    Battleship.network.transmitInformation("REPEAT1");
                }
            }
        });

        //Players returned to menu.
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Battleship.returnToMainMenu();
            }
        });

        gameOverPanel.add(winStatusLabel);
        gameOverPanel.add(repeatButton);
        gameOverPanel.add(returnButton);
        gameOverPanel.setVisible(false);
    }

    public void setWinLabel(boolean value) {
        if(value) {
            winStatusLabel.setText("WINNER");
        }
        else {
            winStatusLabel.setText("LOSER");
        }
        
    }

    public void gameOver(boolean value) {
        if(value) {
            gameOverPanel.setVisible(true);
        }
        else {
            gameOverPanel.setVisible(false);
        }
        
    }


    public JPanel getPanel() {
        return gameOverPanel;
    }
}