package events;

import simulation.Patient;

import java.util.ArrayList;

import static simulation.Clock.getCurrentClockTime;

public abstract class Event implements EventActions {

    protected int processingTime;
    protected int startTime;
    protected int expectedEndingTime;
    protected boolean hasStarted;
    protected boolean shouldStart;
    protected boolean isDone = false;
    protected Patient patient;
    public static ArrayList<Event> events = new ArrayList<>();

    protected Event(){
        events.add(this);
        hasStarted = false;
        shouldStart = false;
    }

    public void start() {
        startTime = getCurrentClockTime();
        expectedEndingTime = startTime+processingTime;
        hasStarted = true;
        shouldStart = false;
    }

    public boolean isDone(){
        return isDone;
    }

    public int getExpectedEndingTime() {
        return expectedEndingTime;
    }

    public Patient getPatient(){
        return patient;
    }

    public boolean hasStarted() {
        return hasStarted;
    }

    public boolean shouldStart(){
        return shouldStart;
    }

    public void setShouldStart(boolean shouldStart) {
        this.shouldStart = shouldStart;
    }

    public boolean shouldFinish(){
        return expectedEndingTime == getCurrentClockTime();
    }
}
