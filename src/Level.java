package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The Level class represents a level in a puzzle game.
 * It contains methods for managing and manipulating the tiles in the level.
 */
public class Level {
    private final int levelNumber ;
    private Tile[][] tiles;
    private boolean solved = true;

    /**
     * Constructs a Level object with the specified tiles and level number.
     *
     * @param tiles       The 2D array of Tile objects representing the level's tiles.
     * @param levelNumber The level number.
     */
	public Level(Tile[][] tiles,int levelNumber) {
		this.tiles = tiles;
        this.levelNumber = levelNumber;
	}

    /**
     * Returns the 2D array of Tile objects representing the level's tiles.
     *
     * @return The tiles of the level.
     */
	public Tile[][] getTiles() {
		return tiles;
	}

    /**
     * Sets the tiles of the level to the specified 2D array of Tile objects.
     *
     * @param T The new tiles to set.
     */
    public void setTiles(Tile[][] T) {
        this.tiles = T;
    }

    /**
     * Returns the tile at the specified coordinates.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return The tile at the specified coordinates.
     */
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    /**
     * Sets the solved state of the level.
     *
     * @param solved The new solved state.
     */
    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    /**
     * Checks if a move from the first tile to the second tile is valid.
     *
     * @param x1 The x-coordinate of the first tile.
     * @param y1 The y-coordinate of the first tile.
     * @param x2 The x-coordinate of the second tile.
     * @param y2 The y-coordinate of the second tile.
     * @return True if the move is valid, false otherwise.
     */
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

    /**
     * Checks if all tiles are in a new place after shuffling.
     *
     * @return True if the tiles are shuffled, false otherwise.
     */
    public boolean checkShuffle() {
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

    /**
     * Randomly shuffles the tiles in the level.
     */
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
        } while (!PuzzleSolver.solvePuzzle(this));
    }

    /**
     * Shuffles the tiles in the level step by step.
     */
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
                            isMoveDone = true;
                        }
                        break;
                    case 1 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1]+1)){
                        	swapTile(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1]+1);
                            isMoveDone = true;
                        }
                        break;
                    case 2 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1]-1)){
                        	swapTile(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1]-1);
                            isMoveDone = true;
                        }
                        break;
                    case 3 :
                        if (isMoveValid(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0]-1, emptyTiles[randomEmptyTile][1])){
                        	swapTile(emptyTiles[randomEmptyTile][0], emptyTiles[randomEmptyTile][1], emptyTiles[randomEmptyTile][0]-1, emptyTiles[randomEmptyTile][1]);
                            isMoveDone = true;
                        }
                        break;
                }

            } while (!isMoveDone);
            if (!(n == 999 && !checkShuffle())){
                n++;
            }
        }
    }

    /**
     * Retrieves the coordinates of the empty tiles.
     *
     * @return A 2D array containing the coordinates of the empty tiles.
     */
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

    /**
     * Prints the current state of the level.
     */
    public void print(){
        for (Tile[] row : getTiles()) {
            for (Tile tile : row) {
                System.out.print(tile.getValue() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Creates a copy of the current level.
     *
     * @return A new instance of the Level class with the same tile configuration and level number.
     */
    public Level copy() {
        Tile[][] newTiles = new Tile[getTiles().length][getTiles()[0].length];
        for (int i = 0; i < getTiles().length; i++){
            for (int j = 0; j < getTiles()[0].length; j++){
                newTiles[i][j] = new Tile(getTiles()[i][j].getValue());
            }
        }
        return new Level(newTiles,levelNumber);
    }

    /**
     * Returns the level number.
     *
     * @return The level number.
     */
    public int getLevelNumber() {
        return levelNumber;
    }

	/**
	 * Checks if the current level is equal to the specified object.
	 *
	 * @param obj The object to compare.
	 * @return True if the object is a Level instance and has the same tile configuration as the current level, false otherwise.
	 */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        
        final Level otherLevel = (Level) obj;

        return Arrays.deepEquals(this.getTiles(), otherLevel.getTiles());
    }

	/**
	 * Generates a hash code for the current level.
	 *
	 * @return The hash code value for the level based on the sum of tile values multiplied by a prime number.
	 */
    @Override
	public int hashCode() {
    	final int primeNumber = 79;
    	int result = 0;
        for (Tile[] row : getTiles())
            for (Tile tile : row)
                result += tile.getValue();
        return result * primeNumber;
	}
}
