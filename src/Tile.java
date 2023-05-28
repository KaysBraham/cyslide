package src;

import javafx.scene.control.Button;

/**
 * Represents a square tile in the game.
 */
public class Tile extends Button {
    private int value; // The value of the tile

    /**
     * Constructs a new tile with the specified value.
     *
     * @param value The value of the tile.
     */
    public Tile(int value) {
        this.value = value;
		this.setStyle("-fx-border-radius: 0;");
    }

    /**
     * Returns the value of the tile.
     *
     * @return The value of the tile.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the tile.
     *
     * @param value The new value of the tile.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Checks if this tile is equal to another object. Two tiles are considered equal
     * if their values are equal.
     *
     * @param obj The object to compare.
     * @return True if the tiles are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        
        final Tile otherTile = (Tile) obj;

        return this.getValue() == otherTile.getValue();
    }
}
