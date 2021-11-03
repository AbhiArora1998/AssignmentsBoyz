/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Fall 2021
 *
 * This class acts as a patient, keeping track of their patient number, priority, their type (Walk-in or Emergency).
 *
 * @author The Boyz
 * @version 1
 */

package simulation;

import events.Event;

import java.util.ArrayList;
import java.util.Random;
/*
patient class provides the patientNumber, the priority and the treatment time it require according to the patient
it then stores the person into the ArrayList
 */
public class Patient {

    public static final char CODE_W = 'W', CODE_E = 'E';
    public static final int HIGHEST_PRIORITY = 1;

    private static int nextPatientNumber = 28064212;
    public static ArrayList<Patient> patients = new ArrayList<>();

    private final int patientNumber;
    private char type; //Type of patient (Walk-in or Emergency)
    private int priority = 0;
    private int treatmentTime;
    private int arrivalTime; //When did the patient arrived
    private int waitingTime;
    private int assessmentTime;
    private int admittingHospitalTime;
    private int departureTime;
    private Event currentEvent;
    private static final Random random = new Random(1000);

    /**
     * Constructor. Per assignment instructions, priority is randomised,
     * however this is to be handled wherever the patient is created.
     * @param type Type of patient (Walk-in [w] or Emergency [E])
     * @param treatmentTime How long will the treatment for this patient take
     * @param priority How urgent it is to treat the patient
     */
    public Patient(int arrivalTime, char type, int treatmentTime,  int priority) {
        this.arrivalTime = arrivalTime;
        this.type = type;
        this.patientNumber = nextPatientNumber++;
        this.treatmentTime = treatmentTime;
        waitingTime = 0;

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

    public void setType(char type) {
        this.type = type;
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

    public int getTreatmentTime() {
        return treatmentTime;
    }

    public void setTreatmentTime(int treatmentTime) {
        this.treatmentTime = treatmentTime;
    }

    @Override
    public String toString() {
        return "simulation.Patient{" +
                "patientNumber=" + patientNumber +
                ", type=" + type +
                ", priority=" + priority +
                ", treatmentTime=" + treatmentTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    public void increaseWaitingTime(){
        waitingTime++;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getAssessmentTime() {
        return assessmentTime;
    }

    public void setAssessmentTime(int assessmentTime) {
        this.assessmentTime = assessmentTime;
    }

    public void setAdmittingToHospitalTime(int assessmentTime) {
        this.admittingHospitalTime = assessmentTime;
    }

    public int getDepartureTime(){return departureTime;}
    public void setDepartureTime(int time){departureTime = time;}

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }
}
