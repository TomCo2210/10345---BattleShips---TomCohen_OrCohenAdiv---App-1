package il.ac.afeka.tomco.battleships.logic;


public class Ship {

    public enum ShipDirection {HORIZONTAL, VERTICAL}
    private ShipDirection direction;
    private int length;
    private int startLocation;
    private int color;
    private int hitCounter = 0;

    public Ship(int length, int color) {
        this.length = length;
        this.color = color;
    }

    ShipDirection getDirection() {
        return direction;
    }

    void setDirection(ShipDirection direction) {
        this.direction = direction;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    int getStartLocation() {
        return startLocation;
    }

    void setStartLocation(int startLocation) {
        this.startLocation = startLocation;
    }

    public boolean isSunk() {
        return hitCounter == length;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    void increaseHitCounter() {
        this.hitCounter++;
    }
}
