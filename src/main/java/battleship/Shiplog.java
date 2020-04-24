package battleship;

public class Shiplog {

    private Cell[] carrier;
    private Cell[] battleship;
    private Cell[] submarine;
    private Cell[] cruiser;
    private Cell[] destroyer;
    private Cell[] hitCells;
    private int nextIndex;

    private boolean carrierDestroyed;
    private boolean battleshipDestroyed;
    private boolean submarineDestroyed;
    private boolean cruiserDestroyed;
    private boolean destroyerDestroyed;

    /*
    "Carrier, length 5",
    "Battleship, length 4",
    "Submarine, length 3",
    "Cruiser, length 3",
    "Destroyer, length 2"}; */

    public Shiplog(String string) {

        if(string.equals("player")) {
            //Defines how many cells each ship can take up.
            carrier = new Cell[5];
            battleship = new Cell[4];
            submarine = new Cell[3];
            cruiser = new Cell[3];
            destroyer = new Cell[5];

            //Initializes each ship array.
            clear();
        }
        else if(string.equals("enemy")) {
            hitCells = new Cell[17];
            for(int i = 0; i < 17; ++i) {
                hitCells[i] = new Cell();
            }
            nextIndex = 0;
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

    public void transmitRemainingCells() {
        for(int i = 0; i < 5; ++i) {
            if(!carrier[i].isCellHit()) {
                Battleship.network.transmitCell(carrier[i]);
            }
        }
        for(int i = 0; i < 4; ++i) {
            if(!battleship[i].isCellHit())
                Battleship.network.transmitCell(battleship[i]);
        }
        for(int i = 0; i < 3; ++i) {
            if(!submarine[i].isCellHit())
            Battleship.network.transmitCell(submarine[i]);

            if(!cruiser[i].isCellHit())
                Battleship.network.transmitCell(cruiser[i]);
            
        }
        for(int i = 0; i < 2; ++i) {
            if(!destroyer[i].isCellHit())
                Battleship.network.transmitCell(destroyer[i]);
        }
    }

    //Clears hit cells array
    public void clearHitCells() {

            for(int i = 0; i < 17; ++i) {
                hitCells[i] = new Cell();
            }
            nextIndex = 0;
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

        //Checks for overlapping.
        for(int j = 0; j < ship.getLength(); ++j) {
            for(int i = 0; i < 5; ++i) {
                if(carrier[i].equals(ship.getCell(j))) {
                    throw new Exception("Ship overlaps Carrier.");
                }
            }
    
            for(int i = 0; i < 4; ++i) {
                if(battleship[i].equals(ship.getCell(j))) {
                    throw new Exception("Ship overlaps Battleship.");
                }
            }
    
            for(int i = 0; i < 3; ++i) {
                if(submarine[i].equals(ship.getCell(j))) {
                    throw new Exception("Ship overlaps Submarine.");
                }
                else if(cruiser[i].equals(ship.getCell(j))) {
                    throw new Exception("Ship overlaps Cruiser.");
                }
            }
    
            for(int i = 0; i < 2; ++i) {
                if(destroyer[i].equals(ship.getCell(j))) {
                    throw new Exception("Ship overlaps Destroyer.");
                }
            }
        }
    }

    //Checks to see if a ship is at the named cell. If so, alerts of hit and logs it. If not, alerts of miss and logs it.
    public boolean checkLog(Cell cell) {

        for(int i = 0; i < 5; ++i) {
            if(carrier[i].equals(cell)) {
                return true;
            }
        }

        for(int i = 0; i < 4; ++i) {
            if(battleship[i].equals(cell)) {
                return true;
            }
        }

        for(int i = 0; i < 3; ++i) {
            if(submarine[i].equals(cell)) {
                return true;
            }
            else if(cruiser[i].equals(cell)) {
                return true;
            }
        }

        for(int i = 0; i < 2; ++i) {
            if(destroyer[i].equals(cell)) {
                return true;
            }
        }

        return false;
    }

    public void addHitEnemyCell(Cell cell) {
        hitCells[nextIndex] = cell;
        hitCells[nextIndex].cellGotHit();
        ++nextIndex;
    }

    public boolean checkEnemyCells(Cell cell) {
        for(int i = 0; i < 17; ++i) {
            if(hitCells[i].equals(cell)) {
                return true;
            }
        }
        return false;
    }

    public void addHitPlayerCell(Cell cell) {
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

    //Determines if a cell has been hit before.
    public boolean checkPlayerCells(Cell cell) {

        for(int i = 0; i < 5; ++i) {
            if(carrier[i].equals(cell) && carrier[i].isCellHit()) {
                return true;
            }
            
        }
        for(int i = 0; i < 4; ++i) {
            if(battleship[i].equals(cell) && battleship[i].isCellHit()) {
                return true;
            }
            
        }

        for(int i = 0; i < 3; ++i) {
            if(submarine[i].equals(cell) && submarine[i].isCellHit()) {
                return true;
            }
            else if(cruiser[i].equals(cell) && cruiser[i].isCellHit()) {
                return true;
            }
        }

        for(int i = 0; i < 2; ++i) {
            if(destroyer[i].equals(cell) && destroyer[i].isCellHit()) {
                return true;
            }
        }

        return false;
        

    }

    //Updates all whether or not a ship is sunk. Ran after every hit received. Incredibly inefficient. Returns true if all ships are sunk.
    public boolean shipStatus() {
        int index = 0;

        if(!carrierDestroyed) {
            for(int i = 0; i < 5; ++i) {
                if(carrier[i].isCellHit()) {
                    ++index;
                }
            }
            if(index == 5) {
                System.out.println("Carrier has been destroyed");
                Battleship.network.transmitInformation("DEST1");
                carrierDestroyed = true;
            }
        }

        index = 0;
        if(!battleshipDestroyed) {
            for(int i = 0; i < 4; ++i) {
                if(battleship[i].isCellHit()) {
                    ++index;
                }
            }
            if(index == 4) {
                System.out.println("Battleship has been destroyed");
                Battleship.network.transmitInformation("DEST2");
                battleshipDestroyed = true;
            }
        }

        index = 0;
        if(!submarineDestroyed) {
            for(int i = 0; i < 3; ++i) {
                if(submarine[i].isCellHit()) {
                    ++index;
                }
            }
            if(index == 3) {
                System.out.println("Submarine has been destroyed");
                Battleship.network.transmitInformation("DEST3");
                submarineDestroyed = true;
            }
        }

        index = 0;
        if(!cruiserDestroyed) {
            for(int i = 0; i < 3; ++i) {
                if(cruiser[i].isCellHit()) {
                    ++index;
                }
            }
            if(index == 3) {
                System.out.println("Cruiser has been destroyed");
                Battleship.network.transmitInformation("DEST4");
                cruiserDestroyed = true;
            }
        }

        index = 0;
        if(!destroyerDestroyed) {
            for(int i = 0; i < 2; ++i) {
                if(destroyer[i].isCellHit()) {
                    ++index;
                }
            }
            if(index == 2) {
                System.out.println("Destroyer has been destroyed");
                Battleship.network.transmitInformation("DEST5");
                destroyerDestroyed = true;
            }
        }





        if(carrierDestroyed && battleshipDestroyed && submarineDestroyed && cruiserDestroyed && destroyerDestroyed) {
            System.out.println("All ships have been destroyed");
            return true;
        }
        return false;
    }
}