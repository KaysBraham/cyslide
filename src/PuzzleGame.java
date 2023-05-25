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
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The PuzzleGame class represents a puzzle game application.
 * It uses JavaFX and extends the JavaFX Application class.
 */
public class PuzzleGame extends Application {
    private static final String folderPath = "data";
    private static final FileIO fileLoader = new FileIO(folderPath);

	/**
     * The array of available levels.
     */
    private static final List<Level> levels = fileLoader.loadLevels();

    /**
     * The currently selected level.
     */
    private static Level currentLevel;

	/**
     * The array of unlocked levels.
     */
    private Level[] unlockedLevels;

    /**
     * The count of moves made in the current level.
     */
    private int moveCount;

    /**
     * The primary stage of the JavaFX application.
     */
    private Stage primaryStage;

    /**
     * The scene representing the home screen of the game.
     */
    private Scene homeScene;

    /**
     * The sequential transition used as a timer in the game.
     */
    private SequentialTransition timer = null;

    /**
     * The label displaying the current level.
     */
    private Label levelLabel;
    public static int currentLevelNumber = 1;
    
    /**
     * The current score of the player.
     */
    private int score;
    
    /**
     * The undo Button.
     */
    private Button undoButton;

    /**
     * The random shuffle Button.
     */

    private Button randomShuffleButton;

    /**
     * The step by step shuffle Button.
     */

    private Button stepByStepShuffleButton;

    /**
     * The redo Button.
     */
    private Button redoButton;

	/**
     * Returns a list containing the levels.
     *
     * @return The level list.
     */
	public static List<Level> getLevels() {
		return levels;
	}

	/**
     * Returns the current level in the game.
     *
     * @return The current level.
     */
    public static Level getCurrentLevel() {
		return currentLevel;
	}

    /**
     * Sets the current level.
     *
     * @param currentLevel The current level.
     */
	public static void setCurrentLevel(Level currentLevel) {
		PuzzleGame.currentLevel = currentLevel;
	}

	/**
     * Returns the current level number.
     *
     * @return The current level number.
     */
    public static int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    /**
     * Returns the current level in the game.
     *
     * @return The current level.
     */
	public static Level getLevel(int n) {
        return levels.get(n);
    }

    /**
     * Get the move count of the current level.
     *
     * @return The move count.
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Sets the move count of the current level.
     *
     * @param moveCount The move count.
     */
    public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	/**
     * Returns the primary stage of the JavaFX application.
     *
     * @return The primary stage.
     */
    public Stage getPrimaryStage() {
		return primaryStage;
	}

    /**
     * Sets the primary stage of the JavaFX application.
     *
     * @param primaryStage The primary stage.
     */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

    /**
     * Returns the scene representing the home screen of the game.
     *
     * @return The home screen scene.
     */
	public Scene getHomeScene() {
		return homeScene;
	}

    /**
     * Sets the scene representing the home screen of the game.
     *
     * @param homeScene The home screen scene.
     */
	public void setHomeScene(Scene homeScene) {
		this.homeScene = homeScene;
	}

    /**
     * Returns the timer used in the game.
     *
     * @return The timer.
     */
	public SequentialTransition getTimer() {
		return timer;
	}

    /**
     * Sets the timer used in the game.
     *
     * @param timer The timer.
     */
	public void setTimer(SequentialTransition timer) {
		this.timer = timer;
	}

    /**
     * Returns the label displaying the current level.
     *
     * @return The level label.
     */
	public Label getLevelLabel() {
		return levelLabel;
	}

    /**
     * Sets the label displaying the current level.
     *
     * @param levelLabel The level label.
     */
	public void setLevelLabel(Label levelLabel) {
		this.levelLabel = levelLabel;
	}

    /**
     * Sets the current level number.
     *
     * @param currentLevelNumber The current level number.
     */
    public static void setCurrentLevelNumber(int currentLevelNumber) {
		PuzzleGame.currentLevelNumber = currentLevelNumber;
	}
    
    /**
     * Returns the current score of the player in the game.
     *
     * @return The current score of the player.
     */
    public int getScore() {
		return score;
	}

