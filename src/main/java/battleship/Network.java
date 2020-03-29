package battleship;

import java.net.*;
import java.io.*;
import java.lang.String;

public class Network {
    private ServerSocket server;
    private Socket socket;

    private ObjectInputStream inFromOtherPlayer;
    private ObjectOutputStream outToOtherPlayer;

    private BufferedReader reader;
    private PrintWriter printer;
    private Thread chatting;
    private Thread dataTransmission;

    private Cell targettedCell;

    public Network() {
    }

    //Creates a server on port 4999. Initiates data streams.
    public void hostGame() throws IOException{
        server = new ServerSocket(4999);
        socket = server.accept();

        //Setup chatting
        //Read from them
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //Send to them
        printer = new PrintWriter(socket.getOutputStream());

        //Changes GUI to start placing ships once connection is established
        Battleship.shipSelect();
        Battleship.myTurn = true;
        Battleship.console.log("It is your turn.");
        chatStart();
        dataStart();
    }

    //Searches for and joins the server hosted on port 4999. Initiates data streams in the same way as the host.
    public void joinGame() throws IOException{
        socket = new Socket("127.0.0.1", 4999);

        //Setup chatting
        //Read from them
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //Send to them
        printer = new PrintWriter(socket.getOutputStream(), true);

        //Changes GUI to start placing ships once connection is established
        Battleship.shipSelect();
        Battleship.myTurn = false;
        Battleship.console.log("It is the enemy's turn.");
        chatStart();
        dataStart();
    }

    

    //Transmits simple chat string to other player's console.
    public void sendMessage(String message) {
        printer.println("ENEMY: " + message);
        printer.flush();
    }

    //Reads input stream from other player to display in console.
    public void readMessages() {
        try {
            Battleship.console.log(reader.readLine());
        }
        catch(IOException except) {
            Battleship.console.log("ERROR: Read message failed");
        }
    }
    
    //Transmits the cell in question to the other player. Waits for response from other player.
    public void transmitCell(Cell cell) {
        try {
            Boolean outcome;
            outToOtherPlayer.writeObject(cell);
            outcome = (Boolean) inFromOtherPlayer.readObject();
            Battleship.mainBoard.updateEnemyField(cell, outcome);
        }
        catch(IOException except) {

        }
        catch(ClassNotFoundException excpet) {

        }
        
    }

    //Reads cell transmitted to player from enemy. Determines outcome and sends results.
    public void readEnemyCellTransmission() {
        try {
            targettedCell = (Cell) inFromOtherPlayer.readObject();
            if(Battleship.playerShips.checkLog(targettedCell)) {
                outToOtherPlayer.writeObject(new Boolean(true));
                Battleship.mainBoard.updatePlayerField(targettedCell, true);
            }
            else {
                outToOtherPlayer.writeObject(new Boolean(false));
                Battleship.mainBoard.updatePlayerField(targettedCell, false);
            }
        }
        catch(IOException except) {

        }
        catch(ClassNotFoundException except) {

        }

    }

    public void dataStart() {
        dataTransmission = new Thread() {
            public void run() {
                while(true) {
                    readEnemyCellTransmission();
                }
            }
        };

        try {
            outToOtherPlayer = new ObjectOutputStream(socket.getOutputStream());
            inFromOtherPlayer = new ObjectInputStream(socket.getInputStream());
        }
        catch(IOException except) {
            Battleship.console.log("ERROR: Failed to initialize transmission streams");
        }

        dataTransmission.start();
    }

    //Starts a thread that will run the communication between players. Required to not bog down the main thread.
    public void chatStart() {
        chatting = new Thread() {
            public void run() {
                while(true){
                    readMessages();
                }

            }
        };

        chatting.start();
    }
    
    public void closeGame(){
        
    }
}