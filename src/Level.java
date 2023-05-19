package src;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level {
    private Tile[][] tiles;
    private boolean solved;

    public Level(Tile[][] tiles) {
        this.tiles = tiles;
        this.solved = true;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void shuffleLevel() {
        List<Tile> tempList = new ArrayList<>();
        int nLines = tiles.length;
        int nColumns = tiles[0].length;
        for (int i = 0; i <= nLines - 1; i++) {
            for (int j = 0; j <= nColumns - 1; j++) {
                if (tiles[i][j].getValue() != -1) {
                    tempList.add(tiles[i][j]);
                }
            }
        }
        Collections.shuffle(tempList);
        int p = 0;
        for (int i = 0; i <= nLines - 1; i++) {
            for (int j = 0; j <= nColumns - 1; j++) {
                if (tiles[i][j].getValue() != -1) {
                    tiles[i][j] = tempList.get(p);
                    p += 1;
                }
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}