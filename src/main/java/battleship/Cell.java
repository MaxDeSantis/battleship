package battleship;

public class Cell {

    private int row;
    private char col;
    private int colActual;

    public Cell() {
        row = 0;
        col = 0;
        colActual = 0;
    }

    public Cell(String cell){
        try {
            col = Character.toUpperCase(cell.charAt(0));

            colActual = col - 'A' + 1;
    
            row = Integer.parseInt(cell.substring(1));

            if(col < 'A' || col > 'J') {
                throw new Exception("Invalid column.");
            }
            if(row > 10 || row < 1) {
                throw new Exception("Invalid row.");
            }
        }
        catch(Exception except) {
            Battleship.console.log(except.getMessage());
        }
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