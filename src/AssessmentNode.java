/**
 * This file is part of a solution to
 *		CPSC300 Assigment 1 Problem 1 Fall 2021
 *
 * Implements the places in line for walk-in patients to wait to be assessed for priority level. Each patient sees
 * the patient in front of them while in this line.
 *
 * @author Noah Stobbe
 * Student Number: 230140171
 * @version 1
 */

public class AssessmentNode {
    private Patient patient;
    private AssessmentNode next;

    public AssessmentNode(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public AssessmentNode getNext() {
        return next;
    }

    public void setNext(AssessmentNode next) {
        this.next = next;
    }
}
