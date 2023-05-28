package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    public void setTiles(Tile[][] T) {
        this.tiles = T;
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
        if (!(x1<getTiles().length && x1>=0 && y1<getTiles()[0].length && y1>=0
                && x2<getTiles().length && x2>=0 && y2<getTiles()[0].length && y2>=0
                && ((getTiles()[x1][y1].getValue()==0 && getTiles()[x2][y2].getValue()!=-1) || (getTiles()[x1][y1].getValue()!=-1 && getTiles()[x2][y2].getValue()==0)))){
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
            getTiles()[x1][y1].setValue(tiles[x2][y2].getValue()) ;
            getTiles()[x2][y2].setValue(temp);
        }
    }

    public boolean checkShuffle() { // checks if all tiles are in a new place after mixing
        int nLines = getTiles().length ;
        int nColumns = getTiles()[0].length ;
        Level solvedLevel = PuzzleGame.getLevel(this.getLevelNumber());
        for (int i = 0; i <= nLines - 1; i++) {
            for (int j = 0; j <= nColumns - 1; j++) {
                if (getTiles()[i][j].getValue() == solvedLevel.getTiles()[i][j].getValue() && getTiles()[i][j].getValue() !=-1) {
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
            do {
                List<Tile> tempList = new ArrayList<Tile>();
                int nLines = getTiles().length;
                int nColumns = getTiles()[0].length;
                for (int i = 0; i <= nLines - 1; i++) {
                    for (int j = 0; j <= nColumns - 1; j++) {
                       if (getTiles()[i][j].getValue() != -1) {
                            tempList.add(getTiles()[i][j]);
                        }
                    }
                }
                Collections.shuffle(tempList);
                int p = 0;
                for (int i = 0; i <= nLines - 1; i++) {
                    for (int j = 0; j <= nColumns - 1; j++) {
                        if (getTiles()[i][j].getValue() != -1) {
                        	getTiles()[i][j] = tempList.get(p);
                            p += 1;
                        }
                    }
                }
                solved=false;
            }while (!checkShuffle());
            if (!PuzzleSolver.solvePuzzle(this)) {
                System.out.println("Unsolvable shuffle");
            }
        }while (!PuzzleSolver.solvePuzzle(this));
    }

    public void stepByStepShuffleLevel() {
        int nLines = getTiles().length;
        int nColumns = getTiles()[0].length;
        int numberOfEmptyTiles = 0;
        for (Tile[] row : getTiles()) {
            for (Tile tile : row) {
                if (tile.getValue() == 0){
                    numberOfEmptyTiles++;
                }
            }
        }
        int n = 0;
        while(n < 1000) {
            int[][] emptyTiles = new int[numberOfEmptyTiles][2];
            int c = 0;
            for(int i = 0; i < nLines; i++){
                for(int j = 0;j<nColumns; j++){
                    if (getTiles()[i][j].getValue() == 0) {
                        emptyTiles[c][0] = i;
                        emptyTiles[c][1] = j;
                        c++;
                    }
                }
            }

            Random random = new Random();
            int randomEmptyTile = random.nextInt(numberOfEmptyTiles);
            boolean isMoveDone = false;
            do {
            	int randomMove = random.nextInt(4);
                switch (randomMove) {
                    case 0 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0]+1, emptyTiles[randomEmptyTile][1])){
                            swapTile(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0]+1, emptyTiles[randomEmptyTile][1]);
                            isMoveDone=true;
                        }
                    case 1 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1]+1)){
                            swapTile(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1]+1);
                            isMoveDone=true;
                        }
                    case 2 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1]-1)){
                            swapTile(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1]-1);
                            isMoveDone=true;
                        }
                    case 3 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0]-1, emptyTiles[randomEmptyTile][1])){
                            swapTile(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0]-1, emptyTiles[randomEmptyTile][1]);
                            isMoveDone=true;
                        }
                    default:
                        break;
                }

            } while (!isMoveDone);
            if (!(n == 999 && checkShuffle())){
                n++;
            }
        }
    }

public int[][] getEmptyTiles() {
    int nLines = getTiles().length;
    int nColumns = getTiles()[0].length;
    int numberOfEmptyTiles = 0;
    for (Tile[] row : getTiles()) {
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
            if (getTiles()[i][j].getValue() == 0) {
                emptyTiles[c][0] = i;
                emptyTiles[c][1] = j;
                c++;
            }
        }
    }
    return emptyTiles;
}



    public void print(){
        for (Tile[] row : getTiles()) {
            for (Tile tile : row) {
                System.out.print(tile.getValue() + " ");
            }
            System.out.println();
        }
    }

    public Level copy() {
        Tile[][] newTiles = new Tile[getTiles().length][getTiles()[0].length];
        for (int i = 0; i < getTiles().length; i++){
            for (int j = 0; j < getTiles()[0].length; j++){
                newTiles[i][j] = new Tile(getTiles()[i][j].getValue());
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

