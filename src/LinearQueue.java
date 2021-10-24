/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Problem 1 Fall 2021
 *
 * Implements the line for walk-in patients to wait to be assessed for priority level, as well as the queue for
 * patients with priority 1 who are finished treatment and waiting to be admitted to the hospital
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
            PatientNode temp = head;
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
}