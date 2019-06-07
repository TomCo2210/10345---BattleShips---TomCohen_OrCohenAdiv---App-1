package il.ac.afeka.tomco.battleships.logic;


import java.util.Random;

public class Board {
    private int shipSunk = 0;
    private int shipsOnBoard = 0;

    public enum TileState {
        EMPTY, HIT, MISS, SHIP, INVALID;

        @Override
        public String toString() {
            switch (this) {
                case EMPTY:
                default:
                    return "";
                case HIT:
                    return "HIT";
                case MISS:
                    return "MISS";
                case SHIP:
                    return "SHIP";
                case INVALID:
                    return "INVALID";
            }
        }
    }

    private Tile mTiles[];

    public Board(int boardSize) {
        mTiles = new Tile[boardSize];
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i] = new Tile();
        }
    }

    public int getBoardSize() {
        return mTiles.length;
    }

    public Tile getTile(int position) {
        return mTiles[position];
    }

    boolean checkTile(int position) {
        if (mTiles[position].getStatus() == TileState.EMPTY) {
            mTiles[position].setStatus(TileState.MISS);
            return true;
        } else if (mTiles[position].getStatus() == TileState.SHIP) {
            mTiles[position].setStatus(TileState.HIT);
            mTiles[position].getShipAssigned().increaseHitCounter();
            if (mTiles[position].getShipAssigned().isSunk())
                increaseShipSunk();
            return true;
        } else return false;
    }

    public void randomizeBoard(Ship[] ships) {
        int rowSize = (int) Math.sqrt(mTiles.length);
        Random random = new Random();
        int x;
        int y;
        Ship.ShipDirection[] directions = Ship.ShipDirection.values();
        for (Ship ship : ships) {
            int position;
            do {
                ship.setDirection(directions[random.nextInt(directions.length)]);

                if (ship.getDirection() == Ship.ShipDirection.HORIZONTAL) {

                    x = random.nextInt((rowSize - ship.getLength()) + 1);
                    y = random.nextInt(rowSize);
                    position = x + (rowSize * y);

                } else {
                    x = random.nextInt(rowSize);
                    y = random.nextInt((rowSize - ship.getLength()) + 1);
                    position = x + (rowSize * y);
                }
            }
            while (!checkShipPositionsEmpty(position, rowSize, ship.getLength(), ship.getDirection()));
            ship.setStartLocation(position);
            if (ship.getDirection() == Ship.ShipDirection.HORIZONTAL)
                for (int i = 0; i < ship.getLength(); i++) {
                    mTiles[ship.getStartLocation() + i].setStatus(TileState.SHIP);
                    mTiles[ship.getStartLocation() + i].setShipAssigned(ship);
                }
            else
                for (int i = 0; i < ship.getLength(); i++) {
                    mTiles[ship.getStartLocation() + i * rowSize].setStatus(TileState.SHIP);
                    mTiles[ship.getStartLocation() + i * rowSize].setShipAssigned(ship);
                }
            increaseShipsOnBoard();
        }
    }

    private boolean checkShipPositionsEmpty(int position, int rowSize, int length, Ship.ShipDirection direction) {
        for (int i = 0; i < length; i++)
            if (direction == Ship.ShipDirection.HORIZONTAL) {
                if (mTiles[position + i].getStatus() != TileState.EMPTY)
                    return false;
            } else if (mTiles[position + (i * rowSize)].getStatus() != TileState.EMPTY)
                return false;
        return true;
    }

    private void increaseShipSunk() {
        this.shipSunk++;
    }

    int getShipSunk() {
        return shipSunk;
    }

    private void increaseShipsOnBoard() {
        this.shipsOnBoard++;
    }

    int getShipsOnBoard() {
        return shipsOnBoard;
    }
}
