package src ;
import java.util.* ;
import java.lang.Math ;
import java.lang.Object ;

public class Taquin {
    private Level[] levels;
    private Level currentLevel;
    private Level[] unlockedLevels;
    private int moveCount;

    public void start(){}
    public void loadLevels(){}
    public void selectLevel(int currentLevel){}
    public void shuffleLevel(){}
    public void swapTile(int x1, int y1, int x2, int y2){

        int temp;
        //check if the two cases chosen are adjacent
        if ((x1 == x2 && Math.abs(y1 - y2) == 1) || (y1 == y2 && Math.abs(x1 - x2) == 1)) {
        //check if the tile is valid to move (at least one of its neighboring squares is empty)
            if ((currentLevel.getTile(x2,y2).getValue() == 0) && ((currentLevel.getTile(x1,y1).getValue() != -1) && (currentLevel.getTile(x1,y1).getValue() != 0))){
                temp = currentLevel.getTile(x1,y1).getValue();
                currentLevel.getTile(x1,y2).setValue(currentLevel.getTile(x2,y2).getValue());
                currentLevel.getTile(x2,y2).setValue(temp);
            }
            else {
                 System.out.println("Impossible movement");
            }
        }
        else {
            System.out.println("The two chosen cases are not adjacent");
        }
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
}
