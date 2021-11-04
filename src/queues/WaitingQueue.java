package queues;

import events.Event;
import simulation.Patient;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Problem 1 Fall 2021
 *
 * Implements the line for patients to wait to be treated in order of priority level
 *
 * @author Noah Stobbe
 * Student Number: 230140171
 * @version 1
 */

public class WaitingQueue {
    // The Patient who will be chosen next
    Node<Event> head;

    public WaitingQueue() {}

    /**
     * Adds a event to the waiting line based upon their priority. If others have the same priority,
     * patientNumber is the determining factor for the order those Patients are lined up in
     * @param event The event to join the line
     * @return none
     */
    public void add(Event event) {
        Node<Event> newPatientNode = new Node<>(event);
        // If there is no line
        if (head == null) {
            head = newPatientNode;
        }
        // If the head has losing priority
        else if (head.getInfo().getPatient().getPriority() > newPatientNode.getInfo().getPatient().getPriority()) {
            newPatientNode.setNext(head);
            head = newPatientNode;
        }
        // If the head has the same priority and the head has been in line for less time
        else if ((head.getInfo().getPatient().getPriority() == newPatientNode.getInfo().getPatient().getPriority()
                && (head.getInfo().getPatient().getPatientNumber() > newPatientNode.getInfo().getPatient().getPatientNumber()))) {
            newPatientNode.setNext(head);
            head = newPatientNode;
        }
        // If the head has winning priority or event number and is the only event in line
        else if(head.getNext() == null) {
            head.setNext(newPatientNode);
        }
        // If we need to trace further down the line until we find a priority that loses
        else {
            Node<Event> temp = head;
            // While the next event in line has winning priority, or has the same priority but has waited longer
            while(temp.getNext().getInfo().getPatient().getPriority() < newPatientNode.getInfo().getPatient().getPriority()
                    || (temp.getNext().getInfo().getPatient().getPriority() == newPatientNode.getInfo().getPatient().getPriority()
                    && temp.getNext().getInfo().getPatient().getPatientNumber() < newPatientNode.getInfo().getPatient().getPatientNumber())) {
                temp = temp.getNext();
                // If we reach the end of the line (because all other patients have more priority)
                if (temp.getNext() == null) {
                    temp.setNext(newPatientNode);
                    return;
                }
            }
            // At this point, we know that the spot for the new event is between temp and temp.getNext()
            newPatientNode.setNext(temp.getNext());
            temp.setNext(newPatientNode);
        }
    }

    /**
     * Removes the patient at the front of the waiting line and returns the patient. If there exists no such line,
     * returns null.
     * @return head The current head attribute of queues.WaitingQueue at the time this method is called
     */
    public Event remove() {
        if (head == null) {
            return null;
        } else if (head.getNext() == null) {
            // Store the person at the front of the line separately as they are leaving the line
            Node<Event> temp = head;
            // Empty the line
            head = null;
            return temp.getInfo();
        } else {
            // Store the person at the front of the line separately as they are leaving the line
            Node<Event> temp = head;
            // Second person in line becomes first
            head = head.getNext();
            return temp.getInfo();
        }
    }

    /**
     * Returns a summary of all the patients in the Waiting Room.
     * @return string The attributes of each patient in the Waiting Room
     */
    @Override
    public String toString() {
        if(head != null) {
            Node<Event> tmp = head;
            String string = "Waiting Queue (for treatment): \n ";
            string += tmp.getInfo().getPatient().toString();
            while (tmp.getNext() != null) {
                string += "\n  ";
                string += tmp.getNext().getInfo().getPatient().toString();
                tmp = tmp.getNext();
            }
            return string;
        }
        return null;
    }

    /**
     * Returns a boolean based upon whether the waiting line is missing its front Patient or not
     * @return Is the front of the line null?
     */
    public boolean isEmpty(){
        return head == null;
    }

    public int size(){
        int count = 0;
        Node<Event> tmp = head;
        while(tmp != null){
            tmp = tmp.getNext();
            count++;
        }
        return count;
    }

    public Patient getPatient(int i){
        Node<Event> tmp = head;
        while(i > 0){
            tmp = tmp.getNext();
            i--;
        }
        return tmp.getInfo().getPatient();
    }

    public Event getEvent(int i){
        Node<Event> tmp = head;
        while(i > 0){
            tmp = tmp.getNext();
            i--;
        }
        return tmp.getInfo();
    }

}