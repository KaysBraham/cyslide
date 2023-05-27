package src;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.* ;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Math;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;



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
    private static int moveCount;

    /**
     * The primary stage of the JavaFX application.
     */
    private Stage primaryStage;

    /**
     * The scene representing the home screen of the game.
     */
    private Scene homeScene;

    /**
     * The scene representing the difficultylayyout of the game.
     */
    private Scene difficultylayout;

    /**
     * The scene representing the leaderboardlayout of the game.
     */
    private Scene leaderboardlayout;



    /**
     * The sequential transition used as a timer in the game.
     */
    private SequentialTransition timer = null;

    /**
     * The label displaying the current level.
     */
    private Label levelLabel;

    static Label moveCountLabel = new Label("Move count : " + moveCount );
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

    private static Pair<Integer, Integer> selectedCell = null;

    static GridPane gridLayout = new GridPane();

    /**
     * Returns a list containing the levels.
     *
     * @return The level list.
     */
    public static List<Level> getLevels() {
        return levels;
    }

    /**
    /**
     * Returns a list of a booleans.
     *
     * @return The boolean list.
     */
    private boolean[] levelsWon;

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

    private static void updateMoveCountLabel() {
        moveCountLabel.setText("Move count : " + moveCount);
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

    public static Pair<Integer, Integer> getSelectedCell() {
		return PuzzleGame.selectedCell;
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
		startButton.setOnAction(e -> startGame());

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
            button.setStyle("-fx-text-fill:#420; -fx-border-color: #420; -fx-background-color: rgba(37,20,12,0); -fx-font-size: 22; -fx-border-width: 3; -fx-font-family: 'Rockwell'; -fx-font-weight: 'bold'");
			button.setOnMousePressed(event -> {
				button.setStyle("-fx-border-color: black; -fx-background-color: #000000;"); // to make grey transparent
	            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1)); // during 0.1 second
	            pauseTransition.setOnFinished(e -> button.setStyle("-fx-text-fill:#420; -fx-border-color: #420; -fx-background-color: rgba(37,20,12,0); -fx-font-size: 22; -fx-border-width: 3; -fx-font-family: 'Rockwell'; -fx-font-weight: 'bold'"));//to remove the transparent
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


        StackPane circleStackPane = new StackPane(rectangle, levelLabel); //to superimpose circle and label
        circleStackPane.setAlignment(Pos.CENTER);
        homeLayout.getChildren().add(circleStackPane);

        HBox buttonsBox = new HBox(10); // To adjust space between button
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(buttons);

        homeLayout.getChildren().addAll(buttonsBox);

        root.getChildren().add(homeLayout);// to add homeLayout in order to superimpose

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

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Starts a new game by setting up the play screen scene and initializing the game components.
     */
    public void startGame(){


        moveCountLabel.setStyle("-fx-font-size: 25px ; -fx-font-family: 'Rockwell'");
        levelsWon = new boolean[getLevels().size()];


        if (getCurrentLevelNumber() > 1) {
            for (int i = 0; i < getCurrentLevelNumber() - 1; i++) {
                if (!levelsWon[i]) {
                    showMessage("You must win the previous levels before playing this one.");
                    return;
                }
            }
        }

        Label titleLabel = new Label("Resolved \n level");
        titleLabel.setStyle("-fx-font-size: 15px ;");

        Image backgroundImage = new Image("file:background2-min.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        VBox playLayout = new VBox(32);
    	playLayout.setAlignment(Pos.CENTER);
        playLayout.setBackground(new Background(background));

        
        HBox topLayout = new HBox(64);
        topLayout.setAlignment(Pos.CENTER);


        gridLayout.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        solvedLevelPreview(grid);



        setUndoButton(new Button("Undo"));
        getUndoButton().setStyle("-fx-font-size:32");
        getUndoButton().setDisable(true);
        getUndoButton().setOnAction(e -> {undoMove(); moveCount = 0 ;});

        setRedoButton(new Button("Redo"));
        getRedoButton().setStyle("-fx-font-size:32");
        getRedoButton().setDisable(true);
        getRedoButton().setOnAction(e -> {redoMove(); moveCount -- ;});

        setCurrentLevel(getLevels().get(getCurrentLevelNumber() - 1).copy());
        currentLevel.randomShuffleLevel();
        tileGridConstuctor(gridLayout);


        setRandomShuffleButton(new Button("Random Shuffle"));
        getRandomShuffleButton().setStyle("-fx-font-size:25");
        getRandomShuffleButton().setOnAction(e -> {currentLevel.randomShuffleLevel();
            tileGridConstuctor(gridLayout); });

        setStepByStepShuffleButton(new Button("Step by step Shuffle"));
        getStepByStepShuffleButton().setStyle("-fx-font-size:25");
        getStepByStepShuffleButton().setOnAction(e -> {currentLevel.stepByStepShuffleLevel();
                                                        tileGridConstuctor(gridLayout); });

        topLayout.getChildren().addAll(getUndoButton(), getRedoButton(), getRandomShuffleButton(), getStepByStepShuffleButton());

        HBox bottomLayout = new HBox(64);
        bottomLayout.setAlignment(Pos.CENTER);

        HBox resolvedLevelLayout = new HBox();

        resolvedLevelLayout.getChildren().addAll(titleLabel, grid) ;

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


        bottomLayout.getChildren().addAll(moveCountLabel,chronometer, giveUpButton, resolvedLevelLayout);

        playLayout.getChildren().addAll(topLayout, gridLayout, bottomLayout);

    	Scene playScene = new Scene(playLayout, 1600, 900); // HD+ scene

/*        playScene.setOnKeyPressed(event -> {
            int emptyRow = currentLevel.getEmptyTiles()[0][0];
            int emptyCol = currentLevel.getEmptyTiles()[0][1];
            int tileRow = 0;
            int tileCol = 0;
            switch (event.getCode()) {
                case UP : {
                    tileRow = emptyRow;
                    tileCol = emptyCol - 1;
                    currentLevel.swapTile(tileRow, tileCol, emptyRow, emptyCol);
                    tileGridConstuctor(gridLayout);
                    break;
                }
                case DOWN : {
                    tileRow = emptyRow;
                    tileCol = emptyCol + 1;
                    currentLevel.swapTile(tileRow, tileCol, emptyRow, emptyCol);
                    tileGridConstuctor(gridLayout);
                    break;
                }
                case LEFT : {
                    tileRow = emptyRow - 1;
                    tileCol = emptyCol;
                    currentLevel.swapTile(tileRow, tileCol, emptyRow, emptyCol);
                    tileGridConstuctor(gridLayout);
                    break;
                }
                case RIGHT : {
                    tileRow = emptyRow + 1;
                    tileCol = emptyCol;
                    currentLevel.swapTile(tileRow, tileCol, emptyRow, emptyCol);
                    tileGridConstuctor(gridLayout);
                    break;
                }
                case ENTER : {
                    if (emptyRow == currentLevel.getEmptyTiles()[0][0] && emptyCol == currentLevel.getEmptyTiles()[0][1]) {
                        emptyRow = currentLevel.getEmptyTiles()[1][0];
                        emptyCol = currentLevel.getEmptyTiles()[1][1];
                    } else {
                        emptyRow = currentLevel.getEmptyTiles()[0][0];
                        emptyCol = currentLevel.getEmptyTiles()[0][1];
                    }
                    break;
                }
                default : {
                }
            }
        });
*/

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
    static int mousePosX;
    static int mousePosY;
    static int initialTranslateX;
    static int initialTranslateY;
    static final int TILE_SIZE = 200;
    private static void tileGridConstuctor(GridPane gridLayout) {
        gridLayout.getChildren().clear();
        for (int i =0;i<currentLevel.getTiles().length;i++) {
            for(int j =0;j<currentLevel.getTiles()[0].length;j++) {

                currentLevel.getTiles()[i][j].setPrefSize(TILE_SIZE,TILE_SIZE);


                switch(currentLevel.getTiles()[i][j].getValue()) {
                    case -1:
                        currentLevel.getTiles()[i][j].setVisible(false);
                        break;
                    case 0:
                        currentLevel.getTiles()[i][j].setText("");
                        currentLevel.getTiles()[i][j].setStyle("-fx-background-color: #fc6;"); // light wood
                        break;
                    default:
                        currentLevel.getTiles()[i][j].setText(Integer.toString(currentLevel.getTiles()[i][j].getValue()));
                        currentLevel.getTiles()[i][j].setStyle("-fx-font-size: 38;"
                                + "-fx-text-fill: #fff;"
                                + "-fx-border-width: 2;"
                                + "-fx-border-color: #420;" // very dark brown
                                + "-fx-background-color: #640;"); // dark wood
                        currentLevel.getTiles()[i][j].setAlignment(Pos.CENTER);
                }
                if(currentLevel.getTiles()[i][j].getValue()!=-1){
                    int finalI4 = i;
                    int finalJ4 = j;
                    currentLevel.getTiles()[i][j].setOnDragDetected(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            mousePosX = (int) event.getSceneX();
                            mousePosY = (int) event.getSceneY();
                            initialTranslateX = (int) currentLevel.getTiles()[finalI4][finalJ4].getTranslateX();
                            initialTranslateY = (int) currentLevel.getTiles()[finalI4][finalJ4].getTranslateY();

                            currentLevel.getTiles()[finalI4][finalJ4].startDragAndDrop(TransferMode.MOVE);

                            ClipboardContent content = new ClipboardContent();
                            content.putString("");  // Ajoutez ici le contenu de l'objet tuile
                            Dragboard dragboard = currentLevel.getTiles()[finalI4][finalJ4].startDragAndDrop(TransferMode.MOVE);
                            dragboard.setContent(content);

                            event.consume();
                            System.out.println("OnDragDetected");

                        }
                    });

                    currentLevel.getTiles()[i][j].setOnDragOver(new EventHandler<DragEvent>() {
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
                    currentLevel.getTiles()[i][j].setOnDragEntered(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* Change le style de la tuile survolée */
                            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                                currentLevel.getTiles()[finalI3][finalJ3].setOpacity(0.7);
                            }
                            event.consume();
                        }
                    });

                    int finalI = i;
                    int finalJ = j;
                    currentLevel.getTiles()[i][j].setOnDragExited(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* Rétablit le style de la tuile */
                            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                                currentLevel.getTiles()[finalI][finalJ].setOpacity(1.0);
                            }
                            event.consume();

                        }
                    });

                    int finalI1 = i;
                    int finalJ1 = j;
                    currentLevel.getTiles()[i][j].setOnDragDropped(new EventHandler<DragEvent>() {
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
                                System.out.println("OnDragDropped");
                                System.out.println("offsetX offsetY");
                                System.out.println(offsetX+" "+offsetY);
                                System.out.println("colOffset rowOffset");
                                System.out.println(colOffset+" "+rowOffset);

                                if ((Math.abs(colOffset) + Math.abs(rowOffset) == 1) && currentLevel.getTiles()[finalI1][finalJ1].getValue()==0) {
                                    int originCol = finalJ1 - colOffset;
                                    int originRow = finalI1 - rowOffset;
                                    System.out.println("finalJ1 finalI1");
                                    System.out.println(finalJ1 +" "+ finalI1);
                                    System.out.println(originCol+" "+originRow);
                                    swapTiles(finalJ1, finalI1, originCol, originRow);


                                    event.setDropCompleted(true);


                                    return;
                                }
                            }
                            event.setDropCompleted(false);
                            event.consume();
                        }
                    });

                    int finalI2 = i;
                    int finalJ2 = j;
                    currentLevel.getTiles()[i][j].setOnDragDone(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* Rétablit le style de la tuile une fois le glisser-déposer terminé */
                            currentLevel.getTiles()[finalI2][finalJ2].setOpacity(1.0);
                            event.consume();
                        }
                    });
                }
                gridLayout.add(currentLevel.getTiles()[i][j], j, i);
            }
        }
    }
    private static void swapTiles(int col1, int row1, int col2, int row2) {
        Node tile1 = getNodeByRowColumnIndex(row1, col1);
        Node tile2 = getNodeByRowColumnIndex(row2, col2);
        System.out.println("swaptile1");
        if (tile1 != null && tile2 != null) {
            currentLevel.swapTile(row1,col1,row2,col2);
            int tile1Index = gridLayout.getChildren().indexOf(tile1);
            int tile2Index = gridLayout.getChildren().indexOf(tile2);
            System.out.println("swaptile2");
            GridPane.setConstraints(tile1, col2, row2);
            GridPane.setConstraints(tile2, col1, row1);
            System.out.println("swaptile3");
            gridLayout.getChildren().set(tile2Index, new Button());
            gridLayout.getChildren().set(tile1Index, tile2);
            System.out.println("swaptile4");
            gridLayout.getChildren().set(tile2Index, tile1);
            System.out.println("Grid disposition :");
            getCurrentLevel().print();
            tileGridConstuctor(gridLayout);
            moveCount ++;
            updateMoveCountLabel() ;
        }
    }
    private static Node getNodeByRowColumnIndex(final int row, final int col) {
        for (Node node : gridLayout.getChildren()) {

            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                System.out.println("coucou1");
                return node;
            }
        }
        return null;
    }

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
	private boolean isGameFinished() {
		// TODO
        //levelsWon[levelNumber - 1] = true;
		return false;
	}

    /**
     * Opens the difficulty settings screen.
     */


    public Scene getDifficultylayout() {
        return difficultylayout;
    }

    public void setDifficultylayout() {
        VBox difficultyLayout = new VBox(10);
        difficultyLayout.setAlignment(Pos.CENTER);
        difficultyLayout.setStyle("-fx-background-color: black;");

        for (int difficulty = 1; difficulty <= 10; difficulty++) {
            Button levelButton = new Button("Level " + difficulty);
            levelButton.setPrefSize(100,50); //set the button size

            levelButton.setStyle("-fx-text-fill:#e19116 ;-fx-border-color: black; -fx-background-color: #25140c;-fx-font-size: 18;-fx-font-family: 'Leoscar'");//set the style of the button
            int finalDifficulty = difficulty;
            levelButton.setOnMousePressed(event -> {
                // TODO
                setCurrentLevelNumber(finalDifficulty);
                getLevelLabel().setText("level: " + finalDifficulty); // updating the label in home screen
                levelButton.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
                pauseTransition.setOnFinished(e -> levelButton.setStyle("-fx-text-fill:#e19116 ;-fx-border-color: black; -fx-background-color: #25140c;-fx-font-size: 18;-fx-font-family: 'Leoscar'"));//to remove the transparent
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
        backButton.setStyle("-fx-text-fill:#e19116 ;-fx-border-color: black; -fx-background-color: #25140c;-fx-font-size: 18;-fx-font-family: 'Leoscar'");//set the style of the button
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
     * Opens the leaderboard screen.
     */

    public Scene getLeaderboardlayout() {
        return leaderboardlayout;
    }

    public void setLeaderboardlayout() {
        VBox leaderboardLayout = new VBox(10);

        leaderboardLayout.setStyle("-fx-background-color: black;");
        leaderboardLayout.setAlignment(Pos.CENTER); //to set all the button on the center
        //create back button
        Button backButton = new Button("Return");
        backButton.setStyle("-fx-text-fill:#e19116 ;-fx-border-color: black; -fx-background-color: #25140c;-fx-font-size: 18;-fx-font-family: 'Leoscar'");//set the style of the button
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
            levelButton.setPrefSize(100,50);

            levelButton.setStyle("-fx-text-fill:#e19116 ;-fx-border-color: black; -fx-background-color: #25140c;-fx-font-size: 18;-fx-font-family: 'Leoscar'");//set the style of the button
            int finalDifficulty = difficulty;
            levelButton.setOnMousePressed(event -> {
                leaderboardLayout.getChildren().clear(); // to remove all the button in order to print th ranking

                levelButton.setStyle("-fx-border-color: black; -fx-background-color: grey;"); // to make grey transparent

                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1)); // during 0.1 second
                pauseTransition.play();

                File file = new File("scoreLastGame.txt."); // to retrieve point

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
                            Label rankLabel = new Label((count + 1) + ".");
                            rankLabel.setStyle("-fx-text-fill: #e19116;-fx-font-size: 18;-fx-font-family: 'Leos-car'");
                            rankLabel.setMinWidth(35);//to avoid the points to move when the ranking is ten
                            labelrank[count] = new Label("Point: " + integer); // to set the rank
                            labelrank[count].setStyle("-fx-text-fill: #e19116;-fx-font-size: 18;-fx-font-family: 'Leos-car'");
                            HBox labelRanking = new HBox(10); //create horizontal box


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
        homeButton.setOnAction(e -> {
            Scene leaderboardScene = getHomeScene();
            StackPane root = new StackPane();
            root.getChildren().addAll(getPrimaryStage().getScene().getRoot(), leaderboardScene.getRoot());
            getPrimaryStage().setScene( new Scene(root));
        });
        // Buttons formatting
		List<Button> buttons = Arrays.asList(tryAgainButton, replayButton, saveScoreButton, homeButton);
		for(Button button : buttons) {
			button.setStyle("-fx-text-fill:rgb(255,255,255) ;-fx-border-color: #e70000; -fx-background-color: #ffffff;-fx-font-size: 18;-fx-font-family: 'Leoscar'");
			button.setOnMousePressed(event -> {
				button.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
	            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
	            pauseTransition.setOnFinished(e -> button.setStyle("-fx-text-fill:#e19116 ;-fx-border-color: black; -fx-background-color: #25140c;-fx-font-size: 18;-fx-font-family: 'Leoscar'"));//to remove the transparent
	            pauseTransition.play();
	        });
		}
        
        // ----------------------------Create VBox to set label and button with space--------------------------------------
        VBox endScreenLayout = new VBox(10); // to create a vertical space 10px
        endScreenLayout.setAlignment(Pos.CENTER); // to center the buttons
        endScreenLayout.getChildren().add(hbox); // to add the hbox
        endScreenLayout.getChildren().addAll(buttons); // to add the buttons
        endScreenLayout.setStyle("-fx-background-color: black;");//

        //---------------------------------------to define the scene-----------------------------------------------------------------
        Scene endScreenScene = new Scene(endScreenLayout, 640, 480);
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


    /*
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
        /*
        currentLevel.print();
        getLevel(currentLevel.getLevelNumber()).print();

        currentLevel.stepByStepShuffleLevel();

        currentLevel.print();
        */
        //fin test
    }
}