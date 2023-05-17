package src;
public class Chronometer {
    private int startTime; // Holds the starting time in seconds
    private int elapsedTime = 0; // Holds the elapsed time in seconds
    private boolean running = false; // Indicates whether the chronometer is running or not
    
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
    // Updates the elapsed time
    public void updateElapsedTime(){
        setElapsedTime(getElapsedTime() + currentTimeSec() - getStartTime());
    }
    
    public boolean isRunning() {
        return running;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }

    // Returns the current time in seconds
    public int currentTimeSec(){
        return (int) ((System.currentTimeMillis()) / 1000); // Using the System method currentTimeMillis() and converting it to seconds
    }

    // Starts the chronometer
    public void start(){
        if(!isRunning()){
            setStartTime(currentTimeSec());
            setRunning(true);
        }
    }

    // Stops the chronometer and calculates the elapsed time
    public void stop(){
        if(isRunning()){
            updateElapsedTime();
            setRunning(false);
        }
    }

    // Resets the chronometer to its initial state
    public void reset(){
        setElapsedTime(0);
        setRunning(false);
    }
}