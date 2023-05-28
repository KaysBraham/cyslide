package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Stack ;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;



/**
 * The PuzzleGame class represents a puzzle game application.
 * It uses JavaFX and extends the JavaFX Application class.
 */
public class PuzzleGame extends Application {
    private static final String folderPath = "data";
    private static final FileIO fileLoader = new FileIO(folderPath);

    private static final List<Level> levels = fileLoader.loadLevels(); // The array of available levels.
    private Level[] unlockedLevels; // The array of unlocked levels.

    private static int moveCount; // The count of moves made in the current level.

    private static Stage primaryStage; // The primary stage of the JavaFX application.
    private static Scene homeScene; // The scene representing the home screen of the game.
    static GridPane gridLayout = new GridPane(); // The grid pane in which the cells are displayed.

    private Scene difficultylayout; // The scene representing the difficultylayout of the game.
    private Scene leaderboardlayout; // The scene representing the leaderboardlayout of the game.

    private static SequentialTransition timer = null; // The sequential transition used as a timer in the game.

    private static Level currentLevel; // The currently selected level.
    private Label levelLabel; // The label displaying the current level.
    static Label moveCountLabel = new Label("Move count : " + getMoveCount()); // The label displaying the current move count.

    public static int currentLevelNumber = 1; // The number of the current level.
    private static int score; // The current score of the player.
    private static int bestScore; // The best score of the player on the current level.

    private static Button undoButton; // The undo Button.
    private static Button redoButton; // The redo Button.
    private static Button randomShuffleButton; // The random shuffle Button.
    private static Button stepByStepShuffleButton; // The step by step shuffle Button.
    private static Button solveButton; // The solve Button.

    private static boolean canPlay = false; // True if the user can play, false otherwise.

    private static Pair<Integer, Integer> selectedCell = null; // The coordinates of the currently selected cell.

    private static boolean[] completedLevels = new boolean[getLevels().size()]; // The table of booleans representing the completed levels.

    static Stack<Tile[][]> undoStack = new Stack<>(); // The stack of previous moves that the user is able to undo.
    static Stack<Tile[][]> redoStack = new Stack<>(); // The stack of undid moves that the user is able to redo.

    public static LinkedHashSet<Tile[][]> solvingMoves= new LinkedHashSet<>(); // The solving moves.

    /**
     * Returns the current level in the game.
     *
     * @return The current level.
     */
    public static Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Returns a list containing the levels.
     *
     * @return The level list.
     */
    public static List<Level> getLevels() {
        return levels;
    }

    public static  int getBestScore() {
        return bestScore;
    }

