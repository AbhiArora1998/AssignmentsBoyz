/**
 * This file is part of a solution to
 *		CPSC300 Assigment 1 Problem 1 Fall 2021
 *
 * Implements the line for patients to wait to be treated in order of priority level
 *
 * @author Noah Stobbe
 * Student Number: 230140171
 * @version 1
 */

public class WaitingQueue {
    PatientNode head;

    public WaitingQueue() {}

    /**
     * Adds a patient to the waiting line
     * @param patient The patient to join the line
     * @return none
     */
    public void add(Patient patient) {
        PatientNode newPatientNode = new PatientNode(patient);
        // If there is no line
        if (head == null) {
            head = newPatientNode;
        }
        // If the head has losing priority
        else if (head.getPatient().getPriority() > newPatientNode.getPatient().getPriority()) {
            newPatientNode.setNext(head);
            head = newPatientNode;
        }
        // If the head has the same priority
        else if (head.getPatient().getPriority() == newPatientNode.getPatient().getPriority()) {
            // If the head has been in line for less time
            if (head.getPatient().getPatientNumber() > newPatientNode.getPatient().getPatientNumber()) {
                newPatientNode.setNext(head);
                head = newPatientNode;
            }
        }
        // If the head has winning priority or patient number and is the only patient in line
        else if(head.getNext() == null) {
            head.setNext(newPatientNode);
        }
        // If we need to trace further down the line until we find a priority that loses
        else {
            PatientNode temp = head;
            while((temp.getNext().getPatient().getPriority() <= newPatientNode.getPatient().getPriority())
                || (temp.getNext().getPatient().getPatientNumber() < newPatientNode.getPatient().getPatientNumber())) {
                temp = temp.getNext();
                // If we reach the end of the line (because all other patients have more priority)
                if (temp.getNext() == null) {
                    temp.setNext(newPatientNode);
                    return;
                }
            }

            // At this point, we know that the spot for the new patient is between temp and temp.getNext()
            newPatientNode.setNext(temp.getNext());
            temp.setNext(newPatientNode);
        }
    }

    /**
     * Removes the patient at the front of the waiting line and returns the patient. If there exists no such line,
     * returns null.
     * @return head The current head attribute of WaitingQueue at the time this method is called
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
