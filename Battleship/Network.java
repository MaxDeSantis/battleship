package battleship;

import java.net.*;
import java.io.*;
import java.lang.String;

public class Network {
    //Main sockets used depending on if host or client.
    //Chatting sockets, port 4999
    private ServerSocket server;
    private Socket socket;
    //Data sockets, port 5000
    private ServerSocket dataServer;
    private Socket dataSocket;

    private ObjectInputStream inFromOtherPlayer;
    private ObjectOutputStream outToOtherPlayer;

    //Used for chatting and data transmission
    private BufferedReader reader;
    private PrintWriter printer;
    private Thread chatting;
    private Thread cellTransmission;
    private Thread hostGameThread;
    private Thread joinGameThread;

    //Objects to faciliate said data transmission
    private Object inputData;
    private Cell transmitCell;
    private Cell outcomeCell;

    //Used for internal logic
    private String otherPlayerIP = "localhost";
    private String receivedMsg;
    private boolean host;
    public boolean connection;
    public boolean theyWantRepeat = false;

    public Network() {
    }

    //Changes IP to player choice. Use "localhost" to connect to self.
    public void setIP(String ip){
        this.otherPlayerIP = ip;
    }

    //Thread just for accepting sockets - allows user to still exit game.
    //Creates a server on port 4999. Initiates data streams on port 5000.
    public void hostGame(){
        hostGameThread = new Thread() {
            public void run() {
                try {
                    Battleship.mainMenu.joinButton.setEnabled(false);

                    //Setup sockets for chat
                    server = new ServerSocket(4999);
                    socket = server.accept();

                    //Sockets used for data transmission
                    dataServer = new ServerSocket(5000);
                    dataSocket = dataServer.accept();

                    //Enables in game logic.
                    host = true;
                    connection = true;

                    //Debugging
                    System.out.println("Hosted successfully");

                    //Setup chatting
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    printer = new PrintWriter(socket.getOutputStream());

                    //Setup data streams
                    outToOtherPlayer = new ObjectOutputStream(dataSocket.getOutputStream());
                    inFromOtherPlayer = new ObjectInputStream(dataSocket.getInputStream());

                    //Changes GUI to start placing ships once connection is established
                    Battleship.shipSelect();
                    Battleship.myTurn = true;
                    Battleship.gameMenu.updateTurnLabel();
                    Battleship.mainMenu.hostButton.setEnabled(true);
                    //Start chat and data threads.
                    chatStart();
                    cellTransmissionStart();

                    //Debugging
                    System.out.println("I am the host");
                }

                catch(IOException except) {
                    System.out.println("Hosting failed");
                    except.printStackTrace();
                    Battleship.mainMenu.joinButton.setEnabled(true);
                }
            }
        };

        //Starts thread.
        hostGameThread.start();
        hostGameThread.setName("Host Game Thread");
    }

    //Searches for and joins the server hosted on port 4999. Initiates data streams in the same way as the host.
    public void joinGame() throws IOException{
        joinGameThread = new Thread() {
            public void run() {
                try {
                    //Setup sockets
                    socket = new Socket(otherPlayerIP, 4999);
                    dataSocket = new Socket(otherPlayerIP, 5000);
                    System.out.println("Joined successfully");

                    //Enabled logic
                    host = false;
                    connection = true;
            
                    //Setup chatting
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    printer = new PrintWriter(socket.getOutputStream(), true);
            
                    //Setup data stream
                    outToOtherPlayer = new ObjectOutputStream(dataSocket.getOutputStream());
                    inFromOtherPlayer = new ObjectInputStream(dataSocket.getInputStream());
            
                    //Changes GUI to start placing ships once connection is established
                    Battleship.shipSelect();
                    Battleship.myTurn = false;
                    Battleship.gameMenu.updateTurnLabel();

                    Battleship.console.log("Player connected");
            
                    //Start chat and data threads.
                    chatStart();
                    cellTransmissionStart();

                    //Debugging
                    System.out.println("I am not the host");
                }
                catch(IOException except) {
                    except.printStackTrace();
                }
                
            }
        };

        //Starts thread.
        joinGameThread.start();
        joinGameThread.setName("Join Game Thread");
        
    }

    //Transmits simple chat string to other player's console.
    public void sendMessage(String message) {
        printer.println("<ENEMY>" + message);
        printer.flush();
    }

    //Reads input stream from other player to display in console.
    public void readMessages() {
        try {
            receivedMsg = reader.readLine();
            if(!receivedMsg.equals(null)) {
                Battleship.console.log(reader.readLine());
            }
        }
        catch(IOException except) {
            System.out.println("ERROR: Read message failed");
            playerExited();
        }
    }
    
    //Transmits the cell in question to the other player. Waits for response from other player.
    public void transmitCell(Cell cell) {

        //Send cell to other player.
        try {
            outToOtherPlayer.writeObject(cell);
            System.out.println("Cell written to other player");
            outcomeCell = cell;

            //Update turn label.
            Battleship.myTurn = false;
            Battleship.gameMenu.updateTurnLabel();
        }

        //Failed in transmitting cell.
        catch(IOException except) {
            System.out.println("IOException in transmitCell");
            except.printStackTrace();
            playerExited();
        }
    }