    /**
     * Sets the current score of the player in the game.
     *
     * @param score The current score of the player.
     */
	public void setScore(int score) {
		this.score = score;
	}

    /**
     * Returns the undo button.
     *
     * @return The undo button.
     */
    public Button getUndoButton() {
		return undoButton;
	}

    /**
     * Sets the undo button.
     *
     * @param undoButton The undo button.
     */
	public void setUndoButton(Button undoButton) {
		this.undoButton = undoButton;
	}

    /**
     * Returns the redo button.
     *
     * @return The redo button.
     */
	public Button getRedoButton() {
		return redoButton;
	}

    /**
     * Sets the redi button.
     *
     * @param redoButton The redo button.
     */
	public void setRedoButton(Button redoButton) {
		this.redoButton = redoButton;
	}


    /**
     * Returns the random shuffle button.
     *
     * @return The random shuffle button.
     */

    public Button getRandomShuffleButton() {
        return randomShuffleButton;
    }

    /**
     * Sets the random shuffle button.
     *
     * @param randomShuffleButton The random shuffle button.
     */
    public void setRandomShuffleButton(Button randomShuffleButton) {
        this.randomShuffleButton = randomShuffleButton;
    }

    /**
     * Returns the step by step shuffle button.
     *
     * @return The step by step shuffle button.
     */

    public Button getStepByStepShuffleButton() {
        return stepByStepShuffleButton;
    }

    /**
     * Sets the step by step shuffle button.
     *
     * @param stepByStepShuffleButton The shuffle button.
     */
    public void setStepByStepShuffleButton(Button stepByStepShuffleButton) {
        this.stepByStepShuffleButton = stepByStepShuffleButton;
    }

    /**
     * Starts the JavaFX application by setting up the primary stage and the home screen scene.
     *
     * @param primaryStage The primary stage.
     * @throws Exception If an exception occurs during the startup of the application.
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		setPrimaryStage(primaryStage);
		
		setLevelLabel(new Label("level: " + getCurrentLevelNumber())); //to print level
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

        //to have image in background
        StackPane root = new StackPane(); // Use StackPane
        // Ajouter l'ImageView de l'image en arrière-plan
        ImageView backgroundImage = new ImageView("file:chemin.png");
        backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());
        root.getChildren().add(backgroundImage);

        VBox homeLayout = new VBox(48);
        homeLayout.setAlignment(Pos.CENTER);
        homeLayout.getChildren().add(getLevelLabel());
        homeLayout.getChildren().addAll(buttons);
        root.getChildren().add(homeLayout);

        setHomeScene(new Scene(root, 640, 480));

        getPrimaryStage().setScene(getHomeScene());
        getPrimaryStage().show();

    }

    /**
     * Stops the timer to allow the application to stop properly, then closes the application.
     */
	@Override
	public void stop(){
		if(getTimer() != null) getTimer().stop(); // at least 1 game has been started. Subsequent calls of SequentialTransition.stop() have no effect.
		Platform.exit();
	}

