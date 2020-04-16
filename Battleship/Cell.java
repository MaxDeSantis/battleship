package battleship;

public class Cell implements java.io.Serializable{

    private static final long serialVersionUID = 1L; //Version id for Serializable. Should change when making incompatible changes.
    private int row;
    private char col;
    private int colActual;

    public Cell() {
        row = 0;
        col = 0;
        colActual = 0;
    }

    public Cell(int row, int colActual) {
        this.row = row;
        this.colActual = colActual;
        this.col = (char) (colActual + 'A' - 1);
    }

    public Cell(String cell) throws Exception{
            col = Character.toUpperCase(cell.charAt(0));
            colActual = col - 'A' + 1;
    
            row = Integer.parseInt(cell.substring(1));

            if(col >= 'A' && col <= 'J') {
            }
            else {
                throw new Exception("Invalid column.");
            }
            if(row <= 10 && row >= 1) {
                
            }
            else {
                throw new Exception("Invalid row.");
            }
    }
    
    public String getValue() {
        return ("" + col + row);
    }

    public int getRow() {
        return row;
    }

    public int getColActual() {
        return colActual;
    }

    public char getCol() {
        return col;
    }

    public boolean equals(Cell compareCell) {
        boolean value;
        if(this.row == compareCell.getRow() && this.colActual == compareCell.getColActual()) {
            value = true;
        }
        else {
            value = false;
        }

        return value;

    }
}