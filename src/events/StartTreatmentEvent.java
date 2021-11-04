package events;

import simulation.Patient;
import simulation.Simulation;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Simulation.treatmentRooms;
import static simulation.Simulation.waitingQueue;

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

public class StartTreatmentEvent extends Event {

    public StartTreatmentEvent(Patient patient) {
        super();
        this.patient = patient;
        processingTime = patient.getTreatmentTime();
    }

    /**
     * If there is a treatment room available at this time, this event starts, the patient is put into a treatment room,
     * the patient is removed from the waiting room, and a report of this event is printed.
     */
    @Override
    public void start(){
        if(treatmentRooms.anyRoomAvailable()){
            super.start();

            // Add patient to available treatment room
            treatmentRooms.placePatient(treatmentRooms.getRoomAvailable(), this);

            patient.setCurrentEvent(this);

            // Remove patient from waiting room
            waitingQueue.remove();

            System.out.println(this);
        }
        shouldStart = false;
    }

    /**
     * Initialize a TreatmentCompletedEvent and start it
     */
    @Override
    public void finish() {
        patient.setCurrentEvent(null);
        isDone = true;

        TreatmentCompletedEvent treatmentCompletedEvent = new TreatmentCompletedEvent(patient);
        treatmentCompletedEvent.setShouldStart(true);
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
