package com.example.cyslidealif;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



public class EndScreen extends Application {

    public MainMenu mainmenu=new MainMenu();

    private int point;
    private int level;


    public EndScreen() {
    }


    public int collectpoint() throws FileNotFoundException {

        File file = new File("pointlastgame.txt"); //to register pointlastgame
        Scanner pointlastgame = new Scanner(file); //collect the point
        String[] data = pointlastgame.next().split(";");
        this.point = Integer.parseInt(data[0]);//convert string to int

        pointlastgame.close();

        return point;//return point

    }
    public int collectlevel() throws FileNotFoundException {
        File file = new File("pointlastgame.txt"); //to register pointlastgame
        Scanner pointlastgame = new Scanner(file); //collect the point
        String[] data = pointlastgame.next().split(";");

        this.level = Integer.parseInt(data[1]);//convert string to int
        pointlastgame.close();

        return level;//return point



    }
    public void show(){
        launch();
    }


    public void start(Stage endStage) throws FileNotFoundException {

        point = collectpoint();


        level = collectlevel();

        //-------------------------------------create point label----------------------------------------------------------------
        Label pointLabel = new Label("Points: " +point ); //to print point
        pointLabel.setStyle("-fx-text-fill: white;-fx-font-size: 15;-fx-font-family: 'Leoscar'");
        Label pointLevel = new Label("Level: " +level );// to print level
        pointLevel.setStyle("-fx-text-fill: white;-fx-font-size: 15;-fx-font-family: 'Leoscar'");
        HBox hbox = new HBox(5); //to create a vertical space 10px
        hbox.setAlignment(Pos.CENTER); //to center the buttons
        hbox.getChildren().addAll(pointLabel,pointLevel); //to align horizontally point+level


        // ------------------------------------Create "Try Again" button---------------------------------------------------------
        Button tryAgainButton = new Button("Try Again");
        tryAgainButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'"); //set the style of the button
        tryAgainButton.setOnMousePressed(event -> {
            tryAgainButton.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
            pauseTransition.setOnFinished(e -> tryAgainButton.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'"));//to remove the transparent
            pauseTransition.play();
        });




        //---------------------------------------- Create "Replay" button------------------------------------------------------
        Button replay = new Button("Replay");
        replay.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'");//set the style of the button
        replay.setOnMousePressed(event -> {
            replay.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
            pauseTransition.setOnFinished(e -> replay.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'"));//to remove the transparent
            pauseTransition.play();
        });
        replay.setOnAction(event -> { //to check if the user push the button
            endStage.close();
            mainmenu.start_new_game();
        });


        //--------------------------------------- Create "Save score" button--------------------------------------------------
        Button savescore = new Button("Save score");


        int finalPoint = point;
        savescore.setOnAction(event -> { //to check if the user push the button

            saveScore(finalPoint);
        });
        savescore.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'");//set the style of the button
        savescore.setOnMousePressed(event -> {
            savescore.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
            pauseTransition.setOnFinished(e -> savescore.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'"));//to remove the transparent
            pauseTransition.play();
        });


        //----------------------------------------- Create "Home" button----------------------------------------------------
        Button home = new Button("Home");

        home.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'");//set the style of the button
        home.setOnMousePressed(event -> {
            home.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
            pauseTransition.setOnFinished(e -> home.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'"));//to remove the transparent
            pauseTransition.play();
        });
        home.setOnAction(event -> { //to check if the user push the button
            leave(endStage);

            try {
                home();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        // ---------------------------------------Create "leave" button---------------------------------------------------
        Button leave = new Button("leave");

        leave.setOnAction(event -> { //to check if the user push the button
            leave(endStage);
        });
        leave.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'");
        leave.setOnMousePressed(event -> {
            leave.setStyle("-fx-border-color: black; -fx-background-color: grey;"); //to make grey transparent
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));//during 0.1 second
            pauseTransition.setOnFinished(e -> leave.setStyle("-fx-text-fill: white;-fx-border-color: white; -fx-background-color: black;-fx-font-size: 15;-fx-font-family: 'Leoscar'"));//to remove the transparent
            pauseTransition.play();
        });

        // ----------------------------Create VBox to set label and button with space--------------------------------------
        VBox vbox = new VBox(10); //to create a vertical space 10px
        vbox.setAlignment(Pos.CENTER); //to center the buttons
        vbox.getChildren().addAll(hbox, tryAgainButton,replay,savescore,home,leave); //to add the



        BackgroundFill black = new BackgroundFill(Color.BLACK, null, null);//set on black
        Background background = new Background(black); //set the color defined by background
        vbox.setBackground(background);

//---------------------------------------to define the scene-----------------------------------------------------------------
        Scene scene = new Scene(vbox, 300, 300);


        //To define the scene
        endStage.setScene(scene);
        endStage.setTitle("end of the game");//set the name of the menu

        // to print the show
        endStage.show();
    }



    public void saveScore(int point) {

        try{ //to check the availability of score
            FileWriter writer = new FileWriter("point.txt");
            writer.write("Score: " + point); //to register the point on point.txt
            writer.close();
        } catch (IOException e) {
            System.out.println("Error  : " + e.getMessage());
        }

    }

    public void leave(Stage endStage){
        endStage.close(); //to close the menu

    }


    public static void main(String[] args) {
        EndScreen end=new EndScreen();
        end.show();

    }
    public void home() throws FileNotFoundException {
        Stage stage=new Stage();
        mainmenu.start(stage);
    }
}



/* public void tryAgain(self){}
    public void replay(self){}


    */
