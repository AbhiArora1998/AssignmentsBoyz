package events;

import simulation.Patient;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Simulation.*;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Fall 2021
 *
 * This class specifically works with the treatment room
 * it checks if the rooms are available
 * It lets patient enter the treatment room
 *
 * @author The Boyz
 * @version 1
 */

public class AssessmentEvent extends Event {

    private static final int ASSESSMENT_PROCESSING_TIME = 4;
    private static AssessmentEvent currentEvent;

    public AssessmentEvent(Patient patient) {
        super();
        processingTime = ASSESSMENT_PROCESSING_TIME;
        this.patient = patient;

    }

    /**
     * Starts AssessmentEvent, records the time the patient gets assessed, and prints a report of assessment
     */
    @Override
    public void start(){
        if(currentEvent == null){
            super.start();

            // Records the time at which patient is assessed
            patient.setAssessmentTime(getCurrentClockTime());

            currentEvent = this;
            System.out.println(this);
            patient.setCurrentEvent(this);
        }
            shouldStart = false;
    }

    /**
     * Removes the patient from the assessment line and adds them to the waiting room, and prints a report that
     * the patient has been assessed.
     */
    @Override
    public void finish() {
        currentEvent = null;

        // Adds patient to waiting room and removes them from the assessment line
        waitingQueue.add(new StartTreatmentEvent(patient));
        assessmentQueue.remove();

        patient.setCurrentEvent(null);
        isDone = true;
        System.out.println(this);

        // Prints out report that patient is entering the waiting room
        System.out.println("Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber() + " (Priority " + patient.getPriority() + ") enters waiting room");
    }

    /**
     * Returns a String reporting the current time, patient number and what the patient is undergoing. If done
     * assessment, returns the priority of the patient.
     * @return The current time, patient number and priority, and what the patient is undergoing
     */
    @Override
    public String toString() {

        if(isDone()){
            patient.setAssessmentCompletedTime(getCurrentClockTime());
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " assessment completed (Priority now " + patient.getPriority() +")";
        } else if (hasStarted && startTime == getCurrentClockTime()){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " starts assessment (waited " + patient.getWaitingTime() +")";
        } else if (hasStarted){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " is STILL being assessed (time remaining: " + (processingTime - (getCurrentClockTime() - startTime) + ").");
        } else {
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") is WAITING to be assessed"
                    + " (waited " + patient.getWaitingTime() + ")";
        }
    }
}