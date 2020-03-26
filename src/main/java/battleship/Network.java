package battleship;

import java.net.*;
import java.io.*;

public class Network {
    ServerSocket server;
    Socket socket;

    ObjectInputStream inFromOtherPlayer;
    ObjectOutputStream outToOtherPlayer;

    OutputStream outstream;
    InputStream instream;
    BufferedReader bf;

    public Network() {


    }

    public void hostGame() throws IOException{
        server = new ServerSocket(4999);
        socket = server.accept();

        outstream = socket.getOutputStream();
        bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //Changes GUI to start placing ships once connection is established
        Battleship.shipSelect();
    }

    public void joinGame() throws IOException{
        socket = new Socket("127.0.0.1", 4999);

        //Changes GUI to start placing ships once connection is established
        Battleship.shipSelect();
    }

    public void sendMesage() {
        
    }

    public void readEnemyShips() {
        try {
            inFromOtherPlayer = new ObjectInputStream(socket.getInputStream());
            Battleship.enemyShips = (Shiplog) inFromOtherPlayer.readObject();
        }
        catch(ClassNotFoundException except) {
            Battleship.console.log("ERROR: Transmitting their ships to us failed");
        }
        catch(IOException except) {
            Battleship.console.log("ERROR: Transmitting their ships to us failed");
        }
    }

    public void sendEnemyShips() {
        
        try {
            outToOtherPlayer = new ObjectOutputStream(socket.getOutputStream());
            outToOtherPlayer.writeObject(Battleship.playerShips);
        }
        catch(IOException except) {
            Battleship.console.log("ERROR: Transmitting our ships to them failed");
        }
    }

    public void closeGame(){
        /*try {
            if(socket.isBound() || socket.isConnected()) {
                socket.close();
            }
            if(server.isBound()) {
                server.close();
            }
        }
        catch(IOException except) {
            Battleship.console.log("ERROR: Unable to close connection.");
        }*/
        
    }
}