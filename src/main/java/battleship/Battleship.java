package battleship;

import javax.swing.*;
import java.awt.*;


public class Battleship {
    //Main functionality classes
    public static Battleship battleship;
    public static Mainmenu mainMenu;
    public static Gameboard mainBoard;
    public static Console console;
    public static Gamemenu gameMenu;

    //Managing overall frame
    private static JFrame mainFrame;
    private static Insets insets = new Insets(10, 20, 10, 20);

    public Battleship(Mainmenu menu, Gameboard board, Console console, Gamemenu gameMenu) {
        mainFrame = new JFrame("Battleship");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(200, 200);
        mainFrame.setLayout(new GridBagLayout());
        
        //Creating constraints for the overall frame
        GridBagConstraints constraints = new GridBagConstraints();

        //Managing layout of the menu portion
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = insets;
        mainFrame.add(menu.getPanel(), constraints);
        mainFrame.add(gameMenu.getPanel(), constraints);

        //Managing layout of the console portion
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainFrame.add(console.getPanel(), constraints);

        //Managing layout of the gameboard portion
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.gridwidth = 2;
        mainFrame.add(board.getPanel(), constraints);

        //Showing the window to the user
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    public static void main(String[] args) {
        mainMenu = new Mainmenu();
        mainBoard = new Gameboard();
        console = new Console();
        gameMenu = new Gamemenu();
        
        battleship = new Battleship(mainMenu, mainBoard, console, gameMenu);
    }
}