package events;

import simulation.Patient;
import java.util.ArrayList;
import static simulation.Clock.getCurrentClockTime;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Fall 2021
 *
 * This class takes care of the event that happen in the hospital simulation
 *
 * @author The Boyz
 * @version 1
 */
public abstract class Event implements EventActions {

    protected int processingTime;
    protected int startTime;
    protected int expectedEndingTime;
    protected boolean hasStarted;
    protected boolean shouldStart;
    protected boolean isDone = false;
    protected Patient patient;
    public static ArrayList<Event> events = new ArrayList<>();
/*
This Constructor adds the event happened into the arrayList
setting boolean values of event to false initially
 */
    protected Event(){
        events.add(this);
        hasStarted = false;
        shouldStart = false;
    }
/*
This class allows the event to start
Takes the current clock time when the event started
Set the startedEvent to true.
 */
    public void start() {
        startTime = getCurrentClockTime();
        expectedEndingTime = startTime+processingTime;
        hasStarted = true;
        shouldStart = false;
    }
    /**
     * Returns a boolean checking if the event is done or not
     * @return if the event done (true or false)
     * */
    public boolean isDone(){
        return isDone;
    }

    /**
     * Returns a the Patient that is in the current event
     * @return Returns the patient
     */

    public Patient getPatient(){
        return patient;
    }
    /**
     * Returns a boolean based upon whether the event has started or not
     * @return if the patient has started the assessment?
     */
    public boolean hasStarted() {
        return hasStarted;
    }
    /**
     * Returns a boolean based when the person would have started
     * @return Is the person in front is finished?
     */
    public boolean shouldStart(){
        return shouldStart;
    }
    /**
     * @param shouldStart sets the boolean value of it when called
     */
    public void setShouldStart(boolean shouldStart) {
        this.shouldStart = shouldStart;
    }
    /**
     * Returns the time when the patient has done assessment
     * @return Is the front of the line null?
     */
    public boolean shouldFinish(){
        return expectedEndingTime == getCurrentClockTime();
    }
}
