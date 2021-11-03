/**
 * This file is part of a solution to
 * CPSC300 Assignment 1 Fall 2021
 * <p>
 * This class simulates one clock cycle, which handles which events should trigger/get done, and which events to create
 * next, as well as, where should patients go when they arrive, are assessed and are treated.
 *
 * @author The Boyz
 * @version 1
 */

package simulation;

import events.*;
import queues.LinearQueue;
import queues.TreatmentRooms;
import queues.WaitingQueue;

import java.util.ArrayList;
import java.util.Comparator;

import static simulation.Clock.getCurrentClockTime;

public final class Simulation {

    public static final int NUMBER_OF_TREATMENT_ROOMS = 3;

    //TODO: change queues to hold events instead of patients? Maybe
    public static final LinearQueue<AssessmentEvent> assessmentQueue = new LinearQueue<>();
    public static final WaitingQueue waitingQueue = new WaitingQueue();
    public static TreatmentRooms treatmentRooms = new TreatmentRooms(NUMBER_OF_TREATMENT_ROOMS);

    private Simulation() {
        /* empty */
    }

    /**
     * Go through all events and progress their code forward.
     *
     * @param patientType    List of patients types that arrived at this clock-cycle extracted from a file.
     * @param processingTime List of times required for treatment for each of the patients above. Also from file.
     */
    public static void doOneClockCycle(ArrayList<Character> patientType, ArrayList<Integer> processingTime) {

        //Create new arrival events. Do not start them yet, as events happen in order based on patient number
        for (int i = 0; i < patientType.size(); i++) {
            ArrivalEvent arrivalEvent = new ArrivalEvent(patientType.get(i), processingTime.get(i));
            arrivalEvent.setShouldStart(true);
        }

        ArrayList<Event> currentClockCycleEvents = new ArrayList<>();
        boolean run = true;
        while(run){

            //Assessment Events
            if (!assessmentQueue.isEmpty()) {
                assessmentQueue.get(0).setShouldStart(true);

            }

            //StartTreatment Events
            if (treatmentRooms.anyRoomAvailable() && !waitingQueue.isEmpty()) {
                waitingQueue.getEvent(0).setShouldStart(true);
            }

            //AdmittingToHospital Event
            ArrayList<Patient> priority1Patients = treatmentRooms.getDonePriority1Patients();
            if (treatmentRooms.anyRoomBeingUsed()
                    && !priority1Patients.isEmpty()) {
                Patient temp = priority1Patients.get(0);
                if (priority1Patients.size() > 1) {//gets priority 1 patient that has waited the longest
                    for (Patient patient : priority1Patients) {
                        if (patient.getWaitingTime() > temp.getWaitingTime()) {
                            temp = patient;
                        }
                    }
                }
                if(temp.getCurrentEvent()==null){
                    temp.setCurrentEvent(new AdmittingToHospitalEvent(temp));
                    temp.getCurrentEvent().setShouldStart(true);
                } else {
                    temp.getCurrentEvent().setShouldStart(true);
                }
            }


            for(Event event: Event.events){
                if(((event.shouldFinish() && !event.isDone()) || (event.shouldStart() && !event.hasStarted())) && !currentClockCycleEvents.contains(event)){
                    currentClockCycleEvents.add(event);
                }
            }

            if(!currentClockCycleEvents.isEmpty()){
                currentClockCycleEvents.sort(Comparator.comparing(l->l.getPatient().getPatientNumber()));
                Event currentEvent = currentClockCycleEvents.get(0);
                if(currentEvent.shouldFinish()) {
                    currentEvent.finish();
                } else {
                    currentEvent.start();
                }
                currentClockCycleEvents.remove(currentEvent);
            } else {
                run = false;
            }
        }

        increaseWaitingTimes();
    }


    private static void increaseWaitingTimes() {
        //Increase waiting time for everyone that is treated and waiting to be admitted. (i = 0 is currently being assessed so skip it)
        for (int i = 1; i < assessmentQueue.size(); i++) {
            assessmentQueue.get(i).getPatient().increaseWaitingTime();
        }
        //Increase waiting time for everyone that is not being assessed. (i = 0 is currently being assessed so skip it)
        for (int i = 0; i < waitingQueue.size(); i++) {
            waitingQueue.getPatient(i).increaseWaitingTime();
        }

        //this increases wait time of priority 1 patients that are in the treatment room and are not addmitted
        for (Patient patient : treatmentRooms.getDonePriority1Patients()) {
            if (patient.getCurrentEvent() == null) {
                patient.increaseWaitingTime();
            }
        }
    }



}
