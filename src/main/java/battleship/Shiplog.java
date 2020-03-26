package battleship;

public class Shiplog {

    private String[] ships;

    public Shiplog() {
        ships = new String[17]; // At most, 17 cells can have ships in them
    }

    public void addShip(String cell, boolean orientation) {

    }

    public void verifyPlacement(String cell, boolean orientation) throws Exception{
        for(int i = 0; i < 17; ++i) {
            
        }
    }

    //Checks to see if a ship is at the named cell. If so, alerts of hit and logs it. If not, alerts of miss and logs it.
    public void checkLog(String cell, boolean orientation) {
        try {
            for(int i = 0; i < 17; ++i) {
                if(cell.charAt(0) == ships[i].charAt(0) && cell.charAt(1) == ships[i].charAt(1)) {
                    throw new Exception("Hit at" + cell + "!");
                }
            }
            throw new Exception("Miss at" + cell + ".");
        }
        catch(Exception except) {
            Battleship.console.log(except.getMessage());
        }
    }
}