package battleship;

public class Shiplog {
    public String[] ships;

    public Shiplog() {
        ships = new String[17]; //at most, 17 cells can have a ship in them.
    }

    public void add(String location) {
        int i = 0;
        while(ships[i] != "") {
            ++i;
        }
        ships[i] = location;
    }

    public boolean hitSuccessful(String location) {
        boolean success = false;
        

        return success;
    }

}