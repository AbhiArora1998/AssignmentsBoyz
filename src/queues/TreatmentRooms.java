package queues;

import events.StartTreatmentEvent;
import simulation.Patient;
import java.util.ArrayList;

import static simulation.Patient.HIGHEST_PRIORITY;

/**
 * This file is part of a solution to
 * CPSC300 Assignment 1 Problem 1 Fall 2021
 * <p>
 * This event occurs when people are waiting in the waiting room to get treated.
 * This treatmentroom class takes 3 people at a time in a treatment room and treats them.
 *
 * @author The BOYZ
 * Student Number: 230141945
 * @version 1
 */
public class TreatmentRooms {

    static private Room[] rooms;

    /**
     * This in class checks if the room is free initiates the start event
     */
    static class Room {
        public boolean isFree;
        public Patient patient;
        public StartTreatmentEvent treatmentEvent;

        /**
         * constructor justs calls the clear method
         */
        public Room() {
            clear();
        }

        /**
         * it is letting waiting queue know that the room is free and there is no event in the room
         */
        public void clear() {
            isFree = true;
            patient = null;
            treatmentEvent = null;
        }

        /**
         * it adds the patient for the startTreatment event
         * and gets the info of that patient object and sets the room to be engaged
         * @param treatmentEvent
         */
        public void addPatient(StartTreatmentEvent treatmentEvent) {
            this.treatmentEvent = treatmentEvent;
            this.patient = treatmentEvent.getPatient();
            isFree = false;
        }
    }


    /**
     * This constructor sets all the room to be free with no patient when initialised
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

    /**
     * it checks if the room is busy or not
     * @return
     */
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

    /**
     * It release the patient from the treatmentRoom
     * and the sets the room to be free and removes the patient from the room
     * @param patient
     * @return If a room is cleared successfully (a room contained the corresponding patient
     *         when this method was called)
     */
    public boolean releasePatient(Patient patient) {

        for (Room room : rooms) {
            if (patient == room.patient) {
                room.clear();
                return true;
            }
        }
        return false;
    }

    /**
     * Here the start treatment occurs filling all the rooms with a patient
     * @return startTreatmentEvent sends the patient that are in the room
     */
    public StartTreatmentEvent[] getTreatmentEvents() {
        StartTreatmentEvent[] treatmentEvents = new StartTreatmentEvent[rooms.length];
        for (int i = 0; i < rooms.length; i++) {
            treatmentEvents[i] = rooms[i].treatmentEvent;
        }
        return treatmentEvents;
    }

    /**
     * treats the patients that have high priority
     * by immediately sending them to room once the room is available
     * @return An ArrayList of patients of priority 1 who are ready to leave their treatment rooms
     */
    public ArrayList<Patient> getDonePriority1Patients() {
        ArrayList<Patient> patients = new ArrayList<>();
        for (Room room : rooms) {
            if (room.patient != null && room.patient.getPriority() == HIGHEST_PRIORITY && room.treatmentEvent.isDone()) {
                patients.add(room.patient);
            }
        }
        return patients;
    }

    /**
     * set all the rooms to be free if patient has been treated.
     * @return Integer count of rooms available
     */
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
