package events;

import simulation.Patient;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Patient.HIGHEST_PRIORITY;
import static simulation.Simulation.treatmentRooms;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Fall 2021
 *
 * This event occurs when patients depart from the hospital
 *
 * @author The Boyz
 * @version 1
 */

public class DepartureEvent extends Event {

    private static final int DEPARTURE_PROCESSING_TIME_OTHER_PRIORITIES = 1;
    private static final int DEPARTURE_PROCESSING_TIME_PRIORITY1 = 0;

    public DepartureEvent(Patient patient) {
        super();
        if(patient.getPriority() == HIGHEST_PRIORITY){
            processingTime = DEPARTURE_PROCESSING_TIME_PRIORITY1;
        } else {
            processingTime = DEPARTURE_PROCESSING_TIME_OTHER_PRIORITIES;

        }
        this.patient = patient;
    }

    /**
     * Starts DepartureEvent
     */
    @Override
    public void start() {
        super.start();
        shouldStart = false;
    }

    /**
     * Ejects patient from their treatment room, records their departure time, and prints a report
     */
    @Override
    public void finish() {
        isDone = true;
        patient.setCurrentEvent(null);

        // Ejects patient from their treatment room and records the time
        treatmentRooms.releasePatient(patient);
        patient.setDepartureTime(getCurrentClockTime());

        System.out.println(this);
    }

    /**
     * Returns a String reporting the current time, patient number and priority,
     * and the number of available treatment rooms
     * @return The current time, patient number and priority, and the number of available treatment rooms
     */
    @Override
    public String toString() {
            return "Time " + getCurrentClockTime()+ ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() +") departs, " + treatmentRooms.roomsAvailable()+ " rm(s) remain";
    }
}
