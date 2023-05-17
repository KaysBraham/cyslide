package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Taquin {
    private Level[] levels;
    private Level currentLevel;
    private Level[] unlockedLevels;
    private Stack moves;
    private int moveCount;

    public void start(){}
    public void loadLevels(){}
    public void selectLevel(int currentLevel){}

    public void moveTile(){}
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
