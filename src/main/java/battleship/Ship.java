package battleship;

public class Ship {

    private Cell shipOrigin;
    private boolean orientation; //True is horizontal
    private int length;
    private Cell[] cells;

    public Ship(Cell shipOrigin, boolean orientation, int length) {
        this.shipOrigin = shipOrigin;
        this.orientation = orientation;
        this.length = length;

        if(orientation) {
            for(int i = 0; i < length; ++i) {
                cells[i] = new Cell("" + (shipOrigin.getCol() + 1) + (shipOrigin.getRow()));
            }
        }
        else {
            for(int i = 0; i < length; ++i) {
                cells[i] = new Cell("" + (shipOrigin.getCol()) + (shipOrigin.getRow() + 1));
            }
        }
    }

    public int getRow() {
        return shipOrigin.getRow();
    }

    public int getColActual() {
        return shipOrigin.getColActual();
    }

    public char getCol() {
        return shipOrigin.getCol();
    }

    public int getLength() {
        return length;
    }

    public boolean getOrientation() {
        return orientation;
    }

    public Cell getCell(int i) {
        return cells[i];
    }
}