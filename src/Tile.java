package src;
public class Tile {
    private int value;

    public Tile(int tileX, int tileY, int value) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}