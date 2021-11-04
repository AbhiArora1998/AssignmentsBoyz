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

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Fall 2021
 *
 * patient class provides the patientNumber, the priority and the treatment time it require according to the patient
 * it then stores the person into the ArrayList
 *
 * @author The Boyz
 * @version 1
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
    private int assessmentCompletedTime; //this is when the assessment is completed used for priority 2-5 patients to calculate waiting time for the treatment room
    private int finishTreatmentTime; //this is when the treatment is completed used for priority 1 patients to calculate waiting time for admitted to hospital
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

        //If the patient arrived in an emergency, makes them top priority
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
            priority = HIGHEST_PRIORITY;
        } else {
            this.priority = random.nextInt(5)+1;
        }
        patients.add(this);
    }
    /**
     * returns an int value which is the patient number
     * @return patient number
     */

    public int getPatientNumber() {
        return patientNumber;
    }
    /**
     * returns if the patient is of type emergency or not
     * @return returns the char E or W
     */

    public char getType() {
        return type;
    }
    /**
     *
     * @param type set the type of the patient
     */

    public void setType(char type) {
        this.type = type;
    }
    /**
     * returns an int value which is the priority of the patient
     * @return priority of the patient which is 1 to 5
     */

    public int getPriority() {
        return priority;
    }
    /**
     * returns an int value which the time patient arrived
     * @return patient arrival
     */

    public int getArrivalTime() {
        return arrivalTime;
    }
    /**
     *
     * @param arrivalTime gets us the time patient arrived at the hospital?
     */


    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    /**
     * provides the treatment time of the patient
     * @return treatment time of the current patient
     */

    public int getTreatmentTime() {
        return treatmentTime;
    }
    /**
     * This toString prints all the deails about the patient
     */

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
    /**
     * returns the time the patient has to wait before the treatment has started
     * @return waiting time of the current patient
     */

    public void increaseWaitingTime(){
        waitingTime++;
    }
    /**
     * returns an int value which is the patient number
     * @return patient number?
     */

    public int getWaitingTime() {
        return waitingTime;
    }
    /**
     * returns the time the patient get assessed
     * @return assessment time of the patient
     */

    public int getAssessmentTime() {
        return assessmentTime;
    }

    /**
     *
     * @param assessmentTime  sets the assessment time of the patient
     */

    public void setAssessmentTime(int assessmentTime) {
        this.assessmentTime = assessmentTime;
    }

    /**
     *
     * @return returns the assessment completed time of the current patient ?
     */
    public int getAssessmentCompletedTime(){ return assessmentCompletedTime;}

    /**
     *
     * @param assessmentCompleted  sets the patient completed time when the patient is done?
     */
    public void setAssessmentCompletedTime(int assessmentCompleted) {this.assessmentCompletedTime = assessmentCompleted;}

    /**
     * @return time the patient is done with the treatment room?
     */
    public int getFinishTreatmentTime(){ return finishTreatmentTime;}

    /**
     *
     * @param finishTreatmentTime  sets the time when the patinet is done with the treatment
     */
    public void setFinishTreatmentTime(int finishTreatmentTime) {this.finishTreatmentTime = finishTreatmentTime;}

    /**
     *
     * @return the time when the patient left the hospital
     */
    public int getDepartureTime(){return departureTime;}

    /**@param time sets the departure time of the current patient
     */
    public void setDepartureTime(int time){departureTime = time;}

    /**
     *
     * @return what event patient  is at
     */
    public Event getCurrentEvent() {
        return currentEvent;
    }

    /**
     * @param currentEvent sets the current event of the patient
     */
    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }
}
