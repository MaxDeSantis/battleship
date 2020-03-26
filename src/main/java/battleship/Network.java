package battleship;

import java.io.IOException;
import java.net.*;

public class Network {
    ServerSocket server;
    Socket socket;


    public Network() {

    }

    public void hostGame() throws IOException{
        server = new ServerSocket(4999);
        socket = server.accept();

        Battleship.shipSelect();
    }

    public void joinGame() throws IOException{
        socket = new Socket("127.0.0.1", 4999);

        Battleship.shipSelect();
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