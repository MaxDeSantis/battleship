package battleship;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import java.awt.*;

//FIXME Make "cell" class with methods to return row and column.
//Replace all cell functionality to reduce repetition


public class Battleship {
    //Main functionality classes
    public static Battleship battleship;
    public static Mainmenu mainMenu;
    public static Gameboard mainBoard;
    public static Console console;
    public static Gamemenu gameMenu;
    public static Shipbuildmenu buildMenu;
    public static Network network;

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
        playerShips = new Shiplog();
        enemyShips = new Shiplog();
        
        battleship = new Battleship(mainMenu, mainBoard, console, gameMenu, buildMenu);
    }

    public static void shipSelect() {
        mainMenu.setVisible(false);
        gameMenu.setVisible(false);
        buildMenu.setVisible(true);
    }

    public static void playGame() {
        mainMenu.setVisible(false);
        buildMenu.setVisible(false);

        gameMenu.setVisible(true);
    }
}