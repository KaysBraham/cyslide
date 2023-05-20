package src;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.io.FileNotFoundException;
import java.lang.Math;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.PauseTransition;

public class Taquin extends Application {
    private Level[] levels;
    private Level currentLevel;
    private Level[] unlockedLevels;
    private int moveCount;
    private Stage primaryStage;
    private Scene homeScene;
    private Timer timer = null;
    private Label levelLabel;


    public static int getLevel() {
        return level;
    }
    public static int level = 1;
    
    public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Scene getHomeScene() {
		return homeScene;
	}

	public void setHomeScene(Scene homeScene) {
		this.homeScene = homeScene;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Label getLevelLabel() {
		return levelLabel;
	}

	public void setLevelLabel(Label levelLabel) {
		this.levelLabel = levelLabel;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		setPrimaryStage(primaryStage);
		
		setLevelLabel(new Label("level: " + getLevel())); //to print level
		getLevelLabel().setStyle("-fx-text-fill: white;-fx-font-size: 18;-fx-font-family: 'Leoscar'");
		
		Button startButton = new Button("New game");
		startButton.setOnAction(e -> startGame());
		
		Button setDifficultyButton = new Button("Difficulty settings");
		setDifficultyButton.setOnAction(e -> openDifficultySettings());
		
		Button showLeaderboardButton = new Button("Leaderboard");
		showLeaderboardButton.setOnAction(e -> openLeaderboard());
		
		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> Platform.exit());
		
		List<Button> buttons = Arrays.asList(startButton, setDifficultyButton, showLeaderboardButton, exitButton);
		for(Button button : buttons) {
			button.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leoscar'");
			button.setOnMousePressed(event -> {
				button.setStyle("-fx-border-color: black; -fx-background-color: grey;"); // to make grey transparent
	            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1)); // during 0.1 second
	            pauseTransition.setOnFinished(e -> button.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leoscar'"));//to remove the transparent
	            pauseTransition.play();
	        });
		}
		
		VBox homeLayout = new VBox(48); // Big spacing
		homeLayout.setAlignment(Pos.CENTER);
		homeLayout.setStyle("-fx-background-color: #00a8c4;");
		homeLayout.getChildren().addAll(buttons);
		
		setHomeScene(new Scene(homeLayout, 640, 480)); // VGA scene
		getPrimaryStage().setScene(getHomeScene());
		getPrimaryStage().show();
	}

	@Override
	public void stop(){
		if(getTimer() != null) getTimer().cancel(); // at least 1 game has been started. Subsequent calls of cancel() have no effect.
    	getPrimaryStage().setScene(getHomeScene());
	}

    public void startGame(){

    	VBox playLayout = new VBox(32);
    	playLayout.setAlignment(Pos.CENTER);
    	playLayout.setStyle("-fx-background-color: #00a8c4;");
        
        HBox topLayout = new HBox(64);
        // TODO undo, redo etc.
        
        GridPane gridLayout = new GridPane();
        // TODO tiles
    	
        setTimer(new Timer());
        Chronometer chronometer = new Chronometer();
        // TODO chronometer (HBox)
        
        Button giveUpButton = new Button("Give up");
        giveUpButton.setStyle("-fx-font-size:32");
        giveUpButton.setOnAction(e -> {
        	getTimer().cancel();
        	getPrimaryStage().setScene(getHomeScene());
        });
        
        HBox bottomLayout = new HBox(64);
        bottomLayout.getChildren().add(chronometer);
        bottomLayout.getChildren().add(giveUpButton);

        playLayout.getChildren().add(topLayout);
        playLayout.getChildren().add(gridLayout);
        playLayout.getChildren().add(bottomLayout);
    	
    	Scene playScene = new Scene(playLayout, 1600, 900); // HD+ scene
    	
        getPrimaryStage().setScene(playScene);

        chronometer.start();

        // Schedule the task to run every second
        getTimer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                chronometer.updateElapsedTime(); // Update the chronometer
                display(chronometer);
            }
        }, 0, 1000);

        // TODO
        
        // at the end of a game
        // getTimer().cancel(); // stop the timer
        // showEndScreen();
    }
	
	public void openDifficultySettings() {
		VBox difficultyLayout = new VBox(10);
		difficultyLayout.setAlignment(Pos.CENTER);
		difficultyLayout.setStyle("-fx-background-color: #00a8c4;");
		
		for (int difficulty = 1; difficulty <= 10; difficulty++) {
            Button levelButton = new Button("Level " + difficulty);
            levelButton.setPrefSize(100,50); //set the button size

            levelButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leos-car'");//set the style of the button
            int finalDifficulty = difficulty;
            levelButton.setOnMousePressed(event -> {
                getLevelLabel().setText("level: " + finalDifficulty);
                levelButton.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
                pauseTransition.setOnFinished(e -> levelButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leos-car'"));//to remove the transparent
                pauseTransition.play();
            });
            difficultyLayout.getChildren().add(levelButton);
        }
        
        Button backButton = new Button("Return");
        backButton.setPrefSize(100,50);//set the button size
        backButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leos-car'");//set the style of the button
        backButton.setOnAction(event -> getPrimaryStage().setScene(getHomeScene()));
        difficultyLayout.getChildren().add(backButton);
        
        Scene difficultyscene = new Scene(difficultyLayout);
        getPrimaryStage().setScene(difficultyscene);
	}

	public void openLeaderboard() {
		VBox leaderboardLayout = new VBox(10);
		leaderboardLayout.setAlignment(Pos.CENTER);
		leaderboardLayout.setStyle("-fx-background-color: #00a8c4;");
		
		// TODO
		
		try {
            /* TODO */throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
	
    public void display(Chronometer chronometer){}
    public void loadLevels(){}
    public void selectLevel(int currentLevel){}
    public void shuffleLevel(){}
    public void swapTile(int x1, int y1, int x2, int y2){

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

        int temp = currentLevel.getTile(x1,y1).getValue();
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
        launch(args);
    }
}