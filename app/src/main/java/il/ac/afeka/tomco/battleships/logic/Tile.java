package il.ac.afeka.tomco.battleships.logic;

public class Tile {

    private Ship shipAssigned = null;

    private Board.TileState mStatus;

    Tile() {
        mStatus = Board.TileState.EMPTY;
    }

    public Board.TileState getStatus() {
        return mStatus;
    }

    public void setStatus(Board.TileState status) {
        this.mStatus = status;
    }

    public Ship getShipAssigned() {
        return shipAssigned;
    }

    void setShipAssigned(Ship shipAssigned) {
        this.shipAssigned = shipAssigned;
    }
}