    /**
     * Starts a new game by setting up the play screen scene and initializing the game components.
     */
    public void startGame(){

    	VBox playLayout = new VBox(32);
    	playLayout.setAlignment(Pos.CENTER);
    	playLayout.setStyle("-fx-background-color: #00a8c4;");
        
        HBox topLayout = new HBox(64);
    	topLayout.setAlignment(Pos.CENTER);

        setUndoButton(new Button("Undo"));
        getUndoButton().setStyle("-fx-font-size:32");
        getUndoButton().setDisable(true);
        getUndoButton().setOnAction(e -> undoMove());
        
        setRedoButton(new Button("Redo"));
        getRedoButton().setStyle("-fx-font-size:32");
        getRedoButton().setDisable(true);
        getRedoButton().setOnAction(e -> redoMove());

        GridPane gridLayout = new GridPane();
        gridLayout.setAlignment(Pos.CENTER);

        setCurrentLevel(getLevels().get(getCurrentLevelNumber() - 1).copy());

        // putting the tiles in the gridpane
        tileGridConstuctor(gridLayout);

        setRandomShuffleButton(new Button("Random Shuffle"));
        getRandomShuffleButton().setStyle("-fx-font-size:25");
        getRandomShuffleButton().setOnAction(e -> {currentLevel.randomShuffleLevel();
                                                tileGridConstuctor(gridLayout);});

        setStepByStepShuffleButton(new Button("Step by step Shuffle"));
        getStepByStepShuffleButton().setStyle("-fx-font-size:25");
        getStepByStepShuffleButton().setOnAction(e -> {currentLevel.stepByStepShuffleLevel();
                                                        tileGridConstuctor(gridLayout);});

        topLayout.getChildren().addAll(getUndoButton(), getRedoButton(), getRandomShuffleButton(), getStepByStepShuffleButton());

        HBox bottomLayout = new HBox(64);
        bottomLayout.setAlignment(Pos.CENTER);

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

        playScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event1 -> {
            playScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event2 -> {
                int clickedRow = gridLayout.getRowIndex((Tile) event1.getTarget());
                int clickedCol = gridLayout.getColumnIndex((Tile) event1.getTarget());
                int emptyRowPos = gridLayout.getRowIndex((Tile) event2.getTarget());
                int emptyColPos = gridLayout.getColumnIndex((Tile) event2.getTarget());
                if (currentLevel.getTile(clickedRow,clickedCol).getValue() == 0 || currentLevel.getTile(clickedRow,clickedCol).getValue() == -1) {
                    System.out.println("prohibit movement");
                }
                else {
                    swapTile(clickedRow, clickedCol, emptyRowPos, emptyColPos) ;
                    tileGridConstuctor(gridLayout);
                }


            });
        });


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

    private static void tileGridConstuctor(GridPane gridLayout) {
        gridLayout.getChildren().clear();
        int i, j = 0;
        for (Tile[] tiles: getCurrentLevel().getTiles()) {
        	i = 0;
        	for(Tile tile : tiles) {
        		switch(tile.getValue()) {
	        		case -1:
	        			tile.setVisible(false);
	        			break;
	        		case 0:
	        			tile.setText("");
	        	        tile.setStyle("-fx-background-color: #fc6;"); // light wood
	        			break;
	        		default:
	        			tile.setText(Integer.toString(tile.getValue()));
	        	        tile.setStyle("-fx-font-size: 38;"
	        	        		+ "-fx-text-fill: #fff;"
	        	        		+ "-fx-border-width: 2;"
	        	        		+ "-fx-border-color: #420;" // very dark brown
	        	        		+ "-fx-background-color: #640;"); // dark wood
	        	        tile.setAlignment(Pos.CENTER);
	            		tile.setOnAction(e -> {
	            			// TODO swapTile
	            		});
        		}
        		gridLayout.add(tile, i++, j);
        	}
        	j++;
        }
    }

    /**
     * Checks if the current game is finished.
     *
     * @return true if the game is finished, false otherwise.
     */
	private boolean isGameFinished() {
		// TODO
		return false;
	}

    /**
     * Opens the difficulty settings screen.
     */
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
                // TODO
                setCurrentLevelNumber(finalDifficulty);
                getLevelLabel().setText("level: " + finalDifficulty); // updating the label in home screen
                levelButton.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
                pauseTransition.setOnFinished(e -> levelButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leos-car'"));//to remove the transparent
                pauseTransition.play();
                getPrimaryStage().setScene(getHomeScene()); // return to home screen after selecting a level
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

    /**
     * Opens the leaderboard screen.
     */
	public void openLeaderboard() {
		VBox leaderboardLayout = new VBox(10);
		leaderboardLayout.setAlignment(Pos.CENTER);
		leaderboardLayout.setStyle("-fx-background-color: #00a8c4;");
        //create back button
        Button backButton = new Button("Return");
        backButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'");//set the style of the button
        backButton.setPrefWidth(100);
        backButton.setPrefHeight(50);
        backButton.setOnAction(event -> getPrimaryStage().setScene(getHomeScene()));

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

                File file = new File("scoreLastGame.txt."); // to retrieve point
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
                    leaderboardLayout.getChildren().add(backButton);
                    pointlastgame.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

            leaderboardLayout.getChildren().add(levelButton);
        }

        leaderboardLayout.getChildren().add(backButton); //add back button
        
        Scene leaderboardScene = new Scene(leaderboardLayout);
        primaryStage.setScene(leaderboardScene);
	}

    /**
     * Shows the end screen.
     *
     * @throws FileNotFoundException if the "scoreLastGame.txt" file is not found.
     */
    public void showEndScreen() throws FileNotFoundException{
    	
    	collectLevel();
    	collectPoints();

        //-------------------------------------create point label----------------------------------------------------------------
        Label pointLabel = new Label("Points: "+getScore()); // to print point
        pointLabel.setStyle("-fx-text-fill: white;-fx-font-size: 18;-fx-font-family: 'Leoscar'");
        Label levelLabel = new Label("Level: "+getCurrentLevelNumber()); // to print level
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

        int finalPoint = getScore();
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

    /**
     * Collects the score from the "scoreLastGame.txt" file.
     *
     * @throws FileNotFoundException if the file is not found.
     */
    public void collectPoints() throws FileNotFoundException {

        File file = new File("scoreLastGame.txt"); // to register scoreLastGame
        Scanner scoreLastGame = new Scanner(file); // collect the point
        String[] data = scoreLastGame.next().split(";");
        setScore(Integer.parseInt(data[0])); // convert string to int
        scoreLastGame.close();

    }

    /**
     * Collects the current level from the "scoreLastGame.txt" file.
     *
     * @throws FileNotFoundException if the file is not found.
     */
    public void collectLevel() throws FileNotFoundException {
    	
        File file = new File("scoreLastGame.txt"); // to register scoreLastGame
        Scanner scoreLastGame = new Scanner(file); // collect the score
        String[] data = scoreLastGame.next().split(";");

        PuzzleGame.currentLevelNumber = Integer.parseInt(data[1]); // convert string to int
        scoreLastGame.close();
        
    }

    /**
     * Saves the player score to a file.
     *
     * @param score The score to save.
     */
    public void saveScore(int score) {

        try { // to check the availability of score
            FileWriter writer = new FileWriter("score.txt");
            writer.write("Score: " + score); // to register the score in score.txt
            writer.close();
        } catch (IOException e) {
            System.out.println("Error  : " + e.getMessage());
        }
    }

    /**
     * Loads the levels.
     */
    public void loadLevels(){
    	// TODO
    }
    
    /**
     * Selects the current level.
     *
     * @param currentLevel The level to select.
     */
    public void selectLevel(int currentLevel){
    	// TODO
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
        
    	setMoveCount(getMoveCount() + 1);
    	
        if(isGameFinished()) {
	        getTimer().stop();
	        try {
				showEndScreen();
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
        }
    }

    /**
     * Checks if the current level is solvable.
     */
    public void checkSolvable(){
    	// TODO
    }

    /**
     * Unlocks the next level.
     */
    public void unlockNextLevel(){
    	// TODO
    }

    /**
     * Displays a message indicating that the level is solved.
     */
    public void displayLevelSolved(){
    	// TODO
    }

    /**
     * Undoes the last move.
     * Decrements the move count.
     */
    public void undoMove(){
    	setMoveCount(getMoveCount() - 1);
    	
    	getRedoButton().setDisable(false);
    	getUndoButton().setDisable(true);
    	
    	// TODO
    }

    /**
     * Redoes the undid move.
     * Increments the move count.
     */
    public void redoMove(){
    	setMoveCount(getMoveCount() + 1);
    	
    	getRedoButton().setDisable(true);
    	getUndoButton().setDisable(false);
    	
    	// TODO
    }

    /**
     * The main method to launch the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
        //tests

        currentLevel.print();
        getLevel(currentLevel.getLevelNumber()).print();

        currentLevel.stepByStepShuffleLevel();

        currentLevel.print();

        //fin test
    }
}