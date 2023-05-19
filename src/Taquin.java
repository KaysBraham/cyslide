package src ;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;
import java.lang.Object;

public class Taquin {
    private Level[] levels;
    private Level currentLevel;
    private Level[] unlockedLevels;
    private int moveCount;

    public void start(){
        // Create the timer and the chronometer
        Timer timer = new Timer();
        Chronometer chronometer = new Chronometer();

        chronometer.start();

        // Schedule the task to run every second
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                chronometer.updateElapsedTime(); // Update the chronometer
                display(chronometer);
            }
        }, 0, 1000);

        // the rest of the program

        // at the end
        timer.cancel();
    }
    public void display(Chronometer chronometer){}
    public void loadLevels(){}
    public void selectLevel(int currentLevel){}
    public void shuffleLevel(){}
    public void swapTile(int x1, int y1, int x2, int y2){

        int temp;
        // ensure the two chosen tiles are adjacent
        if (!(x1 == x2 && Math.abs(y1 - y2) == 1) && !(y1 == y2 && Math.abs(x1 - x2) == 1)){
            System.out.println("The two chosen tiles are not adjacent");
            return;
        }
        // ensure the tile is valid to move (at least one of its neighboring squares is empty)
        if (!(currentLevel.getTile(x2,y2).getValue() == 0) || !((currentLevel.getTile(x1,y1).getValue() != -1) && (currentLevel.getTile(x1,y1).getValue() != 0))){
            System.out.println("Invalid movement");
            return;
        }
        temp = currentLevel.getTile(x1,y1).getValue();
        currentLevel.getTile(x1,y2).setValue(currentLevel.getTile(x2,y2).getValue());
        currentLevel.getTile(x2,y2).setValue(temp);
    }


    public void checkSolvable(){}
    public void checkVictory(){}
    public void saveScore(){}

    public void unlockNextLevel(){}
    public void displayLevelSolved(){}
    public void undoMove(){}

    public int getMoveCount() {
        return moveCount;
    }
    public void showEndScreen(){}

    public static void main(String[] args) {
        String folderPath = "../data";

        FileIO fileLoader = new FileIO(folderPath);
        List<Level> levels = fileLoader.loadLevels();

//test
        for (int i = 0; i < levels.size(); i++) {
            System.out.println("Level " + (i + 1) + ":");
            Level level = levels.get(i);
            Tile[][] tiles = level.getTiles();
            for (Tile[] row : tiles) {
                for (Tile tile : row) {
                    System.out.print(tile.getValue() + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
//fin test
    }
}
