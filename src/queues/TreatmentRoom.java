package queues;

import simulation.Patient;
/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Problem 1 Fall 2021
 *
 * This event  occurs when people are waiting in the waiting room to get treated.
 * This treatmentroom  class takes 3 people at a time  in a treatment room and treat them.
 *
 * @author Abhishek Arora
 * Student Number: 230141945
 * @version 1
 */
public class TreatmentRoom {
    private Patient[] patients = new Patient[3];
    WaitingQueue waitingQueue;

    private boolean roomIsFree1;
    private boolean roomIsFree2;
    private boolean roomIsFree3;
    private boolean patient1IsDone;
    private boolean patient2IsDone;
    private boolean patient3IsDone;
    private boolean[] patientIsDone = {false,false,false};


    /**
     * This constructor set all the room to be free with no patient when initialised
     */
    public TreatmentRoom() {

        this.roomIsFree1 = true;
        this.roomIsFree2 = true;
        this.roomIsFree3 = true;

        this.patient1IsDone = true;
        this.patient2IsDone = true;
        this.patient3IsDone = true;

    }
    /**
     * this method allow patient to enter the treatment room
     * it checks if the rooms are empty
     * if they are then it fills the patient in the room and mark that room as busy
     * @param patient The patient to enter the treatment room
     * @return none
     */
    public void enterTreatmentRoom(Patient patient) {
        if (roomIsFree1) {
            patients[0] = patient;
            patient1IsDone = false;


        } else if (roomIsFree2) {

            patients[1] = patient;
            patient2IsDone = false;
        } else if (roomIsFree3) {
            patients[2] = patient;
            patient3IsDone = false;
        } else {
            System.out.println("The rooms are busy please wait");
        }
    }
    /**
     * this method checks if the there is any patient already in the room
     * before another person can leave the waiting room
     *
     * @return int value to tell which room is free
     */
    public int roomCheck() {
        if (roomIsFree1) {

            return 1;
        } else if (roomIsFree2) {

            return 2;

        } else if (roomIsFree3) {

            return 3;
        } else {
            System.out.println("The rooms are busy please wait");
        }
        return 0;
    }

    public void patientTreated(Patient[] patients) {

    }

    public void setPatient1IsDone(boolean patient1IsDone) {
        this.patient1IsDone = patient1IsDone;
    }

    public void setPatient2IsDone(boolean patient2IsDone) {
        this.patient2IsDone = patient2IsDone;
    }

    public void setPatient3IsDone(boolean patient3IsDone) {
        this.patient3IsDone = patient3IsDone;
    }

    public void setPatientIsDone(int patientNum, boolean patient3IsDone) {
        this.patientIsDone[patientNum] = patientIsDone[patientNum];
    }

    /**
     * Adds a patient to the waiting line
     *
     * @return none
     */
    public boolean getPatientIsDone(int patientNum) {
        return patientIsDone[patientNum];
    }

    public Patient setRoomIsFree1() {
        Patient temp = patients[0];
        patients[0] = null;
        this.roomIsFree1 = true;
        return temp;
    }

    public Patient setRoomIsFree2() {
        Patient temp = patients[1];
        patients[1] = null;
        this.roomIsFree2 = true;
        return temp;
    }

    public Patient setRoomIsFree3() {
        Patient temp = patients[2];
        patients[2] = null;
        this.roomIsFree3 = true;
        return temp;
    }
    public Patient setRoomIsFree(int room) {
        Patient temp = patients[room];
        patients[room] = null;
        this.roomIsFree3 = true;
        return temp;
    }

    public Patient[] getPatients() {
        return patients;
    }

    public void waitingQueueRemove() {
        waitingQueue.remove();
    }
}
