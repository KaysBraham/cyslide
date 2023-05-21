package src;

import java.util.*;

public class Level {
    private Tile[][] tiles;
    private boolean solved;

	public Level(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void swapTile(int x1, int y1, int x2, int y2){

        int temp;
        // ensure the two chosen tiles are adjacent
        if (!(x1 == x2 && Math.abs(y1 - y2) == 1) && !(y1 == y2 && Math.abs(x1 - x2) == 1)){
            System.out.println("The two chosen tiles are not adjacent");
            return;
        }
        // ensure the tile is valid to move (at least one of its neighboring squares is empty)
        if (!(tiles[x2][y2].getValue() == 0) || !((tiles[x1][y1].getValue() != -1) && (tiles[x1][y1].getValue() != 0))){
            System.out.println("Invalid movement");
            return;
        }
        temp = tiles[x1][y1].getValue();
        tiles[x1][y1].setValue(tiles[x2][y2].getValue()) ;
        tiles[x2][y2].setValue(temp);
    }

    public void randomShuffleLevel() {
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


    public void stepByStepShuffleLevel() {
        int nLines = tiles.length;
        int nColumns = tiles[0].length;
        int i = 0;
        int j = 0;
        int n = 0;
        while (n < 1000) {
            i = 0;
            while (i != nLines) {
                j = 0;
                while (j != nColumns) {
                    if (tiles[i][j].getValue() == 0) {
                        
                        /*if (i == 0) {
                            swapTile(i, j, i + 1, j);
                        } else if (i == nLines - 1) {
                            swapTile(i, j, i - 1, j);
                        } else if (j == 0) {
                            swapTile(i, j, i, j + 1);
                        } else if (j == nColumns - 1) {
                            swapTile(i, j, i, j - 1);
                        } else {*/

                            Random random = new Random();
                            int nb;
                            int verif = 0;
                            do {
                                nb = random.nextInt(4);
                                if (nb == 0) {
                                    if (i != 0 && tiles[i - 1, j].getValue() != -1) //check if the exchanged tile exists
                                    {
                                        swapTile(i, j, i - 1, j);
                                        verif = 1;
                                    }
                                } else if (nb == 1) {
                                    if (j != nColumns - 1 && tiles[i, j + 1].getValue() != -1) //check if the exchanged tile exists
                                    {
                                        swapTile(i, j, i, j + 1);
                                        verif = 1;
                                    }
                                } else if (nb == 2) {
                                    if (i != nLines - 1 && tiles[i + 1, j].getValue() != -1) //check if the exchanged tile exists
                                    {
                                        swapTile(i, j, i + 1, j);
                                        verif = 1;
                                    }
                                } else if (nb == 3) {
                                    if (j != 0 && tiles[i, j - 1].getValue() != -1) //check if the exchanged tile exists
                                    {
                                        swapTile(i, j, i, j - 1);
                                        verif = 1;
                                    }
                                }
                            } while (verif == 0)
                        //}
                        n += 1 ;
                    }
                    j += 1;
                }
                i += 1;
            }
        }
    }

    public boolean checkShuffle() { // checks if all tiles are in a new place after mixing
        int nLines = tiles.length ;
        int nColumns = tiles[0].length ;
        int compt = 1 ;
        for (int i = 0; i <= nLines - 1; i++) {
            for (int j = 0; j <= nColumns - 1; j++) {
                if (tiles[i][j].getValue() == compt) {
                    return false;
                } else if (tiles[i][j].getValue() != compt) {
                    compt += 1;
                }
            }
        }
        return true;
    }

}

