package events;

import simulation.Patient;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Simulation.treatmentRooms;
import static simulation.Simulation.waitingQueue;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Fall 2021
 *
 * Triggers once treatment of a patient is completed. If the patient has priority 2-5, the patient departs immediately
 *
 * @author The Boyz
 * @version 1
 */

public class TreatmentCompletedEvent extends Event {

    public TreatmentCompletedEvent(Patient patient) {
        super();
        this.patient = patient;

        // This Event has no duration as it is merely bridging
        processingTime = 0;
    }

    /**
     * Starts TreatmentCompletedEvent, prints a notification of treatment completion, and allows
     * patients with 2-5 priority to depart immediately
     */
    @Override
    public void start(){
        super.start();
        patient.setCurrentEvent(this);
        System.out.println(this);

        // Gives patients with priority 2-5 the DepartureEvent
        if(patient.getPriority() != Patient.HIGHEST_PRIORITY){
            patient.setCurrentEvent(new DepartureEvent(patient));
            patient.getCurrentEvent().setShouldStart(true);
        }

        shouldStart = false;
    }

    /**
     * Ends the TreatmentCompletedEvent
     */
    @Override
    public void finish() {
        patient.setCurrentEvent(null);
        isDone = true;
        System.out.println(this);
    }

    /**
     * Returns a String reporting the current time, patient number and priority, and what the patient is undergoing
     * @return The current time, patient number and priority, and what the patient is undergoing
     */
    @Override
    public String toString() {
        if(isDone()){
            patient.setFinishTreatmentTime(getCurrentClockTime());
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") finishes treatment";
        } else if (hasStarted && startTime == getCurrentClockTime()){
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") starts treatment"
                    + " (waited " + (getCurrentClockTime()- patient.getAssessmentCompletedTime())//(getCurrentClockTime() - patient.getAssessmentTime()) + " START TREATMENT EVENT assessetime " + patient.getAssessmentTime()
                    + ", "+ treatmentRooms.roomsAvailable() + " rm(s) remain)";
        } else if (hasStarted){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " is STILL being treated (time remaining: " + (processingTime - (getCurrentClockTime() - startTime) + ").");
        } else {
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") is WAITING for treatment"
                    + " (waited " + patient.getWaitingTime()
                    + ", "+ treatmentRooms.roomsAvailable() + " rm(s) remain)";
        }
    }
}
