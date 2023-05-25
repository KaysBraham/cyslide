package src;

import javafx.scene.control.Button;

public class Tile extends Button {
    private int value;

    public Tile(int value) {
        this.value = value;
		this.setPrefSize(100, 100);
		this.setStyle("-fx-border-radius: 0;");
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
