package battleship;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import java.awt.*;

/* 
To-Dos as of 4/6/2020 1:46pm:
Improve program response when one player disconnects but other doesn't.
Turn JoinGame into it's own thread.
Implement win conditions.
Implement "play again" button.
Put "exit" button on every page. Maybe include with gameboard rather than mainmenu?
Implement "return to main menu" button.
Improve "your turn" notification.
*/


public class Battleship {
    //Main functionality classes
    public static Battleship battleship;
    public static Mainmenu mainMenu;
    public static Gameboard mainBoard;
    public static Console console;
    public static Gamemenu gameMenu;
    public static Shipbuildmenu buildMenu;
    public static Network network;
    public static NetworkMenu networkMenu;

    //Managing overall frame
    private static JFrame mainFrame;
    private static Insets insets = new Insets(2, 5, 2, 5);

    //Required game logic
    public static Shiplog playerShips;
    public static Shiplog enemyShips;
    public static boolean gameOver = false;
    public static boolean myTurn;

    public Battleship(Mainmenu menu, Gameboard board, Console console, Gamemenu gameMenu, Shipbuildmenu shipBuilder) {
        
        mainFrame = new JFrame("Battleship");
        mainFrame.setLocation(200, 200);
        mainFrame.setLayout(new GridBagLayout());
        
        //Creating constraints for the overall frame
        GridBagConstraints constraints = new GridBagConstraints();

        //Managing layout of the menu portion
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = insets;
        constraints.weighty = 0.5;
        constraints.weightx = 0.5;
        mainFrame.add(menu.getPanel(), constraints);
        mainFrame.add(gameMenu.getPanel(), constraints);
        mainFrame.add(shipBuilder.getPanel(), constraints);
        mainFrame.add(networkMenu.getPanel(), constraints);

        //Managing layout of the console portion
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainFrame.add(console.getPanel(), constraints);

        //Managing layout of the gameboard portion
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.gridwidth = 2;
        constraints.weighty = 1;
        constraints.weightx = 0.5;
        mainFrame.add(board.getPanel(), constraints);

        //Setting proper procedure for exiting the program
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mainMenu.closeGame();
            }
        });

        //Showing the window to the user
        mainFrame.setMinimumSize(new Dimension(700, 800));
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    public static void main(String[] args) {
        mainMenu = new Mainmenu();
        mainBoard = new Gameboard();
        console = new Console();
        gameMenu = new Gamemenu();
        buildMenu = new Shipbuildmenu();
        network = new Network();
        networkMenu = new NetworkMenu();
        playerShips = new Shiplog();
        enemyShips = new Shiplog();
        
        battleship = new Battleship(mainMenu, mainBoard, console, gameMenu, buildMenu);
    }

    public static void wonGame() {
        console.log("You win!");
        gameMenu.gameOver(true);
    }

    public static void lostGame() {
        console.log("You lost!");
        gameMenu.gameOver(true);
        
    }

    public static void reset() { //FIXME update this to properly function.
        mainBoard.clear();
        //network.reset();
        //playerShips.clear();
        //enemyShips.clear();
        shipSelect();
    }

    public static void returnToMainMenu() {
        network.closeConnections();
        gameMenu.setVisible(false);
        networkMenu.setVisible(false);
        buildMenu.setVisible(false);
        mainMenu.setVisible(true);
    }

    public static void shipSelect() {
        mainMenu.setVisible(false);
        gameMenu.setVisible(false);
        networkMenu.setVisible(false);
        buildMenu.setVisible(true);
    }

    public static void networkMenu() {
        mainMenu.setVisible(false);
        gameMenu.setVisible(false);
        buildMenu.setVisible(false);
        networkMenu.setVisible(true);
    }

    public static void playGame() {
        network.transmitInformation("READY");
        mainMenu.setVisible(false);
        buildMenu.setVisible(false);
        networkMenu.setVisible(false);
        gameMenu.setVisible(true);
    }
}