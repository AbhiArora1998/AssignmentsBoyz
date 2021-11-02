package events;

import simulation.Patient;
import simulation.Simulation;

import static simulation.Clock.getCurrentClockTime;

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
    private Patient patient;

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
    }

    @Override
    public String toString() {
            return "Time " + getCurrentClockTime()+ ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() +") departs, " + Simulation.treatmentRooms.roomsAvailable()+ " rm(s) remain";
    }
    public Patient getPatient() {
        return patient;
    }
}
