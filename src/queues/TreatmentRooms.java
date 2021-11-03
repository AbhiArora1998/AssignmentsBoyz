package queues;

import events.StartTreatmentEvent;
import simulation.Patient;

import java.util.ArrayList;

import static simulation.Simulation.waitingQueue;

/**
 * This file is part of a solution to
 * CPSC300 Assignment 1 Problem 1 Fall 2021
 * <p>
 * This event  occurs when people are waiting in the waiting room to get treated.
 * This treatmentroom  class takes 3 people at a time  in a treatment room and treat them.
 *
 * @author The BOYZ
 * Student Number: 230141945
 * @version 1
 */
public class TreatmentRooms {

    static private Room[] rooms;


    static class Room {
        public boolean isFree;
        public Patient patient;
        public StartTreatmentEvent treatmentEvent;

        public Room() {
            clear();
        }

        public void clear() {
            isFree = true;
            patient = null;
            treatmentEvent = null;
        }

        public void addPatient(StartTreatmentEvent treatmentEvent) {
            this.treatmentEvent = treatmentEvent;
            this.patient = treatmentEvent.getPatient();
            isFree = false;
        }
    }


    /**
     * This constructor set all the room to be free with no patient when initialised
     */
    public TreatmentRooms(int numberOfTreatmentRooms) {
        rooms = new Room[numberOfTreatmentRooms];
        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = new Room();
        }
    }

    /**
     * This method is supposed to be run after checking availability with anyRoomAvailable().
     *
     * @return
     * @throws Exception
     */
    public int getRoomAvailable() {
        int roomNumber = -1;
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].isFree) {
                roomNumber = i;
                break;
            }
        }
        if (roomNumber == -1) {
            System.out.println("ERROR: No rooms available. Did you check availability with anyRoomAvailable first?");
        }
        return roomNumber;
    }

    /**
     * @return true if any room is available
     */
    public boolean anyRoomAvailable() {
        boolean available = false;
        for (Room room : rooms) {
            if (room.isFree) {
                available = true;
                break;
            }
        }
        return available;
    }

    public boolean anyRoomBeingUsed() {
        boolean used = false;
        for (Room room : rooms) {
            if (!room.isFree) {
                used = true;
                break;
            }
        }
        return used;
    }

    /**
     * Place a new patient into one of the treatment rooms.
     *
     * @param roomNumber
     * @param treatmentEvent
     * @return false if room was already filled. True if successful.
     */
    public boolean placePatient(int roomNumber, StartTreatmentEvent treatmentEvent) {
        if (rooms[roomNumber].isFree) {
            rooms[roomNumber].addPatient(treatmentEvent);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Adds a patient to the waiting line
     *
     * @return none
     */
    public boolean isTreatmentDone(int roomNumber) {
        return rooms[roomNumber].treatmentEvent.isDone();
    }

    /**
     * WORDS
     *
     * @param roomNumber room where the patient to release is
     * @return The patient just released. If the treatment is not done yet, return null.
     */
    public Patient releasePatient(int roomNumber) {
        if (rooms[roomNumber].treatmentEvent.isDone()) {
            Patient temp = rooms[roomNumber].patient;
            rooms[roomNumber].clear();
            return temp;
        } else {
            return null;
        }
    }

    public boolean releasePatient(Patient patient) {

        for (Room room : rooms) {
            if (patient == room.patient) {
                room.clear();
                return true;
            }
        }
        return false;
    }

    public StartTreatmentEvent[] getTreatmentEvents() {
        StartTreatmentEvent[] treatmentEvents = new StartTreatmentEvent[rooms.length];
        for (int i = 0; i < rooms.length; i++) {
            treatmentEvents[i] = rooms[i].treatmentEvent;
        }
        return treatmentEvents;
    }

    public ArrayList<Patient> getDonePriority1Patients() {
        ArrayList<Patient> patients = new ArrayList<>();
        for (Room room : rooms) {
            if (room.patient != null && room.patient.getPriority() == 1 && room.treatmentEvent.isDone()) {
                patients.add(room.patient);
            }
        }

        return patients;
    }

    public int roomsAvailable(){
        int count = 0;
        for(Room room: rooms) {
            if(room.isFree){
                count++;
            }
        }
        return count;
    }
}
