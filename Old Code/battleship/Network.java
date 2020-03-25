package battleship;

import java.net.*;
import java.io.*;

public class Network {
    Shiplog enemyShips = new Shiplog();
    public Network() {
    }

    public void launchHost() throws IOException{
        ServerSocket server = new ServerSocket(4999);
        Socket s = server.accept();

        



        s.close();
        server.close();

    }

    public void findHost() throws IOException{
        Socket s = new Socket("127.0.0.1", 4999);
        




        s.close();
    }

    public Shiplog getEnemyShips() {
        return enemyShips;
    }
}