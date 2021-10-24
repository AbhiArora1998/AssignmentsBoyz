package events;

import simulation.Patient;

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

    private static final int DEPARTURE_PROCESSING_TIME = 1;

    public DepartureEvent(Patient patient) {
        super();
        processingTime = DEPARTURE_PROCESSING_TIME;
    }

    @Override
    public void start() {
        super.start();
    }
}
