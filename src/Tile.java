package src;

import javafx.scene.control.Button;

public class Tile extends Button {
    private int value;

    public Tile(int value) {
        this.value = value;
		this.setStyle("-fx-border-radius: 0;");
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        
        final Tile otherTile = (Tile) obj;

        return this.getValue() == otherTile.getValue();
    }
}