    public static  void setBestScore(int n) {
        bestScore = n ;
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
     * Returns the completed levels.
     *
     * @return The completed levels.
     */
    public static boolean[] getCompletedLevels() {
		return completedLevels;
	}

	/**
     * Get the move count of the current level.
     *
     * @return The move count.
     */
    public static int getMoveCount() {
        return moveCount;
    }

    /**
     * updates the move count label
     */
    private static void updateMoveCountLabel() {
        getMoveCountLabel().setText("Move count : " + getMoveCount());
    }

    /**
     * Sets the move count of the current level.
     *
     * @param moveCount The move count.
     */
    public static void setMoveCount(int moveCount) {
        PuzzleGame.moveCount = moveCount;
        updateMoveCountLabel();
    }

    /**
     * Returns the primary stage of the JavaFX application.
     *
     * @return The primary stage.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets the primary stage of the JavaFX application.
     *
     * @param primaryStage The primary stage.
     */
    public void setPrimaryStage(Stage primaryStage) {
        PuzzleGame.primaryStage = primaryStage;
    }

    /**
     * Returns the scene representing the home screen of the game.
     *
     * @return The home screen scene.
     */
    public static Scene getHomeScene() {
        return homeScene;
    }

    /**
     * Sets the scene representing the home screen of the game.
     *
     * @param homeScene The home screen scene.
     */
    public void setHomeScene(Scene homeScene) {
        PuzzleGame.homeScene = homeScene;
    }

    /**
     * Returns the timer used in the game.
     *
     * @return The timer.
     */
    public static SequentialTransition getTimer() {
        return timer;
    }

    /**
     * Sets the timer used in the game.
     *
     * @param timer The timer.
     */
    public static void setTimer(SequentialTransition timer) {
        PuzzleGame.timer = timer;
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
     * Returns the move count label.
     * @return the move count label.
     */
    public static Label getMoveCountLabel() {
		return moveCountLabel;
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
    public static int getScore() {
        return score;
    }

    /**
     * Sets the current score of the player in the game.
     *
     * @param score The current score of the player.
     */
    public static void setScore(int score) {
       PuzzleGame.score = score;
    }

    /**
     * Returns the undo button.
     *
     * @return The undo button.
     */
    public static Button getUndoButton() {
        return undoButton;
    }

    /**
     * Sets the undo button.
     *
     * @param undoButton The undo button.
     */
    public static void setUndoButton(Button undoButton) {
        PuzzleGame.undoButton = undoButton;
    }

    /**
     * Returns the redo button.
     *
     * @return The redo button.
     */
    public static Button getRedoButton() {
        return redoButton;
    }

    /**
     * Sets the redo button.
     *
     * @param redoButton The redo button.
     */
    public static void setRedoButton(Button redoButton) {
        PuzzleGame.redoButton = redoButton;
    }

    /**
     * Returns the grid layout. 
     * 
     * @return the grid layout.
     */
    public static GridPane getGridLayout() {
		return gridLayout;
	}

    /**
     * Sets the grid layout.
     * 
     * @param gridLayout the grid layout.
     */
	public static void setGridLayout(GridPane gridLayout) {
		PuzzleGame.gridLayout = gridLayout;
	}

    /**
     * Returns the undo stack. 
     * 
     * @return the undo stack.
     */
    public static Stack<Tile[][]> getUndoStack() {
		return undoStack;
	}

    /**
     * Returns the redo stack. 
     * 
     * @return the redo stack.
     */
	public static Stack<Tile[][]> getRedoStack() {
		return redoStack;
	}

    /**
     * Undoes the last move.
     */
	private static void undo() {

        if (getUndoStack().isEmpty()) return;

    	setMoveCount(getMoveCount() - 1);
    	getRedoButton().setDisable(false);

        if (getUndoStack().size() == 1){
        	getUndoButton().setDisable(true);
        }
        getRedoStack().push(getCurrentLevel().copy().getTiles());
        Tile[][] previousState = getUndoStack().pop();
        getCurrentLevel().print() ;
        getCurrentLevel().setTiles(previousState);
        getCurrentLevel().print() ;

    	tileGridConstuctor(getGridLayout());
    }

    /**
     * Redoes the last undid move.
     */
    private static void redo() {

        if (getRedoStack().isEmpty()) return;

    	setMoveCount(getMoveCount() + 1);
    	getUndoButton().setDisable(false);

        if (getRedoStack().size() == 1) {
            getRedoButton().setDisable(true);
        }
        getUndoStack().push(getCurrentLevel().copy().getTiles());
        Tile[][] nextState = getRedoStack().pop();
        getCurrentLevel().print() ;
        getCurrentLevel().setTiles(nextState);
        getCurrentLevel().print() ;

    	tileGridConstuctor(getGridLayout());
    }

	/**
     * Returns the solving moves.
     *
     * @return The solving moves.
     */
    public static LinkedHashSet<Tile[][]> getSolvingMoves() {
		return solvingMoves;
	}

	/**
     * Solves the current level.
     */
    private static void solve(){
        for(Tile[][] tiles : getSolvingMoves()){
        	getCurrentLevel().setTiles(tiles);
            tileGridConstuctor(getGridLayout());
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.play();
        }
    }

	/**
     * Returns the random shuffle button.
     *
     * @return The random shuffle button.
     */
    public static Button getRandomShuffleButton() {
        return randomShuffleButton;
    }

    /**
     * Sets the random shuffle button.
     *
     * @param randomShuffleButton The random shuffle button.
     */
    public static void setRandomShuffleButton(Button randomShuffleButton) {
        PuzzleGame.randomShuffleButton = randomShuffleButton;
    }

    /**
     * Returns the step by step shuffle button.
     *
     * @return The step by step shuffle button.
     */

    public static Button getStepByStepShuffleButton() {
        return stepByStepShuffleButton;
    }

    /**
     * Sets the step by step shuffle button.
     *
     * @param stepByStepShuffleButton The shuffle button.
     */

    public static void setStepByStepShuffleButton(Button stepByStepShuffleButton) {
        PuzzleGame.stepByStepShuffleButton = stepByStepShuffleButton;
    }

    /**
     * Returns the selected cell.
     * 
     * @return the selected cell.
     */
    public static Pair<Integer, Integer> getSelectedCell() {
		return PuzzleGame.selectedCell;
	}

    static PauseTransition pause1 = new PauseTransition(Duration.seconds(1));

    static PauseTransition pause2 = new PauseTransition(Duration.seconds(2));


    static void firstShuffle(){
        pause1.play() ;
        pause2.setOnFinished(event -> {
            currentLevel.stepByStepShuffleLevel();
            tileGridConstuctor(getGridLayout());});
        pause2.play();
        canPlay = true ;
    }

    /**
     * Returns the solve button.
     *
     * @return The solve button.
     */

    public static Button getsolveButton() {
        return solveButton;
    }

    /**
     * Sets the solve button.
     *
     * @param solveButton The shuffle button.
     */
    public static void setsolveButton(Button solveButton) {
        PuzzleGame.solveButton = solveButton;
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
        getPrimaryStage().setMaximized(true);
        String levelText = "level: " + getCurrentLevelNumber();

        Rectangle rectangle = new Rectangle(200, 30);
        rectangle.setStyle("-fx-stroke:rgba(68,34,0,0); -fx-stroke-width: 1px; -fx-fill: rgba(68,34,0,0); -fx-text-fill:#420"); //to configure border


        setLevelLabel(new Label(levelText)); //to print level
		getLevelLabel().setStyle("-fx-text-fill: #442200;-fx-font-size: 25;-fx-font-family: 'Rockwell'");//to configure levellabel

		Button startButton = new Button("New game");
		startButton.setOnAction(e -> {startGame(); firstShuffle();});


        Button setDifficultyButton = new Button("Difficulty settings");
        setDifficultyButton.setOnAction(e -> {
            setDifficultylayout();

            Scene leaderboardScene = getDifficultylayout();

            StackPane root = new StackPane();
            root.getChildren().addAll(getPrimaryStage().getScene().getRoot(), leaderboardScene.getRoot());
            getPrimaryStage().setScene( new Scene(root));
        });
        Button showLeaderboardButton = new Button("Leaderboard");
        showLeaderboardButton.setOnAction(e -> {
            setLeaderboardlayout();

            Scene leaderboardScene = getLeaderboardlayout();

            StackPane root = new StackPane();
            root.getChildren().addAll(getPrimaryStage().getScene().getRoot(), leaderboardScene.getRoot());
            getPrimaryStage().setScene( new Scene(root));
        });

		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> Platform.exit());

		List<Button> buttons = Arrays.asList(startButton, setDifficultyButton, showLeaderboardButton, exitButton);
		for(Button button : buttons) {
            button.setStyle("-fx-text-fill:#420; -fx-border-color: #420; -fx-background-color: rgba(37,20,12,0); -fx-font-size: 22; -fx-border-width: 3; -fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");
			button.setOnMousePressed(event -> {
				button.setStyle("-fx-border-color: rgba(0,0,0,0); -fx-background-color: rgba(0,0,0,0);"); // to make grey transparent
	            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1)); // during 0.1 second
	            pauseTransition.setOnFinished(e -> button.setStyle("-fx-text-fill:#420; -fx-border-color: #420; -fx-background-color: rgba(37,20,12,0); -fx-font-size: 22; -fx-border-width: 3; -fx-font-family: 'Rockwell'; -fx-font-size: 'bold'"));//to remove the transparent
	            pauseTransition.play();
	        });
		}

        //to have image in background
        StackPane root = new StackPane(); // Use StackPane
        // add imageview in background image

        ImageView backgroundImage = new ImageView("file:background2-min.jpg");
        backgroundImage.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImage.fitHeightProperty().bind(primaryStage.heightProperty());
        root.getChildren().add(backgroundImage); //to add the image


        VBox homeLayout = new VBox(5);//to create vertical box
        homeLayout.setAlignment(Pos.CENTER);

        Text titleText = new Text("KANT");
        titleText.setFont(Font.font("Rockwell extra bold", FontWeight.BOLD, 90));
        titleText.setFill(Color.web("#420"));
        homeLayout.getChildren().add(titleText);


        StackPane circleStackPane = new StackPane(rectangle, getLevelLabel()); //to superimpose circle and label
        circleStackPane.setAlignment(Pos.CENTER);
        homeLayout.getChildren().add(circleStackPane);

        HBox buttonsBox = new HBox(10); // To adjust space between button
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(buttons);

        homeLayout.getChildren().addAll(buttonsBox);

        root.getChildren().add(homeLayout);// to add homeLayout in order to superimpose

        // get screensize of monitor
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        
        // set the scene size to fullscreen size
        setHomeScene(new Scene(root, screenSize.getWidth(), screenSize.getHeight()));

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

    private static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Starts a new game by setting up the play screen scene and initializing the game components.
     */
    public static void startGame(){

        getMoveCountLabel().setStyle("-fx-font-size: 25px ; -fx-font-family: 'Rockwell'; -fx-text-fill: #420");

        if (getCurrentLevelNumber() > 1) {
            for (int i = 0; i < getCurrentLevelNumber() - 1; i++) {
                if (!getCompletedLevels()[i]) {
                    showMessage("You must win the previous levels before playing this one.");
                    return;
                }
            }
        }


        Label titleLabel = new Label("Resolved \n level");
        titleLabel.setStyle("-fx-font-size: 15px ;");

        Image backgroundImage = new Image("file:background2-min.jpg");
        backgroundImage.widthProperty().add(primaryStage.widthProperty());
        backgroundImage.heightProperty().add(primaryStage.heightProperty());
        BackgroundSize backgroundSize = new BackgroundSize(1000, 1000, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        VBox playLayout = new VBox(32);
    	playLayout.setAlignment(Pos.CENTER);
        playLayout.setBackground(new Background(background));

        
        HBox topLayout = new HBox(64);
        topLayout.setAlignment(Pos.CENTER);

        setUndoButton(new Button("Undo"));
        getUndoButton().setStyle("-fx-font-size:32");
        getUndoButton().setDisable(true);
        getUndoButton().setOnAction(e -> undo());
        getUndoButton().setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-width: 3; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");

        setRedoButton(new Button("Redo"));
        getRedoButton().setStyle("-fx-font-size:32");
        getRedoButton().setDisable(true);
        getRedoButton().setOnAction(e -> redo());
        getRedoButton().setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-width: 3; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");

        getGridLayout().setAlignment(Pos.CENTER);


        setCurrentLevel(getLevels().get(getCurrentLevelNumber() - 1).copy());
        getCurrentLevel();
        tileGridConstuctor(getGridLayout());


        setRandomShuffleButton(new Button("Random Shuffle"));
        getRandomShuffleButton().setStyle("-fx-font-size:25");
        getRandomShuffleButton().setOnAction(e -> {
        	getCurrentLevel().randomShuffleLevel();
            tileGridConstuctor(getGridLayout());
        });
        getRandomShuffleButton().setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-width: 3; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");

        setStepByStepShuffleButton(new Button("Step by step Shuffle"));
        getStepByStepShuffleButton().setStyle("-fx-font-size:25");
        getStepByStepShuffleButton().setOnAction(e -> {
        	getCurrentLevel().stepByStepShuffleLevel();
        	tileGridConstuctor(getGridLayout());
        });
        getStepByStepShuffleButton().setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-width: 3; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");

        setsolveButton(new Button("Solve Shuffle"));
        getsolveButton().setStyle("-fx-font-size:25");
        getsolveButton().setOnAction(e -> solve());
        getsolveButton().setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-width: 3; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");

        topLayout.getChildren().addAll(getUndoButton(), getRedoButton(), getRandomShuffleButton(), getStepByStepShuffleButton(),getsolveButton());

        HBox bottomLayout = new HBox(64);
        bottomLayout.setAlignment(Pos.CENTER);

        setMoveCount(0);

        Chronometer chronometer = new Chronometer();

        Button giveUpButton = new Button("Give up");
        giveUpButton.setStyle("-fx-font-size:35");
        giveUpButton.setOnAction(e -> {
            getTimer().stop();
            Scene leaderboardScene = getHomeScene();
            StackPane root = new StackPane();
            root.getChildren().addAll(getPrimaryStage().getScene().getRoot(), leaderboardScene.getRoot());
            getPrimaryStage().setScene( new Scene(root));
        });

        giveUpButton.setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-width: 3; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");

        GridPane grid = new GridPane();
        solvedLevelPreview(grid);
        
        HBox resolvedLevelLayout = new HBox();

        resolvedLevelLayout.getChildren().addAll(titleLabel, grid) ;

        bottomLayout.getChildren().addAll(getMoveCountLabel(),chronometer, giveUpButton, resolvedLevelLayout);


        playLayout.getChildren().addAll(topLayout, getGridLayout(), bottomLayout);

        // get screensize of monitor
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        
        // set the scene size to fullscreen size
    	Scene playScene = new Scene(playLayout, screenSize.getWidth(), screenSize.getHeight()); // HD+ scene

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

    static int mousePosX; // The x-coordinate of the mouse position.
    static int mousePosY; // The y-coordinate of the mouse position.
    static int initialTranslateX; // TODO comment
    static int initialTranslateY; // TODO comment
    static final int TILE_SIZE = 200; // The size of the tiles.

    /**
     * Constructs the grid layout with the tiles.
     * 
     * @param gridLayout the grid pane.
     */
    private static void tileGridConstuctor(GridPane gridLayout) {
        gridLayout.getChildren().clear();


        for (int i = 0; i<getCurrentLevel().getTiles().length; i++) {
            for(int j = 0; j<getCurrentLevel().getTiles()[0].length; j++) {

            	getCurrentLevel().getTiles()[i][j].setPrefSize(TILE_SIZE,TILE_SIZE);


                switch(getCurrentLevel().getTiles()[i][j].getValue()) {
                    case -1:
                    	getCurrentLevel().getTiles()[i][j].setVisible(false);
                        break;
                    case 0:
                    	getCurrentLevel().getTiles()[i][j].setText("");
                        getCurrentLevel().getTiles()[i][j].setStyle("-fx-background-color: #442200; -fx-border-width: 4;"); // light wood
                        break;
                    default:
                    	getCurrentLevel().getTiles()[i][j].setText(Integer.toString(getCurrentLevel().getTiles()[i][j].getValue()));
                        getCurrentLevel().getTiles()[i][j].setStyle("-fx-font-size: 45;"
                                + "-fx-text-fill: #420;"
                                + "-fx-border-width: 4;"
                                + "-fx-border-color: #420;" // very dark brown
                                + "-fx-background-color: rgba(102,68,0,0);"); // dark wood
                        getCurrentLevel().getTiles()[i][j].setAlignment(Pos.CENTER);
                }


                if(getCurrentLevel().getTiles()[i][j].getValue()!=-1){
                    int finalI4 = i;
                    int finalJ4 = j;
                    getCurrentLevel().getTiles()[i][j].setOnDragDetected(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            if (!canPlay){
                                return;}
                            mousePosX = (int) event.getSceneX();
                            mousePosY = (int) event.getSceneY();
                            initialTranslateX = (int) getCurrentLevel().getTiles()[finalI4][finalJ4].getTranslateX();
                            initialTranslateY = (int) getCurrentLevel().getTiles()[finalI4][finalJ4].getTranslateY();

                            getCurrentLevel().getTiles()[finalI4][finalJ4].startDragAndDrop(TransferMode.MOVE);

                            ClipboardContent content = new ClipboardContent();
                            content.putString("");  // Ajoutez ici le contenu de l'objet tuile
                            Dragboard dragboard = getCurrentLevel().getTiles()[finalI4][finalJ4].startDragAndDrop(TransferMode.MOVE);
                            dragboard.setContent(content);

                            event.consume();
                        }
                    });

                    getCurrentLevel().getTiles()[i][j].setOnDragOver(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* Accepte uniquement le glisser-déposer à l'intérieur du GridPane */
                            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                                event.acceptTransferModes(TransferMode.MOVE);
                            }
                            event.consume();
                        }
                    });

                    int finalI3 = i;
                    int finalJ3 = j;
                    getCurrentLevel().getTiles()[i][j].setOnDragEntered(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* Change le style de la tuile survolée */
                            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                            	getCurrentLevel().getTiles()[finalI3][finalJ3].setOpacity(0.7);
                            }
                            event.consume();
                        }
                    });

                    int finalI = i;
                    int finalJ = j;
                    getCurrentLevel().getTiles()[i][j].setOnDragExited(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* Rétablit le style de la tuile */
                            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                            	getCurrentLevel().getTiles()[finalI][finalJ].setOpacity(1.0);
                            }
                            event.consume();

                        }
                    });

                    int finalI1 = i;
                    int finalJ1 = j;
                    getCurrentLevel().getTiles()[i][j].setOnDragDropped(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* Échange les positions des tuiles si elles sont adjacentes */
                            if (event.getGestureSource() != this) {
                                double offsetX = event.getSceneX() - mousePosX;
                                double offsetY = event.getSceneY() - mousePosY;
                                int colOffset;
                                int rowOffset;

                                if(Math.abs(offsetX)>Math.abs(offsetY)){
                                    colOffset= (int) (offsetX/Math.abs(offsetX));
                                    rowOffset = 0;
                                }else {
                                    rowOffset= (int) (offsetY/Math.abs(offsetY));
                                    colOffset = 0;
                                }

                                if ((Math.abs(colOffset) + Math.abs(rowOffset) == 1) && getCurrentLevel().getTiles()[finalI1][finalJ1].getValue()==0) {
                                    int originCol = finalJ1 - colOffset;
                                    int originRow = finalI1 - rowOffset;
                                    getUndoStack().push(getCurrentLevel().copy().getTiles());
                                        swapTiles(finalJ1, finalI1, originCol, originRow);
                                        getRedoStack().clear();
                                        getRedoButton().setDisable(true);
                                        getUndoButton().setDisable(false);
                                        setMoveCount(getMoveCount() + 1);
                                        event.setDropCompleted(true);

                                    if(isGameFinished()) {
                            	        getTimer().stop();
                        	        	getCompletedLevels()[getCurrentLevelNumber() - 1] = true;
                            	        try {
                            				showEndScreen();
                            			} catch (FileNotFoundException ex) {
                            				ex.printStackTrace();
                            			}
                                    }

                                    return;
                                }
                            }
                            event.setDropCompleted(false);
                            event.consume();
                        }
                    });

                    int finalI2 = i;
                    int finalJ2 = j;
                    getCurrentLevel().getTiles()[i][j].setOnDragDone(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* Rétablit le style de la tuile une fois le glisser-déposer terminé */
                        	getCurrentLevel().getTiles()[finalI2][finalJ2].setOpacity(1.0);
                            event.consume();
                        }
                    });
                }
                getGridLayout().add(getCurrentLevel().getTiles()[i][j], j, i);
            }
        }
    }

    /**
     * Swaps 2 tiles in the grid layout.
     * 
     * @param col1 the column of the first tile.
     * @param row1 the row of the first tile.
     * @param col2 the column of the second tile.
     * @param row2 the row of the second tile.
     */
    private static void swapTiles(int col1, int row1, int col2, int row2) {
        Node tile1 = getNodeByRowColumnIndex(row1, col1);
        Node tile2 = getNodeByRowColumnIndex(row2, col2);
        if (tile1 != null && tile2 != null) {
        	getCurrentLevel().swapTile(row1, col1, row2, col2);
            int tile1Index = getGridLayout().getChildren().indexOf(tile1);
            int tile2Index = getGridLayout().getChildren().indexOf(tile2);
            GridPane.setConstraints(tile1, col2, row2);
            GridPane.setConstraints(tile2, col1, row1);
            getGridLayout().getChildren().set(tile2Index, new Button()); // buffer to avoid an exception for duplicate children
            getGridLayout().getChildren().set(tile1Index, tile2);
            getGridLayout().getChildren().set(tile2Index, tile1);
            tileGridConstuctor(getGridLayout());
        }
    }

    /**
     * Gets the Node at the provided row and column
     * in the grid layout.
     * 
     * @param row the row to search for.
     * @param col the column to search for.
     * @return the found node, or null if none.
     */
    private static Node getNodeByRowColumnIndex(final int row, final int col) {
        for (Node node : getGridLayout().getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    /**
     * Displays a preview of the solved level
     * on the bottom-right corner of the screen.
     * 
     * @param gridLayout the grid pane in which the preview will be displayed.
     */
    private static void solvedLevelPreview(GridPane gridLayout) {
        gridLayout.getChildren().clear();
        int i, j = 0;
        for (Tile[] tiles: getLevel(getCurrentLevelNumber() - 1).getTiles()) {
            i = 0;
            for(Tile tile : tiles) {
                tile.setPrefSize(50, 50);
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
                        tile.setStyle("-fx-font-size: 15;"
                                + "-fx-text-fill: #fff;"
                                + "-fx-border-width: 2;"
                                + "-fx-border-color: #420;" // very dark brown
                                + "-fx-background-color: #640;"); // dark wood
                        tile.setAlignment(Pos.CENTER);
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
	private static boolean isGameFinished() {
		return getCurrentLevel().equals(getLevels().get(getCurrentLevelNumber() - 1));
	}

    /**
     * Returns the difficulty layout.
     * 
     * @return the difficulty layout.
     */
    public Scene getDifficultylayout() {
        return difficultylayout;
    }

    /**
     * TODO comment
     */
    public void setDifficultylayout() {
        VBox difficultyLayout = new VBox(10);

        Image backgroundImage = new Image("file:background2-min.jpg");
        backgroundImage.widthProperty().add(primaryStage.widthProperty());
        backgroundImage.heightProperty().add(primaryStage.heightProperty());
        BackgroundSize backgroundSize = new BackgroundSize(1000, 1000, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        difficultyLayout.setAlignment(Pos.CENTER);
        difficultyLayout.setBackground(new Background(background));

        for (int difficulty = 1; difficulty <= 10; difficulty++) {
            Button levelButton = new Button("Level " + difficulty);
            levelButton.setPrefSize(100, 50); //set the button size

            levelButton.setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-width: 3; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");//set the style of the button
            int finalDifficulty = difficulty;
            levelButton.setOnMousePressed(event -> {
                setCurrentLevelNumber(finalDifficulty);
                getLevelLabel().setText("level: " + finalDifficulty); // updating the label in home screen
                levelButton.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
                pauseTransition.setOnFinished(e -> levelButton.setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-size: 3; -fx-background-color: rgba(37,20,12,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'"));//to remove the transparent
                pauseTransition.play();
                Scene leaderboardScene = getHomeScene();

                StackPane root = new StackPane();
                root.getChildren().addAll(getPrimaryStage().getScene().getRoot(), leaderboardScene.getRoot());
                getPrimaryStage().setScene( new Scene(root)); // return to home screen after selecting a level
            });
            difficultyLayout.getChildren().add(levelButton);
        }

        Button backButton = new Button("Return");
        backButton.setPrefSize(100,50);//set the button size
        backButton.setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-width: 3; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");//set the style of the button
        backButton.setOnAction(e -> {

            Scene leaderboardScene = getHomeScene();

            StackPane root = new StackPane();
            root.getChildren().addAll(getPrimaryStage().getScene().getRoot(), leaderboardScene.getRoot());
            getPrimaryStage().setScene( new Scene(root));
        });
        difficultyLayout.getChildren().add(backButton);

        this.difficultylayout = new Scene(difficultyLayout);
    }

    /**
     * Returns the leaderboard layout.
     * 
     * @return the leaderboard layout.
     */
    public Scene getLeaderboardlayout() {
        return leaderboardlayout;
    }

    public void setLeaderboardlayout() {
        VBox leaderboardLayout = new VBox(10);

        Image backgroundImage = new Image("file:background2-min.jpg");
        backgroundImage.widthProperty().add(primaryStage.widthProperty());
        backgroundImage.heightProperty().add(primaryStage.heightProperty());
        BackgroundSize backgroundSize = new BackgroundSize(1000, 1000, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        leaderboardLayout.setAlignment(Pos.CENTER);
        leaderboardLayout.setBackground(new Background(background));
        Button backButton = new Button("Return");
        backButton.setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-width: 3; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 20;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");//set the style of the button
        backButton.setPrefWidth(100);
        backButton.setPrefHeight(50);
        backButton.setOnAction(e -> {
            Scene leaderboardScene = getHomeScene();

            StackPane root = new StackPane();
            root.getChildren().addAll(getPrimaryStage().getScene().getRoot(), leaderboardScene.getRoot());
            getPrimaryStage().setScene( new Scene(root));
        });
        for (int difficulty = 1; difficulty <= 10; difficulty++) { // to print the button
            Button levelButton = new Button("Level " + difficulty);
            levelButton.setPrefSize(100, 50);

            levelButton.setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-border-width: 3; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");//set the style of the button
            int finalDifficulty = difficulty;
            levelButton.setOnMousePressed(event -> {
                leaderboardLayout.getChildren().clear(); // to remove all the button in order to print th ranking

                levelButton.setStyle("-fx-border-color: rgba(37,20,12,0); -fx-background-color: rgba(128,128,128,0);"); // to make grey transparent

                File file = new File("scoreLastGame.txt"); // to retrieve point

                Scanner pointlastgame;
                try {
                    pointlastgame = new Scanner(file);

                    String[] data = pointlastgame.next().split(";");//to separate point and level according to
                    Label[] labelrank = new Label[10];
                    Integer[] listpoint = new Integer[10];
                    Arrays.fill(listpoint, 0);//to initialize in zer0
                    int count = 0;
                    do {
                        if (data.length >= 2 && Integer.parseInt(data[1]) == finalDifficulty) {
                            listpoint[count] = Integer.parseInt(data[0]);
                            count++;
                        }

                        if (pointlastgame.hasNextLine() && count<10)
                        {
                            data = pointlastgame.nextLine().split(";");
                        }
                        else{
                            break;
                        }
                    } while (true);

                    count = 0;

                    listpoint=sortPointsWithoutZeros(listpoint); //to sort listpoint in excluding zero
                    for (Integer integer : listpoint) {

                        if(count == 10) break;
                        else {
                            Label rankLabel = new Label((count + 1) + ".");
                            rankLabel.setStyle("-fx-text-fill: #442200;-fx-font-size: 18;-fx-font-family: 'Rockwell'");
                            rankLabel.setMinWidth(35);//to avoid the points to move when the ranking is ten
                            labelrank[count] = new Label("Point: " + integer); // to set the rank
                            labelrank[count].setStyle("-fx-text-fill: #442200;-fx-font-size: 18;-fx-font-family: 'Leos-car'");
                            HBox labelRanking = new HBox(); //create horizontal box


                            labelRanking.getChildren().addAll(rankLabel, labelrank[count]); //add the label containing rank and point
                            labelRanking.setAlignment(Pos.CENTER);//to set the label contaning rank+point on the center
                            leaderboardLayout.getChildren().add(labelRanking);



                            count++;
                        }
                    }
                    leaderboardLayout.getChildren().add(backButton);

                    backButton.setOnAction(e -> {
                        setLeaderboardlayout();
                        Scene leaderboardScene = getLeaderboardlayout();
                        StackPane root = new StackPane();
                        root.getChildren().addAll(getPrimaryStage().getScene().getRoot(), leaderboardScene.getRoot());
                        getPrimaryStage().setScene(new Scene(root));
                    });



                    pointlastgame.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

            leaderboardLayout.getChildren().add(levelButton);

        }

        leaderboardLayout.getChildren().add(backButton); //add back button

        this.leaderboardlayout = new Scene(leaderboardLayout);
    }



    /**
     * Shows the end screen.
     *
     * @throws FileNotFoundException if the "scoreLastGame.txt" file is not found.
     */
    public static void showEndScreen() throws FileNotFoundException{

        collectLevel();
        collectPoints();

        //-------------------------------------create point label----------------------------------------------------------------
        if (getMoveCount() < getBestScore() || getBestScore() == 0 ){
            setBestScore(getMoveCount());
        }
        Label bestScoreLabel = new Label("Best Score : " + getBestScore());
        Label scoreLabel = new Label("Score : " + getMoveCount());
        scoreLabel.setStyle("-fx-text-fill:#000000; -fx-border-color: rgba(68,34,0,0); -fx-background-color: rgba(37,20,12,0); -fx-font-size: 22; -fx-border-width: 3; -fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");
        bestScoreLabel.setStyle("-fx-text-fill:#000000; -fx-border-color: rgba(68,34,0,0); -fx-background-color: rgba(37,20,12,0); -fx-font-size: 22; -fx-border-width: 3; -fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");
        Label levelLabel = new Label("Level: " + getCurrentLevelNumber());
        levelLabel.setStyle("-fx-text-fill:#000000; -fx-border-color: rgba(128,128,128,0); -fx-background-color: rgba(37,20,12,0); -fx-font-size: 22; -fx-border-width: 3; -fx-font-family: 'Rockwell'; -fx-font-size: 'bold'");
        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(levelLabel, scoreLabel);

        // ------------------------------------Create "Next Level" button---------------------------------------------------------
        Button nextLevelButton = new Button("Next Level");
        nextLevelButton.setOnAction(event -> {setCurrentLevelNumber(getCurrentLevelNumber()+1);startGame(); firstShuffle();});

        //---------------------------------------- Create "Replay" button------------------------------------------------------
        Button replayButton = new Button("Replay");
        replayButton.setOnAction(event -> {startGame(); firstShuffle();});

        //--------------------------------------- Create "Save score" button--------------------------------------------------
        Button saveScoreButton = new Button("Save score");

        int finalPoint = getScore();
        saveScoreButton.setOnAction(event -> saveScore(finalPoint));

        //----------------------------------------- Create "Home" button----------------------------------------------------
        Button homeButton = new Button("Home");
        homeButton.setOnAction(e -> {
            Scene leaderboardScene = getHomeScene();
            StackPane root = new StackPane();
            root.getChildren().addAll(getPrimaryStage().getScene().getRoot(), leaderboardScene.getRoot());
            getPrimaryStage().setScene( new Scene(root));
        });
        // Buttons formatting
		List<Button> buttons = Arrays.asList(nextLevelButton, replayButton, saveScoreButton, homeButton);
		for(Button button : buttons) {
			button.setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-background-color: rgba(0,0,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold' ; -fx-border-width: 3 ;");
			button.setOnMousePressed(event -> {
				button.setStyle("-fx-border-color: rgba(102,68,0,0); -fx-background-color: rgba(128,128,128,0);"); //to make grey transparent
	            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
	            pauseTransition.setOnFinished(e -> button.setStyle("-fx-text-fill:#442200 ;-fx-border-color: #442200; -fx-background-color: rgba(68,34,0,0);-fx-font-size: 18;-fx-font-family: 'Rockwell'; -fx-font-size: 'bold' ; -fx-border-width: 3 ;"));//to remove the transparent
	            pauseTransition.play();
	        });
		}

        // ----------------------------Create VBox to set label and button with space--------------------------------------
        VBox endScreenLayout = new VBox(10); // to create a vertical space 10px

        Image backgroundImage = new Image("file:background2-min.jpg");
        backgroundImage.widthProperty().add(primaryStage.widthProperty());
        backgroundImage.heightProperty().add(primaryStage.heightProperty());
        BackgroundSize backgroundSize = new BackgroundSize(1000, 1000, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        endScreenLayout.setBackground(new Background(background));

        endScreenLayout.setAlignment(Pos.CENTER); // to center the buttons
        endScreenLayout.getChildren().add(hbox); // to add the hbox
        if (getBestScore() != 0 ) {
            endScreenLayout.getChildren().add(bestScoreLabel);
        }
        if (finishGame()) {
            endScreenLayout.getChildren().addAll(replayButton, saveScoreButton, homeButton) ;
        }
        else {
            endScreenLayout.getChildren().addAll(buttons); // to add the buttons
        }


        //---------------------------------------to define the scene-----------------------------------------------------------------
        // get screensize of monitor
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

        // set the scene size to fullscreen size
        Scene endScreenScene = new Scene(endScreenLayout, screenSize.getWidth(), screenSize.getHeight());
        getPrimaryStage().setScene(endScreenScene);

    }

    /**
     * Collects the score from the "scoreLastGame.txt" file.
     *
     * @throws FileNotFoundException if the file is not found.
     */
    public static void collectPoints() throws FileNotFoundException {

        File file = new File("scoreLastGame.txt"); // to register scoreLastGame
        Scanner scoreLastGame = new Scanner(file); // collect the point
        String[] data = scoreLastGame.next().split(";");
        setScore(Integer.parseInt(data[0])); // convert string to int
        scoreLastGame.close();

    }

    public static boolean finishGame() {
        int n = levels.size() ;
        if (getCurrentLevelNumber() == n + 1 ){
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * Collects the current level from the "scoreLastGame.txt" file.
     *
     * @throws FileNotFoundException if the file is not found.
     */
    public static void collectLevel() throws FileNotFoundException {

        File file = new File("scoreLastGame.txt"); // to register scoreLastGame
        Scanner scoreLastGame = new Scanner(file); // collect the score
        String[] data = scoreLastGame.next().split(";");

        setCurrentLevelNumber(Integer.parseInt(data[1])); // convert string to int
        scoreLastGame.close();

    }

    /**
     * Saves the player score to a file.
     *
     * @param score The score to save.
     */
    public static void saveScore(int score) {

        try {
            FileWriter writer = new FileWriter("score.txt");
            writer.write("Score: " + score); // to register the score in score.txt
            writer.close();
        } catch (IOException e) {
            System.out.println("Error  : " + e.getMessage());
        }
    }

    public Integer[] sortPointsWithoutZeros(Integer[] points) {
        //To exclude zero
        List<Integer> notZeroPoints = new ArrayList<>();

        for (int point : points) {
            if (point != 0) {
                notZeroPoints.add(point);
            }
        }

        // To sort without zero
        Collections.sort(notZeroPoints);

        // to insert zero at the end
        List<Integer> sortedPoints = new ArrayList<>(notZeroPoints);
        for (int point : points) {
            if (point == 0) {
                sortedPoints.add(point);
            }
        }

        //to register the points with zero on sortedaway
        Integer[] sortedArray = new Integer[sortedPoints.size()];
        for (int i = 0; i < sortedPoints.size(); i++) {
            sortedArray[i] = sortedPoints.get(i);
        }

        return sortedArray;
    }

    public static void main(String[] args) {
        launch(args);

        currentLevel=levels.get(8).copy();
        if(Arrays.deepEquals(currentLevel.getTiles(),levels.get(8).getTiles())){
            System.out.println("youpi");
        }else {
            System.out.println("noooo");
        }
        currentLevel.print();
        currentLevel.stepByStepShuffleLevel();
        if(Arrays.deepEquals(currentLevel.getTiles(),levels.get(8).getTiles())){
            System.out.println("youpi");
        }else {
            System.out.println("noooo");
        }

        /*
        src.Node node = new src.Node(currentLevel,null,0,PuzzleSolver.calculateManhattanDistance(currentLevel));
        PuzzleSolver.solvePuzzle(currentLevel);
*/

    }
}