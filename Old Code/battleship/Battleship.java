package battleship;

public class Battleship {
    public static void main( String[] args )
    {
        Menu mainMenu = new Menu();
        Gameboard playBoard = new Gameboard();
        Network network = new Network();

        //boolean gameOver = false;

        //Launches main menu of the game giving player option to network connect, play the game, or exit.
        mainMenu.launchMenu(playBoard, network);

        
    }

    public static void createShips(Gameboard board) {
        ShipBuilder buildMenu = new ShipBuilder();
        buildMenu.start(board);
    }
}