package src;

import java.util.*;

public class Level {
    private final int levelNumber ;
    private Tile[][] tiles;
    private boolean solved = true;

	public Level(Tile[][] tiles,int levelNumber) {
		this.tiles = tiles;
        this.levelNumber = levelNumber;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }


    public void setSolved(boolean solved) {
        this.solved = solved;
    }


/*
    public void stepByStepShuffleLevel() {
        int nLines = tiles.length;
        int nColumns = tiles[0].length;
        for(int n = 0;n<1000;n++){
            for(int i = 0;i<nLines;i++){
                for(int j = 0;j<nColumns;j++){
                    if (tiles[i][j].getValue() == 0) {
                        Random random = new Random();
                        int randomNumber;
                        int verif = 0;
                        do {
                            randomNumber = random.nextInt(4);
                            if (randomNumber == 0) {
                                if (i != 0 && tiles[i - 1][j].getValue() != -1) //check if the exchanged tile exists
                                {
                                    swapTile(i, j, i - 1, j);
                                    verif = 1;
                                }
                            } else if (randomNumber == 1) {
                                if (j != nColumns - 1 && tiles[i][j + 1].getValue() != -1) //check if the exchanged tile exists
                                {
                                    swapTile(i, j, i, j + 1);
                                    verif = 1;
                                }
                            } else if (randomNumber == 2) {
                                if (i != nLines - 1 && tiles[i + 1][j].getValue() != -1) //check if the exchanged tile exists
                                {
                                    swapTile(i, j, i + 1, j);
                                    verif = 1;
                                }
                            } else if (randomNumber == 3) {
                                if (j != 0 && tiles[i][j - 1].getValue() != -1) //check if the exchanged tile exists
                                {
                                    swapTile(i, j, i, j - 1);
                                    verif = 1;
                                }
                            }
                        } while (verif == 0);
                    }
                }
            }
        }
    }
*/
public int[][] getEmptyTiles() {
    int nLines = tiles.length;
    int nColumns = tiles[0].length;
    int numberOfEmptyTiles = 0;
    for (Tile[] row : tiles) {
        for (Tile tile : row) {
            if (tile.getValue() == 0) {
                numberOfEmptyTiles++;
            }
        }
    }
    int n = 0;
    int[][] emptyTiles = new int[numberOfEmptyTiles][2];
    int c = 0;
    for (int i = 0; i < nLines; i++) {
        for (int j = 0; j < nColumns; j++){
            if (tiles[i][j].getValue() == 0) {
                emptyTiles[c][0] = i;
                emptyTiles[c][1] = j;
                c++;
            }
        }
    }
    return emptyTiles;
}



    public void print(){
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                System.out.print(tile.getValue() + " ");
            }
            System.out.println();
        }
    }

    public Level copy() {
        Tile[][] newTiles = new Tile[tiles.length][tiles[0].length];
        for (int i = 0 ; i < tiles.length ; i++){
            for (int j = 0 ; j < tiles[0].length ; j++){
                newTiles[i][j] = new Tile(tiles[i][j].getValue());
            }
        }
        return new Level(newTiles,levelNumber);
    }

    public int getLevelNumber() {
        return levelNumber ;
    }
}