    //Sends a string of information to other player. Uses predetermined codes such as "EXIT", "READY", "OVER", "HIT", "MISS".
    public void transmitInformation(String string) {
        try {
            outToOtherPlayer.writeObject(string);
        }
        catch (IOException except) {
            System.out.println("IOException in transmitInfo");
            except.printStackTrace();
        }
    }

    //Reads cell transmitted to player from enemy. Determines outcome and sends results.
    public void readEnemyCellTransmission() {
        try {
            inputData = inFromOtherPlayer.readObject();

            //If a cell was transmitted, enemy made an attack.
            if(inputData instanceof Cell) {
                transmitCell = (Cell) inputData;

                //Self explanatory - updates turn label each round
                outcomeCell = transmitCell;
                Battleship.myTurn = true;
                Battleship.gameMenu.updateTurnLabel();

                //Check if it's a hit or miss. Result sent back to other player.
                if(Battleship.playerShips.checkLog(transmitCell)) {
                    Battleship.mainBoard.updatePlayerField(transmitCell, true);
                    outToOtherPlayer.writeObject(new String("HIT"));

                    if(Battleship.playerShips.allHit()) {
                        Battleship.network.transmitInformation("OVER");
                        Battleship.lostGame();
                    }
                    
                }
                else {
                    Battleship.mainBoard.updatePlayerField(transmitCell, false);
                    outToOtherPlayer.writeObject(new String("MISS"));
                }
                
            }
            
            //A cell was not transmitted, so check if it was an information string.
            else if(inputData instanceof String) {
                String information = (String) inputData;

                //Variety of outcomes depending on information value.
                if(information.equals("HIT")) {
                    Battleship.mainBoard.updateEnemyField(outcomeCell, true);
                }

                //You missed on the enemy.
                else if(information.equals("MISS")) {
                    Battleship.mainBoard.updateEnemyField(outcomeCell, false);
                }

                //The enemy exited the game.
                else if(information.equals("EXIT")) {
                    System.out.println("Other client exited");
                    playerExited();
                    System.out.println("playerExited method executed");
                }
                
                //The enemy has selected their ships and is ready to start.
                else if(information.equals("READY")) {
                    Battleship.gameMenu.setReady();
                }

                //The game is over and you won the game.
                else if(information.equals("OVER")) {
                    Battleship.wonGame();
                }

                //Other player opted to play again.
                else if(information.equals("REPEAT1")) {
                    Battleship.console.log("Other player wants to play again");
                    theyWantRepeat = true;

                }

                //Other player agreed to play again.
                else if(information.equals("REPEAT2")) {
                    Battleship.reset();

                }

                //Error handling
                else {
                    System.out.println("String unidentifiable in readCellTransmission");
                }
            }

            //The transmission was neither a Cell nor a String object.
            else {
                Battleship.console.log("No instanceof");
            }
        }

        //Something went wrong with transmissions.
        catch(IOException except) {
            System.out.println("IOException in readEnemyCellTransmission");
            except.printStackTrace();
            closeConnections();
        }
        //Something went wrong with transmissions.
        catch(ClassNotFoundException except) {
            System.out.println("ClassNotFoundException in readEnemyCellTransmission");
        }
    }

    //Starts a thread that reads data for cells and information from other player.
    public void cellTransmissionStart() {
        cellTransmission = new Thread() {
            public void run() {
                System.out.println("Cell transmission started");
                while(connection) {
                    readEnemyCellTransmission();
                }
                System.out.println("Stopped reading info");
            }
        };

        cellTransmission.start();
        cellTransmission.setName("Cell Transmission Thread");
    }

    //Starts a thread that will run the communication between players. Required to not bog down the main thread.
    public void chatStart() {
        
        chatting = new Thread() {
            public void run() {
                System.out.println("Chat started");
                while(connection) {
                    readMessages();
                }
                System.out.println("stopped reading chat");
            }
        };

        chatting.start();
        chatting.setName("Chatting thread");
    }

    //Runs when the other player exits, facilities safe closing.
    public void playerExited() {
        if(connection) {
            closeConnections();
            Battleship.returnToMainMenu();
            System.out.println("ran 'return to main menu' method");
            Battleship.console.log("Other player has exited the game.");
        }
    }

    //Try to safely close all sockets and stream objects.
    public void closeConnections(){
        if(connection) {
            
            try {
                if(host) {
                    server.close();
                    dataServer.close();
                    host = false;
                }

                socket.close();
                dataSocket.close();

                reader.close();
                printer.close();
                inFromOtherPlayer.close();
                outToOtherPlayer.close();
                connection = false;
            }

            //Failed to close something.
            catch(IOException except) {
                System.out.println("Error closing sockets");
                except.printStackTrace();
            }
        }
        
    }
}