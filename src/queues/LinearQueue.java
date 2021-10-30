package queues;

import simulation.Patient;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Problem 1 Fall 2021
 *
 * Implements the line for walk-in patients to wait to be assessed for priority level
 *
 * @author Noah Stobbe
 * Student Number: 230140171
 * @version 1
 */

public class LinearQueue {
    private PatientNode head;

    public LinearQueue() {}

    /**
     * Adds a patient to the back of the assessment line
     * @param patient The patient to join the line
     * @return none
     */
    public void add(Patient patient) {
        if (head == null) {
            head = new PatientNode(patient);
        } else {
            PatientNode temp = head;
            // Set temp to the last patient in the assessment line
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            // Put new patient after last patient in line for assessment
            temp.setNext(new PatientNode(patient));
        }
        PatientNode newNode = new PatientNode(patient);
    }

    /**
     * Removes the patient at the front of the assessment line and returns the patient. If there exists no such line,
     * returns null.
     * @return head The current head attribute of AssessmentQueue at the time this method is called
     */
    public Patient remove() {
        if (head == null) {
            return null;
        } else if (head.getNext() == null) {
            // Store the person at the front of the line separately as they are leaving the line
            PatientNode temp = head;
            // Empty the line
            head = null;
            return temp.getPatient();
        } else {
            // Store the person at the front of the line separately as they are leaving the line
            PatientNode temp = head;
            // Second person in line becomes first
            head = head.getNext();
            return temp.getPatient();
        }
    }

    /**
     * Returns a summary of all the walk-in patients waiting to be assessed for priority
     * @return string The attributes of each walk-in patient waiting for assessment
     */
    @Override
    public String toString() {
        if(head != null) {
            PatientNode tmp = head;
            String string = "Linear Queue (assessment): \n  ";
            string += tmp.getPatient().toString();
            while (tmp.getNext() != null) {
                string += "\n  ";
                string += tmp.getNext().getPatient().toString();
                tmp = tmp.getNext();
            }
            return string;
        }
        return null;
    }

    /**
     * Returns a boolean based upon whether the assessment line is missing its front or not
     * @return Is the front of the line null?
     */
    public boolean isEmpty(){
        return head == null;
    }

    /**
     * Returns the Patient at the front of the assessment line
     * @return head The Patient at the front of the assessment line
     */
    public PatientNode getHead() {
        return head;
    }

    /**
     * Returns the number of patients waiting to be assessed
     * @return Size of the LinearQueue
     */
    public int size(){
        int count = 0;
        PatientNode tmp = head;
        while(tmp != null) {
            tmp = tmp.getNext();
            count++;
        }
        return count;
    }

    /**
     * Returns the Patient i Patients behind the Patient at the front of the assessment line. If i = 0, returns the
     * Patient at the front of the line.
     * @param i The number of steps in line behind the front Patient
     * @return The patient at the ith step from the front. Returns null if there is no such Patient
     */
    public Patient get(int i) {
        PatientNode tmp = head;
        while(i > 0){
            tmp = tmp.getNext();
            i--;
        }
        return tmp.getPatient();
    }
}