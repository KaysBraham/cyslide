package src;

import java.util.Arrays;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * The Chronometer class represents a chronometer that can measure elapsed time.
 * It displays the hours, minutes, and seconds of the elapsed time.
 * It is a HBox on its own, allowing the 3 fields to display properly.
 */
public class Chronometer extends HBox{
	
	/**
	 * Holds the starting time in seconds.
	 */
	private int startTime;
	
	/**
	 * Holds the elapsed time in seconds. Initialized to 0.
	 */
    private int elapsedTime = 0;
    
    /**
     * Indicates whether the chronometer is running or not.
     */
    private boolean running = false;
    
    /**
     * Label for displaying hours.
     */
    private Label hoursLabel = new Label("");
    
    /**
     * Label for displaying minutes.
     */
    private Label minutesLabel = new Label("00 : ");
    
    /**
     * Label for displaying seconds.
     */
    private Label secondsLabel = new Label("00");

    /**
     * Constructs a Chronometer object.
     * Initializes the labels for hours, minutes, and seconds.
     */
	public Chronometer() {
		List<Label> labels = Arrays.asList(hoursLabel, minutesLabel, secondsLabel);
		for(Label label : labels)
			label.setStyle("-fx-text-fill: white; -fx-background-color: black;-fx-font-size: 18;-fx-font-family: 'Leoscar'");
		
		this.getChildren().addAll(labels);
	}

    /**
     * Returns the starting time of the chronometer.
     *
     * @return The starting time in seconds.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Sets the starting time of the chronometer.
     *
     * @param startTime The starting time to set in seconds.
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the elapsed time of the chronometer.
     *
     * @return The elapsed time in seconds.
     */
    public int getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Sets the elapsed time of the chronometer.
     *
     * @param elapsedTime The elapsed time to set in seconds.
     */
    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * Checks if the chronometer is running.
     *
     * @return true if the chronometer is running, false otherwise.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Sets the running state of the chronometer.
     *
     * @param running The running state to set (boolean).
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Returns the label displaying the hours.
     *
     * @return The label displaying the hours.
     */
    public Label getHoursLabel() {
		return hoursLabel;
	}

    /**
     * Returns the label displaying the minutes.
     *
     * @return The label displaying the minutes.
     */
	public Label getMinutesLabel() {
		return minutesLabel;
	}

    /**
     * Returns the label displaying the seconds.
     *
     * @return The label displaying the seconds.
     */
	public Label getSecondsLabel() {
		return secondsLabel;
	}

    /**
     * Returns the current time in seconds.
     * Converts the System.currentTimeMillis() output to seconds.
     *
     * @return The current time in seconds.
     */
    public int currentTimeSec(){
        return (int) ((System.currentTimeMillis()) / 1000);
    }

    /**
     * Starts the chronometer.
     * If the chronometer is already running, does nothing.
     */
    public void start(){
        if(isRunning()) return;

        setStartTime(currentTimeSec());
        setRunning(true);
    }

    /**
     * Updates the elapsed time based on the current time.
     * Calculates the hours, minutes, and seconds of the elapsed time and updates the corresponding labels.
     */
    public void updateElapsedTime(){
        setElapsedTime(currentTimeSec() - getStartTime());

        int hours = getElapsedTime() / 3600;
        int minutes = (getElapsedTime() / 60) % 60;
        int seconds = getElapsedTime() % 60;
        
        getHoursLabel().setText(hours != 0 ? String.format("%02d", hours) + " : " : ""); // If one hour or more passed, display the hours
        getMinutesLabel().setText(String.format("%02d", minutes) + " : ");
        getSecondsLabel().setText(String.format("%02d", seconds));
    }

    /**
     * Stops the chronometer and calculates the elapsed time.
     * If the chronometer is not running, does nothing.
     * Updates the elapsed time and sets the running state to false.
     */
    public void stop(){
        if(!isRunning()) return;

        updateElapsedTime();
        setRunning(false);
    }

    /**
     * Resets the chronometer to its initial state.
     * Sets the elapsed time to 0 and the running state to false.
     */
    public void reset(){
        setElapsedTime(0);
        setRunning(false);
    }
}