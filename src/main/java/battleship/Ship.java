package battleship;

public class Ship {

    private Cell shipOrigin;
    private boolean orientation; //True is horizontal
    private int length;
    private Cell[] cells;

    public Ship(Cell shipOrigin, boolean orientation, int length) throws Exception{
        cells = new Cell[length];
        this.shipOrigin = shipOrigin;
        this.orientation = orientation;
        this.length = length;

        if(orientation) {
            Battleship.console.log("Building horizontal ship");
            for(int i = 0; i < length; ++i) {
                Battleship.console.log("i = " + i + "row: " + shipOrigin.getRow() + "col: " + (shipOrigin.getColActual() + i));
                cells[i] = new Cell(shipOrigin.getRow(), shipOrigin.getColActual() + i);
                Battleship.console.log("Success");
            }
        }
        else {
            Battleship.console.log("Building vertical ship");
            for(int i = 0; i < length; ++i) {
                cells[i] = new Cell(shipOrigin.getRow() + i, shipOrigin.getColActual());
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