/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Fall 2021
 *
 * This class acts as a patient, keeping track of their patient number, priority, their type (Walk-in or Emergency).
 *
 * @author The Boyz
 * @version 1
 */


import java.util.ArrayList;
import java.util.Random;
/*
patient class provides the patientNumber, the priority and the treatment time it require according to the patient
it then stores the person into the ArrayList
 */
public class Patient {

    public static final char CODE_MISSING = 0, CODE_W = 'W', CODE_E = 'E';
    public static final int PRIORITY_MISSING = 0;
    public static final int HIGHEST_PRIORITY = 1;

    private static int nextPatientNumber = 28064212;
    public static ArrayList<Patient> patients = new ArrayList<>();

    private int patientNumber = 0;
    private char type = 0; //Type of patient (Walk-in or Emergency)
    private int priority = 0;
    private int treatmentTime = 0;
    private int arrivalTime = 0; //When did the patient arrived
    private static Random random = new Random(1000);

    /**
     * Constructor. Per assignment instructions, priority is randomised,
     * however this is to be handled wherever the patient is created.
     * @param type Type of patient (Walk-in [w] or Emergency [E])
     * @param treatmentTime How long will the treatment for this patient take
     * @param priority How urgent it is to treat the patient
     */
    public Patient(char type, int treatmentTime, int arrivalTime, int priority) {
        this.arrivalTime = arrivalTime;
        this.type = type;
        this.patientNumber = nextPatientNumber++;
        this.treatmentTime = treatmentTime;

        //If the patient arrived in an emergency, make them top priority
        if(type == CODE_E){
            priority = HIGHEST_PRIORITY;
        } else {
            this.priority = priority;
        }

        patients.add(this);
    }

    /**
     * Same constructor as above, but with priority randomised.
     * @param type Type of patient (Walk-in [w] or Emergency [E])
     * @param treatmentTime How long will the treatment for this patient take
     */
    public Patient( int arrivalTime, char type, int treatmentTime) {
        this.arrivalTime = arrivalTime;
        this.type = type;
        this.patientNumber = nextPatientNumber++;
        this.treatmentTime = treatmentTime;

        //If the patient arrived in an emergency, make them top priority
        if(type == CODE_E){
            priority = 1;
        } else {
            this.priority = random.nextInt(5)+1;
        }

        patients.add(this);
    }

    public int getPatientNumber() {
        return patientNumber;
    }

    public char getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
