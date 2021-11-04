package queues;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Problem 1 Fall 2021
 *
 * Implements a place in line for a patient to wait. Each queues.PatientNode stores
 * the patient behind them while in a line.
 *
 * @author The Boyz
 * @version 1
 */
/*
This class has objects and the node that will help us point to the next one.
 */
public class Node<T> {
    private final T obj;
    private Node<T> next;
/*
Sets the nodes object to the current object that it will point to
 */
    public Node(T obj) {
        this.obj = obj;
    }

    public T getInfo() {
        return obj;
    }
    /**
     * return address that it has in the next (Node)
     * @return returns the object
     */
    public Node<T> getNext() {
        return next;
    }
/*
sets the value of next to the new node
@param sets the next node to new Patient
 */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}