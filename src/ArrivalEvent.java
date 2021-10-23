/**
 * This file is part of a solution to
 *		CPSC300 Assigment 1 Problem 1 Fall 2021
 *
 * This event occurs when patients first arrive at the hospital
 *
 * @author Sebastian Lopez
 * Student Number: 230140171
 * @version 1
 */

import java.util.Random;

public class ArrivalEvent extends Event{

    private final char patientType;
    private final int treatmentTime;
    private LinearQueue linearQueue;
    private final Random random = new Random(1000);

    public ArrivalEvent(char patientType, int treatmentTime) {
        super();
        this.patientType = patientType;
        this.treatmentTime = treatmentTime;

        start();
    }

    public ArrivalEvent(char patientType, int treatmentTime, int priority) {
        super();
        this.patientType = patientType;
        this.treatmentTime = treatmentTime;

        start(priority);
    }

    /**
     * Create a new Patient
     * @param priority The severity of the patient's symptoms, used to determine which patients
     *                 are to be treated earliest
     */
    public void start(int priority){
        super.start();
        new Patient(creationTime, patientType, treatmentTime, priority);
    }

    @Override
    public void start() {
        super.start();
        new Patient(creationTime, patientType, treatmentTime);
    }
}
