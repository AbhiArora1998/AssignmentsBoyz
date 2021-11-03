package queues;

import simulation.Patient;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Problem 1 Fall 2021
 *
 * Implements a place in line for a patient to wait. Each queues.PatientNode stores
 * the patient behind them while in a line.
 *
 * @author Noah Stobbe
 * Student Number: 230140171
 * @version 1
 */

public class Node<T> {
    private final T obj;
    private Node<T> next;

    public Node(T obj) {
        this.obj = obj;
    }

    public T getInfo() {
        return obj;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}