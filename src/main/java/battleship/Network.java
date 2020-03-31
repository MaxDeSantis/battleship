package battleship;

import java.net.*;
import java.io.*;
import java.lang.String;

public class Network {
    private ServerSocket server;
    private Socket socket;

    private ServerSocket dataServer;
    private Socket dataSocket;

    private final String ip = "127.0.0.1";

    private ObjectInputStream inFromOtherPlayer;
    private ObjectOutputStream outToOtherPlayer;

    private BufferedReader reader;
    private PrintWriter printer;
    private Thread chatting;
    private Thread dataTransmission;
    private Thread cellTransmission;

    private Object inputData;
    private boolean host;
    private boolean connection;
    public boolean ready = false;
    private boolean waiting = false;
    private Cell transmitCell;
    

    public Network() {
    }

    //Creates a server on port 4999. Initiates data streams.
    public void hostGame() throws IOException{
        //Sockets used for chatting
        server = new ServerSocket(4999);
        socket = server.accept();

        //Sockets used for data transmission
        dataServer = new ServerSocket(5000);
        dataSocket = dataServer.accept();

        System.out.println("Hosted successfully");

        //Setup chatting
        //Read from them
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //Send to them
        printer = new PrintWriter(socket.getOutputStream());

        //Setup data streams
        outToOtherPlayer = new ObjectOutputStream(dataSocket.getOutputStream());
        inFromOtherPlayer = new ObjectInputStream(dataSocket.getInputStream());

        //Changes GUI to start placing ships once connection is established
        Battleship.shipSelect();
        Battleship.myTurn = true;
        Battleship.console.log("It is your turn.");
        host = true;
        connection = true;
        chatStart();
        cellTransmissionStart();
        //dataStart();
    }

    //Searches for and joins the server hosted on port 4999. Initiates data streams in the same way as the host.
    public void joinGame() throws IOException{
        socket = new Socket(ip, 4999);
        dataSocket = new Socket(ip, 5000);
        System.out.println("Joined successfully");

        //Setup chatting
        //Read from them
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //Send to them
        printer = new PrintWriter(socket.getOutputStream(), true);

        //Setup data stream
        outToOtherPlayer = new ObjectOutputStream(dataSocket.getOutputStream());
        inFromOtherPlayer = new ObjectInputStream(dataSocket.getInputStream());

        //Changes GUI to start placing ships once connection is established
        Battleship.shipSelect();
        Battleship.myTurn = false;
        Battleship.console.log("It is the enemy's turn.");
        host = false;
        connection = true;
        chatStart();
        //dataStart();
        cellTransmissionStart();
    }

    

    //Transmits simple chat string to other player's console.
    public void sendMessage(String message) {
        printer.println("ENEMY: " + message);
        printer.flush();
    }

    //Reads input stream from other player to display in console.
    public void readMessages() {
        try {
            //receivedMessage = reader.readLine();
            Battleship.console.log(reader.readLine());
        }
        catch(IOException except) {
            Battleship.console.log("ERROR: Read message failed");
        }
    }
    
    //Transmits the cell in question to the other player. Waits for response from other player.
    public void transmitCell(Cell cell) {
        try {
            outToOtherPlayer.writeObject(cell);
            System.out.println("Cell written to other player");
            Battleship.myTurn = !Battleship.myTurn;
        }
        catch(IOException except) {
            System.out.println("IOException in transmitCell");
            except.printStackTrace();
        }
    }

    //Reads cell transmitted to player from enemy. Determines outcome and sends results.
    public void readEnemyCellTransmission() {
        try {
            inputData = inFromOtherPlayer.readObject();

            if(inputData instanceof Boolean && waiting) {
                Boolean outcome;
                outcome = (Boolean) inputData;
                Battleship.console.log("Outcome read: " + outcome.booleanValue());
                Battleship.mainBoard.updateEnemyField(transmitCell, outcome.booleanValue());
                System.out.println("Board updated");
                waiting = false;
            }
            else if(inputData instanceof Cell) {
                transmitCell = (Cell) inputData;

                if(Battleship.playerShips.checkLog(transmitCell)) {
                    Battleship.console.log("Cell hit");
                    Battleship.mainBoard.updatePlayerField(transmitCell, true);
                    outToOtherPlayer.writeObject(new Boolean(true));
                    waiting = true;
                    Battleship.myTurn = !Battleship.myTurn;
                }
                else {
                    Battleship.console.log("Cell not hit");
                    outToOtherPlayer.writeObject(new Boolean(false));
                    Battleship.mainBoard.updatePlayerField(transmitCell, false);
                }
            }
            
            else if(inputData instanceof String) {
                String information = (String) inputData;
                Battleship.console.log(information);
            }
            /*if(Battleship.playerShips.checkLog(targettedCell)) {
                System.out.println("Cell hit");
                outToOtherPlayer.writeObject(new Boolean(true));
                Battleship.mainBoard.updatePlayerField(targettedCell, true);
            }
            else {
                System.out.println("Cell not hit");
                outToOtherPlayer.writeObject(new Boolean(false));
                Battleship.mainBoard.updatePlayerField(targettedCell, false);
            } */
        }
        catch(IOException except) {
            System.out.println("IOException in readEnemyCellTransmission");
            except.printStackTrace();

            playerExited();
        }
        catch(ClassNotFoundException except) {
            System.out.println("ClassNotFoundException in readEnemyCellTransmission");

        }

    } 

    /*public void sendData(String value) {
        try {
            outToOtherPlayer.writeObject(value);
        }
        catch(IOException except) {

        }
    } */

    /*public void receiveData() {
        String value;
        try {
            value = (String) inFromOtherPlayer.readObject();

            if(value.equals("EXIT")) {
                playerExited();
            }
            else if(value.equals("READY")) {
                ready = true;
            }
        }
        catch(IOException except) {

        }
        catch(ClassNotFoundException except) {

        }
    } */

    public void cellTransmissionStart() {
        cellTransmission = new Thread() {
            public void run() {
                System.out.println("Cell transmission started");
                while(true) {
                    readEnemyCellTransmission();
                }
            }
        };

        cellTransmission.start();
    }

    /*public void dataStart() {
        
        dataTransmission = new Thread() {
            public void run() {
                System.out.println("Data transmission started");
                while(true) {
                    receiveData();
                }
            }
        };

        dataTransmission.start();
    } */

    //Starts a thread that will run the communication between players. Required to not bog down the main thread.
    public void chatStart() {
        
        chatting = new Thread() {
            public void run() {
                System.out.println("Chat started");
                while(true){
                    readMessages();
                }

            }
        };

        chatting.start();
    }

    public void playerExited() {
            dataTransmission.interrupt();
            chatting.interrupt();
            cellTransmission.interrupt();
            try {
                if(host) {
                    
                    server.close();
                }
                socket.close();
                
            }
            catch(IOException except) {
                Battleship.console.log("Error closing sockets");
            }
    }
    
    public void closeGame(){
        if(connection) {
            //sendData("EXIT");
            //dataTransmission.interrupt();
            chatting.interrupt();
            cellTransmission.interrupt();
            try {
                if(host) {
                    
                    server.close();
                }
                socket.close();
                
            }
            catch(IOException except) {
                Battleship.console.log("Error closing sockets");
            }
        }
        
    }
}