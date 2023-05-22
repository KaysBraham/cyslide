package src;

import java.util.Arrays;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class Chronometer extends HBox{

	private int startTime; // Holds the starting time in seconds
    private int elapsedTime = 0; // Holds the elapsed time in seconds
    private boolean running = false; // Indicates whether the chronometer is running or not
    private Label hoursLabel = new Label("");;
    private Label minutesLabel = new Label("00 : ");
    private Label secondsLabel = new Label("00");

	public Chronometer() {
		List<Label> labels = Arrays.asList(hoursLabel, minutesLabel, secondsLabel);
		for(Label label : labels)
			label.setStyle("-fx-text-fill: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leoscar'");
		
		this.getChildren().addAll(labels);
	}
    
    public int getStartTime() {
        return startTime;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    
    public boolean isRunning() {
        return running;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }

    public Label getHoursLabel() {
		return hoursLabel;
	}

	public Label getMinutesLabel() {
		return minutesLabel;
	}

	public Label getSecondsLabel() {
		return secondsLabel;
	}

	// Returns the current time in seconds
    public int currentTimeSec(){
        return (int) ((System.currentTimeMillis()) / 1000); // Using the System method currentTimeMillis() and converting it to seconds
    }

    // Starts the chronometer
    public void start(){
        if(isRunning()) return;

        setStartTime(currentTimeSec());
        setRunning(true);
    }

    // Updates the elapsed time
    public void updateElapsedTime(){
        setElapsedTime(currentTimeSec() - getStartTime());

        int hours = getElapsedTime() / 3600;
        int minutes = (getElapsedTime() / 60) % 3600;
        int seconds = getElapsedTime() % 60;
        
        getHoursLabel().setText(hours != 0 ? String.format("%02d", hours) + " : " : ""); // If one hour or more passed, display the hours
        getMinutesLabel().setText(String.format("%02d", minutes) + " : ");
        getSecondsLabel().setText(String.format("%02d", seconds));
    }

    // Stops the chronometer and calculates the elapsed time
    public void stop(){
        if(!isRunning()) return;

        updateElapsedTime();
        setRunning(false);
    }

    // Resets the chronometer to its initial state
    public void reset(){
        setElapsedTime(0);
        setRunning(false);
    }
}