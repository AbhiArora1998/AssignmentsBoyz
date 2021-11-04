package events;

import simulation.Patient;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Simulation.treatmentRooms;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Problem 1 Fall 2021
 *
 * This event occurs when patients depart from the hospital
 *
 * @author Sebastian Lopez
 * Student Number: 230141246
 * @version 1
 */

public class DepartureEvent extends Event {

    private static final int DEPARTURE_PROCESSING_TIME_OTHER_PRIORITIES = 1;
    private static final int DEPARTURE_PROCESSING_TIME_PRIORITY1 = 0;

    public DepartureEvent(Patient patient) {
        super();
        if(patient.getPriority() == 1){
            processingTime = DEPARTURE_PROCESSING_TIME_PRIORITY1;
        } else {
            processingTime = DEPARTURE_PROCESSING_TIME_OTHER_PRIORITIES;

        }
        this.patient = patient;
    }

    @Override
    public void start() {
        super.start();
        shouldStart = false;
    }

    @Override
    public void finish() {
        isDone = true;
        patient.setCurrentEvent(null);
        treatmentRooms.releasePatient(patient);
        patient.setDepartureTime(getCurrentClockTime());
        System.out.println(this);
    }

    @Override
    public String toString() {
            return "Time " + getCurrentClockTime()+ ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() +") departs, " + treatmentRooms.roomsAvailable()+ " rm(s) remain";
    }
}
