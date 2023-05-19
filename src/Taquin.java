package src ;
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
}
