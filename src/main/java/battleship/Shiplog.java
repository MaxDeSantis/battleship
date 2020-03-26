package battleship;

public class Shiplog {

    private String[] ships;
    private int nextFreeIndex;
    private boolean isFull = false;

    public Shiplog() {
        ships = new String[17]; // At most, 17 cells can have ships in them

        for(int i = 0; i < 17; ++i) {
            ships[i] = "";
        }
        nextFreeIndex = 0;
    }

    public void addShip(String cell, boolean orientation, int length) {

        String[] shipPosition = new String[length];

        int row = 0;
        char column = Character.toUpperCase(cell.charAt(0));

        if(cell.length() == 2) {
            row = cell.charAt(1) - '0';
        }
        else if(cell.length() == 3) {
            row = (cell.charAt(1) - '0') + (cell.charAt(2) - '0');
        }

        if(orientation) {
            //Horizontal ship
            for(int i = 0; i < length; ++i) {
                shipPosition[i] = "" + column + row;
                ++column;
            }
        }
        else {
            //Vertical ship
            for(int i = 0; i < length; ++i) {
                shipPosition[i] = "" + column + row;
                ++row;
            }
        }

        for(int i = 0; i < length; ++i) {
            ships[nextFreeIndex] = shipPosition[i];
            ++nextFreeIndex;
        }



    }

    public void verifyPlacement(String cell, boolean orientation, int length) throws Exception{
        String[] shipPosition = new String[length];
        int row = 0;
        char column = 'A';

        //Converts cell into row and column formatting using char features
        column = Character.toUpperCase(cell.charAt(0));
        if(cell.length() == 2) {
            row = cell.charAt(1) - '0';
        }
        else if(cell.length() == 3) {
            row = (cell.charAt(1) - '0') + (cell.charAt(2) - '0');
        }
        else {
            throw new Exception("Invalid cell choice.");
        }

        if(column >= 'A' || column <= 'J' || row >= 1 || row <= 10) {
            int colActual = column - 'A' + 1;
            //Checks if the ship goes over the edges
            if((orientation && (colActual + length) > 10) || (!orientation && (row + length) > 10)) {
                throw new Exception("Not enough space in this location.");
            }
        }
    
        if(orientation) {
            //Horizontal ship
            for(int i = 0; i < length; ++i) {
                shipPosition[i] = "" + column + row;
                ++column;
            }
        }
        else {
            //Vertical ship
            for(int i = 0; i < length; ++i) {
                shipPosition[i] = "" + column + row;
                ++row;
            }
        }
        for(int i = 0; i < 17; ++i) {
            for(int j = 0; j < length; ++j) {
                if(ships[i].equals(shipPosition[j])) {
                    throw new Exception("Cell choice intersects existing ship.");
                }
            }
        }
    }

    //Checks to see if a ship is at the named cell. If so, alerts of hit and logs it. If not, alerts of miss and logs it.
    public void checkLog(String cell, boolean orientation) {

        //Converts cell into row and column formatting using char features
        char row = cell.charAt(1);
        char column = Character.toUpperCase(cell.charAt(0));

        try {
            for(int i = 0; i < 17; ++i) {
                if(column == ships[i].charAt(0) && row == ships[i].charAt(1)) {
                    throw new Exception("Hit at" + cell + "!");
                }
            }
            throw new Exception("Miss at" + cell + ".");
        }
        catch(Exception except) {
            Battleship.console.log(except.getMessage());
        }
    }

    public void setFull() {
        isFull = true;
    }

    public boolean isFull() {
        return isFull;
    }
}