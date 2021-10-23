/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Problem 1 Fall 2021
 *
 * Implements a place in line for a patient to wait. Each PatientNode stores
 * the patient behind them while in a line.
 *
 * @author Noah Stobbe
 * Student Number: 230140171
 * @version 1
 */

public class PatientNode {
    private Patient patient;
    private PatientNode next;

    public PatientNode(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public PatientNode getNext() {
        return next;
    }

    public void setNext(PatientNode next) {
        this.next = next;
    }
}