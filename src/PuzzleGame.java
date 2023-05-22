package src;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
import javafx.animation.SequentialTransition;

public class PuzzleGame extends Application {
    private Level[] levels;
    private Level currentLevel;
    private Level[] unlockedLevels;
    private int moveCount;
    private Stage primaryStage;
    private Scene homeScene;
    private SequentialTransition timer;
    private Label levelLabel;
    public static int level = 1;
    private int point;
    
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

	public SequentialTransition getTimer() {
		return timer;
	}

	public void setTimer(SequentialTransition timer) {
		this.timer = timer;
	}

	public Label getLevelLabel() {
		return levelLabel;
	}

	public void setLevelLabel(Label levelLabel) {
		this.levelLabel = levelLabel;
	}

    public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public static int getLevel() {
        return level;
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
		if(getTimer() != null) getTimer().stop(); // at least 1 game has been started. Subsequent calls of stop() have no effect.
    	getPrimaryStage().setScene(getHomeScene());
	}

    public void startGame(){

    	VBox playLayout = new VBox(32);
    	playLayout.setAlignment(Pos.CENTER);
    	playLayout.setStyle("-fx-background-color: #00a8c4;");
        
        HBox topLayout = new HBox(64);
        
        // TODO undo, redo etc.
        
        GridPane gridLayout = new GridPane();
        
        // TODO tiles in gridLayout
        
//        if(isGameFinished()) {
//            getTimer().stop();
//            try {
//				showEndScreen();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//        }
        
        HBox bottomLayout = new HBox(64);
    	
        Chronometer chronometer = new Chronometer();
        
        Button giveUpButton = new Button("Give up");
        giveUpButton.setStyle("-fx-font-size:32");
        giveUpButton.setOnAction(e -> {
        	getTimer().stop();
        	getPrimaryStage().setScene(getHomeScene());
        });
        
        bottomLayout.getChildren().addAll(chronometer, giveUpButton);

        playLayout.getChildren().addAll(topLayout, gridLayout, bottomLayout);
    	
    	Scene playScene = new Scene(playLayout, 1600, 900); // HD+ scene
    	
        getPrimaryStage().setScene(playScene);

        chronometer.start();

        // Schedule the task to run every second
        final PauseTransition timerUpdate = new PauseTransition(Duration.seconds(1));
        // Update the chronometer
        timerUpdate.setOnFinished(e -> chronometer.updateElapsedTime());
        
        // start the timer
        setTimer(new SequentialTransition(timerUpdate));
        getTimer().setCycleCount(PauseTransition.INDEFINITE);
        getTimer().play();
    }
	
