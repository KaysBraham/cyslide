package src;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;




public class MainMenu extends Application {
    private Label labellevel;

    private static Stage stage;
    Taquin taquin=new Taquin();

    public static void setStage(Stage stage) {
        MainMenu.stage = stage;
    }
    public static Stage getStage() {
        return stage;
    }

    public static int getLevel() {
        return level;
    }

    public static int level=1;


    public void show(){

        launch();
    }


    public void start(Stage MainStage) {
        setStage(MainStage);


        labellevel = new Label("level: " +getLevel()); //to print level
        labellevel.setStyle("-fx-text-fill: white;-fx-font-size: 15;-fx-font-family: 'Leoscar'");

        // ------------------------------------Create "new_game_button"---------------------------------------------------------
        Button new_game_button = new Button("New game");
        new_game_button.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'"); //set the style of the button
        new_game_button.setOnMousePressed(event -> {
            new_game_button.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
            pauseTransition.setOnFinished(e -> new_game_button.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'"));//to remove the transparent
            pauseTransition.play();
        });
        new_game_button.setOnAction(event -> { //to check if the user push the button
            MainStage.close();
            start_new_game();
        });

        // ------------------------------------"Difficulty button" ---------------------------------------------------------
        Button Difficulty = new Button("Choose difficulty");

        Difficulty.setOnAction(event -> { //to check if the user push the button
            MainStage.close();
            open_difficulty_settings();
        });
        Difficulty.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'");//set the style of the button
        Difficulty.setOnMousePressed(event -> {
            Difficulty.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
            pauseTransition.setOnFinished(e -> Difficulty.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'"));//to remove the transparent
            pauseTransition.play();
        });


        // ------------------------------------"ranking_button" ---------------------------------------------------------
        Button ranking = new Button("ranking");

        ranking.setOnAction(event -> { //to check if the user push the button

            try {
                show_leaderboard();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        ranking.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'");//set the style of the button
        ranking.setOnMousePressed(event -> {
            ranking.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
            pauseTransition.setOnFinished(e -> ranking.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'"));//to remove the transparent
            pauseTransition.play(); //un bouton pour afficher le classement des meilleurs scores.
        });
        // ---------------------------------------Create "leave" button---------------------------------------------------
        Button leave = new Button("leave");

        leave.setOnAction(event -> { //to check if the user push the button

            quit_game(MainStage);
        });
        leave.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'");
        leave.setOnMousePressed(event -> {
            leave.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
            pauseTransition.setOnFinished(e -> leave.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'"));//to remove the transparent
            pauseTransition.play();
        });


        // ----------------------------Create VBox to set label and button with space--------------------------------------
        VBox vbox = new VBox(10); //to create a vertical space 10px
        vbox.setAlignment(Pos.CENTER); //to center the buttons
        vbox.getChildren().addAll(labellevel,new_game_button,Difficulty,ranking,leave); //to add the



        BackgroundFill black = new BackgroundFill(Color.BLACK, null, null);//set on black
        Background background = new Background(black); //set the color defined by background
        vbox.setBackground(background);


        //---------------------------------------to define the scene-----------------------------------------------------------------
        Scene scene = new Scene(vbox, 300, 300);


        //To define the scene
        MainStage.setScene(scene);
        MainStage.setTitle("Main menu");//set the name of the menu

        // to print the show
        MainStage.show();



    }



    //la fenêtre principale tkinter dans laquelle l'écran d'accueil est affiché.





    public void start_new_game(){//new games

        taquin.start();


    }


    public void open_difficulty_settings() {

        Stage difficultystage = new Stage();
        VBox difficultycontainer = new VBox(10);
        difficultycontainer.setAlignment(Pos.CENTER); //to center the buttons


        for (int difficulty = 1; difficulty <= 10; difficulty++) {

            Button levelButton = new Button("Level " + difficulty);
            levelButton.setPrefSize(100,50); //set the button size

            levelButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'");//set the style of the button
            int finalDifficulty = difficulty;
            levelButton.setOnMousePressed(event -> {
                labellevel.setText("level: " + finalDifficulty);
                levelButton.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
                pauseTransition.setOnFinished(e -> levelButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'"));//to remove the transparent
                pauseTransition.play();
            });

            difficultycontainer.getChildren().add(levelButton);

        }
        BackgroundFill black = new BackgroundFill(Color.BLACK, null, null);//set on black
        Background background = new Background(black); //set the color defined by background
        difficultycontainer.setBackground(background);
        Button backButton = new Button("Return");
        backButton.setPrefSize(100,50);//set the button size
        backButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'");//set the style of the button

        difficultycontainer.getChildren().add(backButton);
        backButton.setOnAction(event -> {

            difficultystage.close();
            MainMenu.getStage().show();


        });
        Scene difficultyscene = new Scene(difficultycontainer);


        difficultystage.setScene(difficultyscene);
        difficultystage.show();

    } //une méthode appelée lorsque le bouton "Réglage de difficulté" est cliqué.



    public void show_leaderboard() throws FileNotFoundException {//To see the ranking


        Stage rankingstage = new Stage();
        VBox rankingcontainer = new VBox(10);//to print button on vertical
        rankingcontainer.setAlignment(Pos.CENTER); //to center the buttons


//-------------------------------------------------difficulty button---------------------------------------------------------
        for (int difficulty = 1; difficulty <= 10; difficulty++) {//to print the button


            Button levelButton = new Button("Level " + difficulty);
            levelButton.setPrefSize(100,50);

            levelButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'");//set the style of the button
            int finalDifficulty = difficulty;
            levelButton.setOnMousePressed(event -> {
                rankingcontainer.getChildren().clear();//to remove all the button in order to print th ranking
                levelButton.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent


                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
                pauseTransition.play();

                File file = new File("pointlastgame.txt."); //to retrieve point
                //collect the point
                Scanner pointlastgame;
                try {
                    pointlastgame = new Scanner(file);

                    String[] data = pointlastgame.next().split(";");//to separate point and level according to ;
                    Label[] labelrank = new Label[10];
                    Integer[] listpoint = new Integer[10];
                    Arrays.fill(listpoint, 0);//to initialize in zer0
                    int count=0;
                    do {
                        if (Integer.parseInt(data[1]) == finalDifficulty) {
                            listpoint[count]= Integer.parseInt(data[0]);


                            count++;

                        }

                        if (pointlastgame.hasNextLine()) {
                            data = pointlastgame.nextLine().split(";");
                        }else{
                            break;
                        }
                    }while (pointlastgame.hasNextLine());
                    count=0;

                    Arrays.sort(listpoint, Comparator.reverseOrder()); //to sort in reverse
                    for (Integer integer : listpoint) {

                        if(count==10){
                            break;
                        }
                        else{
                            labelrank[count] = new Label("Point: " + integer + " | Level: " + finalDifficulty);//to set the rank
                            labelrank[count].setStyle("-fx-text-fill: white;-fx-font-size: 15;-fx-font-family: 'Leos-car'");
                            rankingcontainer.getChildren().add(labelrank[count]);//add the ranking into the container
                            count++;
                        }
                    }
                    pointlastgame.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            });

            rankingcontainer.getChildren().add(levelButton);

        }
        BackgroundFill black = new BackgroundFill(Color.BLACK, null, null);//set on black
        Background background = new Background(black); //set the color defined by background
        rankingcontainer.setBackground(background);

        Button backButton = new Button("Return");
        backButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leos-car'");//set the style of the button
        backButton.setPrefWidth(100);
        backButton.setPrefHeight(50);
        rankingcontainer.getChildren().add(backButton);
        backButton.setOnAction(event -> {

            rankingstage.close();
            MainMenu.getStage().show();


        });
        Scene difficultyscene = new Scene(rankingcontainer);

        rankingstage.setScene(difficultyscene);
        rankingstage.show();


    }

    public void quit_game(Stage Mainmenu){//quit the game.
        Mainmenu.close();


    }

    public static void main(String[] args) {
        MainMenu main=new MainMenu();
        main.show();


    }


}