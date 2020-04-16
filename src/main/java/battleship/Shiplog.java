package battleship;

public class Shiplog {

    private Cell[] carrier;
    private Cell[] battleship;
    private Cell[] submarine;
    private Cell[] cruiser;
    private Cell[] destroyer;

    /*
    "Carrier, length 5",
    "Battleship, length 4",
    "Submarine, length 3",
    "Cruiser, length 3",
    "Destroyer, length 2"}; */

    public Shiplog() {
        //Defines how many cells each ship can take up.
        carrier = new Cell[5];
        battleship = new Cell[4];
        submarine = new Cell[3];
        cruiser = new Cell[3];
        destroyer = new Cell[5];

        //Initializes each ship array.
        for(int i = 0; i < 5; ++i) {
            carrier[i] = new Cell();
        }
        for(int i = 0; i < 4; ++i) {
            battleship[i] = new Cell();
        }
        for(int i = 0; i < 3; ++i) {
            submarine[i] = new Cell();
            cruiser[i] = new Cell();
        }
        for(int i = 0; i < 2; ++i) {
            destroyer[i] = new Cell();
        }
    }

    //This clears the logs to work as new.
    public void clear() {

        for(int i = 0; i < 5; ++i) {
            carrier[i] = new Cell();
        }
        for(int i = 0; i < 4; ++i) {
            battleship[i] = new Cell();
        }
        for(int i = 0; i < 3; ++i) {
            submarine[i] = new Cell();
            cruiser[i] = new Cell();
        }
        for(int i = 0; i < 2; ++i) {
            destroyer[i] = new Cell();
        }

    }

    //This method adds the ship to the list of taken up cells.
    public void addShip(Ship ship, int shipNumber) {

        switch(shipNumber) {
            case 0: 
                for(int i = 0; i < 5; ++i) {
                    carrier[i] = ship.getCell(i);
                }
                break;

            case 1:
                for(int i = 0; i < 4; ++i) {
                    battleship[i] = ship.getCell(i);
                }
                break;

            case 2:
                for(int i = 0; i < 3; ++i) {
                    submarine[i] = ship.getCell(i);
                }
                break;

            case 3:
                for(int i = 0; i < 3; ++i) {
                    cruiser[i] = ship.getCell(i);
                }
                break;

            case 4:
                for(int i = 0; i < 2; ++i) {
                    destroyer[i] = ship.getCell(i);
                }
                break;

            default:
            break;
        }
    }

    //This method checks to see if the ship actually fits on the board. No hanging off the edge or overlapping with other ships.
    public void verifyPlacement(Ship ship) throws Exception{
        int length = ship.getLength();
        int row = ship.getRow();
        int column = ship.getColActual();
        boolean orientation = ship.getOrientation();

        //Checks if hanging off the edge.
        if((orientation && (column + length) > 11) || (!orientation && (row + length) > 11)) {
            throw new Exception("Not enough space in this location.");
        }
        
        //Checks if overlapping
        for(int i = 0; i < 17; ++i) {
            for(int j = 0; j < length; ++j) {
                if(takenCells[i].equals(ship.getCell(j))) {
                    throw new Exception("Ship overlapping other ship.");
                }
            }
        }
    }

    //Checks to see if a ship is at the named cell. If so, alerts of hit and logs it. If not, alerts of miss and logs it.
    public boolean checkLog(Cell cell) {
        boolean value = false;

        for(int i = 0; i < 17; ++i) {
            if(cell.getColActual() == takenCells[i].getColActual() && cell.getRow() == takenCells[i].getRow()) { //FIXME 
                value = true;
            }
        }

        return value;
    }

    public void addHitCell(Cell cell) {
        for(int i = 0; i < 5; ++i) {
            if(carrier[i].equals(cell)) {
                carrier[i].cellGotHit();
                return;
            }
        }

        for(int i = 0; i < 4; ++i) {
            if(battleship[i].equals(cell)) {
                battleship[i].cellGotHit();
                return;
            }
        }

        for(int i = 0; i < 3; ++i) {
            if(submarine[i].equals(cell)) {
                submarine[i].cellGotHit();
                return;
            }
            else if(cruiser[i].equals(cell)) {
                cruiser[i].cellGotHit();
                return;
            }
        }

        for(int i = 0; i < 2; ++i) {
            if(destroyer[i].equals(cell)) {
                destroyer[i].cellGotHit();
                return;
            }
        }

        return;
    }

    public boolean checkHitCell(Cell cell) {

        for(int i = 0; i < 5; ++i) {
            if(carrier[i].isCellHit()) {
                return true;
            }
        }

        for(int i = 0; i < 4; ++i) {
            if(battleship[i].isCellHit()) {
                return true;
            }
        }

        for(int i = 0; i < 3; ++i) {
            if(submarine[i].isCellHit()) {
                return true;
            }
            else if(cruiser[i].isCellHit()) {
                return true;
            }
        }

        for(int i = 0; i < 2; ++i) {
            if(destroyer[i].isCellHit()) {
                return true;
            }
        }

        return false;
        

    }

    public boolean allHit() { //FIXME
        boolean value = false;
        
        return value;
    }
}