	private boolean isGameFinished() {
		// TODO
		return false;
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

        for (int difficulty = 1; difficulty <= 10; difficulty++) { // to print the button
            Button levelButton = new Button("Level " + difficulty);
            levelButton.setPrefSize(100,50);

            levelButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leos-car'");//set the style of the button
            int finalDifficulty = difficulty;
            levelButton.setOnMousePressed(event -> {
            	leaderboardLayout.getChildren().clear(); // to remove all the button in order to print th ranking
                levelButton.setStyle("-fx-border-color: black; -fx-background-color: grey;"); // to make grey transparent

                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1)); // during 0.1 second
                pauseTransition.play();

                File file = new File("pointlastgame.txt."); // to retrieve point
                // collect the point
                Scanner pointlastgame;
                try {
                    pointlastgame = new Scanner(file);

                    String[] data = pointlastgame.next().split(";");//to separate point and level according to ;
                    Label[] labelrank = new Label[10];
                    Integer[] listpoint = new Integer[10];
                    Arrays.fill(listpoint, 0);//to initialize in zer0
                    int count = 0;
                    do {
                        if (Integer.parseInt(data[1]) == finalDifficulty) {
                            listpoint[count]= Integer.parseInt(data[0]);
                            count++;
                        }

                        if (pointlastgame.hasNextLine()) data = pointlastgame.nextLine().split(";");
                        else break;
                    } while (pointlastgame.hasNextLine());
                    
                    count = 0;

                    Arrays.sort(listpoint, Comparator.reverseOrder()); //to sort in reverse
                    for (Integer integer : listpoint) {

                        if(count == 10) break;
                        else {
                            labelrank[count] = new Label("Point: " + integer + " | Level: " + finalDifficulty); // to set the rank
                            labelrank[count].setStyle("-fx-text-fill: white;-fx-font-size: 18;-fx-font-family: 'Leos-car'");
                            leaderboardLayout.getChildren().add(labelrank[count]); // add the ranking into the container
                            count++;
                        }
                    }
                    pointlastgame.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

            leaderboardLayout.getChildren().add(levelButton);
        }
        Button backButton = new Button("Return");
        backButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'");//set the style of the button
        backButton.setPrefWidth(100);
        backButton.setPrefHeight(50);
        backButton.setOnAction(event -> getPrimaryStage().setScene(getHomeScene()));
        leaderboardLayout.getChildren().add(backButton);
        
        Scene leaderboardScene = new Scene(leaderboardLayout);
        primaryStage.setScene(leaderboardScene);
	}
	
    public void showEndScreen() throws FileNotFoundException{
    	
    	collectLevel();
    	collectPoints();

        //-------------------------------------create point label----------------------------------------------------------------
        Label pointLabel = new Label("Points: "+getPoint()); // to print point
        pointLabel.setStyle("-fx-text-fill: white;-fx-font-size: 18;-fx-font-family: 'Leoscar'");
        Label levelLabel = new Label("Level: "+getLevel()); // to print level
        levelLabel.setStyle("-fx-text-fill: white;-fx-font-size: 18;-fx-font-family: 'Leoscar'");
        HBox hbox = new HBox(5); // to create a vertical space 10px
        hbox.setAlignment(Pos.CENTER); // to center the buttons
        hbox.getChildren().addAll(pointLabel, levelLabel); // to align horizontally point+level
        
        // ------------------------------------Create "Try Again" button---------------------------------------------------------
        Button tryAgainButton = new Button("Try Again");

        //---------------------------------------- Create "Replay" button------------------------------------------------------
        Button replayButton = new Button("Replay");
        replayButton.setOnAction(event -> startGame());

        //--------------------------------------- Create "Save score" button--------------------------------------------------
        Button saveScoreButton = new Button("Save score");

        int finalPoint = getPoint();
        saveScoreButton.setOnAction(event -> saveScore(finalPoint));

        //----------------------------------------- Create "Home" button----------------------------------------------------
        Button homeButton = new Button("Home");
        homeButton.setOnAction(event -> getPrimaryStage().setScene(getHomeScene()));
        
        // Buttons formatting
		List<Button> buttons = Arrays.asList(tryAgainButton, replayButton, saveScoreButton, homeButton);
		for(Button button : buttons) {
			button.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leoscar'");
			button.setOnMousePressed(event -> {
				button.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
	            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
	            pauseTransition.setOnFinished(e -> button.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leoscar'"));//to remove the transparent
	            pauseTransition.play();
	        });
		}
        
        // ----------------------------Create VBox to set label and button with space--------------------------------------
        VBox endScreenLayout = new VBox(10); // to create a vertical space 10px
        endScreenLayout.setAlignment(Pos.CENTER); // to center the buttons
        endScreenLayout.getChildren().add(hbox); // to add the hbox
        endScreenLayout.getChildren().addAll(buttons); // to add the buttons

        //---------------------------------------to define the scene-----------------------------------------------------------------
		Scene endScreenScene = new Scene(endScreenLayout, 300, 300);
		primaryStage.setScene(endScreenScene);
		
    }
    
    public void collectPoints() throws FileNotFoundException {

        File file = new File("pointLastGame.txt"); // to register pointlastgame
        Scanner pointlastgame = new Scanner(file); // collect the point
        String[] data = pointlastgame.next().split(";");
        setPoint(Integer.parseInt(data[0])); // convert string to int
        pointlastgame.close();

    }
    
    public void collectLevel() throws FileNotFoundException {
    	
        File file = new File("pointlastgame.txt"); // to register pointlastgame
        Scanner pointlastgame = new Scanner(file); // collect the point
        String[] data = pointlastgame.next().split(";");

        PuzzleGame.level = Integer.parseInt(data[1]); // convert string to int
        pointlastgame.close();
        
    }
    
    public void saveScore(int point) {

        try{ // to check the availability of score
            FileWriter writer = new FileWriter("point.txt");
            writer.write("Score: " + point); //to register the point on point.txt
            writer.close();
        } catch (IOException e) {
            System.out.println("Error  : " + e.getMessage());
        }
    }
	
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

    public static void main(String[] args) {
        launch(args);
    }
}