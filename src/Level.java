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
    public boolean isMoveValid(int x1, int y1, int x2, int y2){
        // ensure the two chosen tiles are adjacent
        if (!(x1 == x2 && Math.abs(y1 - y2) == 1) && !(y1 == y2 && Math.abs(x1 - x2) == 1)){
            System.out.println("The two chosen tiles are not adjacent");
            return false;
        }
        // ensure the tile is valid to move (at least one of its neighboring squares is empty)
        /*
        if (!(tiles[x2][y2].getValue() == 0) || !((tiles[x1][y1].getValue() != -1) && (tiles[x1][y1].getValue() != 0))){
            System.out.println("Invalid movement");
            return false;
        }
        */
        if (!(x1<tiles.length && x1>=0 && y1<tiles[0].length && y1>=0
                && x2<tiles.length && x2>=0 && y2<tiles[0].length && y2>=0
                && ((tiles[x1][y1].getValue()==0 && tiles[x2][y2].getValue()!=-1) || (tiles[x1][y1].getValue()!=-1 && tiles[x2][y2].getValue()==0)))){
            System.out.println("Invalid movement");
            return false;
        }
        return true;
    }

    /**
     * Swaps two tiles on the current level.
     *
     * @param x1 The x-coordinate of the first tile.
     * @param y1 The y-coordinate of the first tile.
     * @param x2 The x-coordinate of the second tile.
     * @param y2 The y-coordinate of the second tile.
     */
    public void swapTile(int x1, int y1, int x2, int y2){
        int temp;
        if(isMoveValid(x1, y1, x2, y2)){
            temp = tiles[x1][y1].getValue();
            tiles[x1][y1].setValue(tiles[x2][y2].getValue()) ;
            tiles[x2][y2].setValue(temp);
        }
    }

    public boolean checkShuffle() { // checks if all tiles are in a new place after mixing
        int nLines = tiles.length ;
        int nColumns = tiles[0].length ;
        Level solvedLevel = PuzzleGame.getLevel(this.getLevelNumber());
        for (int i = 0; i <= nLines - 1; i++) {
            for (int j = 0; j <= nColumns - 1; j++) {
                if (tiles[i][j].getValue() == solvedLevel.getTiles()[i][j].getValue() && tiles[i][j].getValue() !=-1) {
                    System.out.println("un melange mauvais");
                    return false;
                }
            }
        }
        System.out.println("Bon melange");
        return true;
    }

    public void randomShuffleLevel() {
        do {
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
            System.out.println("un melange de fait");
            solved=false;
        }while (!checkShuffle());
    }

    public void stepByStepShuffleLevel() {
        int nLines = tiles.length;
        int nColumns = tiles[0].length;
        int numberOfEmptyTiles=0;
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.getValue()==0){
                    numberOfEmptyTiles++;
                }
            }
        }
        int n = 0;
        while(n<1000){
            int[][] emptyTiles = new int[numberOfEmptyTiles][2];
            int c = 0;
            for(int i = 0;i<nLines;i++){
                for(int j = 0;j<nColumns;j++){
                    if (tiles[i][j].getValue() == 0) {
                        emptyTiles[c][0]=i;
                        emptyTiles[c][1]=j;
                        c++;
                    }
                }
            }


            Random random = new Random();
            int randomEmptyTile = random.nextInt(numberOfEmptyTiles);
            boolean isMoveDone = false;
            do {int randomMove = random.nextInt(4);
                switch (randomMove){
                    case 0 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1],emptyTiles[randomEmptyTile][0]+1,emptyTiles[randomEmptyTile][1])){
                            swapTile(emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1],emptyTiles[randomEmptyTile][0]+1,emptyTiles[randomEmptyTile][1]);
                            isMoveDone=true;
                        }
                    case 1 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1],emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1]+1)){
                            swapTile(emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1],emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1]+1);
                            isMoveDone=true;
                        }
                    case 2 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1],emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1]-1)){
                            swapTile(emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1],emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1]-1);
                            isMoveDone=true;
                        }
                    case 3 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1],emptyTiles[randomEmptyTile][0]-1,emptyTiles[randomEmptyTile][1])){
                            swapTile(emptyTiles[randomEmptyTile][0],emptyTiles[randomEmptyTile][1],emptyTiles[randomEmptyTile][0]-1,emptyTiles[randomEmptyTile][1]);
                            isMoveDone=true;
                        }
                    default:
                        break;

                }

            }while (!isMoveDone);
            print();
            if (!(n==999 && !checkShuffle())){
                n++;
            }
        }
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
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[0].length; j++){
                newTiles[i][j] = new Tile(tiles[i][j].getValue());
            }
        }
        return new Level(newTiles,levelNumber);
    }

    public int getLevelNumber() {
        return levelNumber;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        
        final Level otherLevel = (Level) obj;

        return Arrays.deepEquals(this.getTiles(), otherLevel.getTiles());
    }
}

