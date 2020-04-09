package battleship;

public class Shiplog {

    private Cell[] takenCells;
    private Cell[] hitCells;
    private int nextFreeIndex;

    public Shiplog() {
        takenCells = new Cell[17]; // At most, 17 cells can have ships in them
        hitCells = new Cell[17];
        for(int i = 0; i < 17; ++i) {
            takenCells[i] = new Cell();
            hitCells[i] = new Cell();
        }
        nextFreeIndex = 0;
    }

    //This method adds the ship to the list of taken up cells.
    public void addShip(Ship ship) {

        for(int i = 0; i < ship.getLength(); ++i) {
            takenCells[nextFreeIndex] = ship.getCell(i);
            ++nextFreeIndex;
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
            if(cell.getColActual() == takenCells[i].getColActual() && cell.getRow() == takenCells[i].getRow()) {
                value = true;
            }
        }

        return value;
    }

    public void resetIndex() {
        nextFreeIndex = 0;
    }

    public void addHitCell(Cell cell) {
        hitCells[nextFreeIndex] = cell;
        ++nextFreeIndex;
    }

    public boolean checkHitCell(Cell cell) {
        
        boolean value = false;
        for(int i = 0; i < 17; ++i) {
            if(hitCells[i].equals(cell)) {
                value = true;
            }
        }
        return value;
    }

    public boolean allHit() {
        boolean value = false;
        if(nextFreeIndex > 16) {
            value = true;
        }
        return value;
    }
}