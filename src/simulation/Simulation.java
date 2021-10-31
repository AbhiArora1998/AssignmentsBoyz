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

import events.AdmittingToHospitalEvent;
import events.ArrivalEvent;
import events.AssessmentEvent;
import events.DepartureEvent;
import events.StartTreatmentEvent;
import queues.LinearQueue;
import queues.TreatmentRooms;
import queues.WaitingQueue;

import java.util.ArrayList;

import static simulation.Clock.getCurrentClockTime;

public final class Simulation {

    public static final int NUMBER_OF_TREATMENT_ROOMS = 3;

    //TODO: change queues to hold events instead of patients? Maybe
    public static final LinearQueue assessmentQueue = new LinearQueue();
    public static final WaitingQueue waitingQueue = new WaitingQueue();
    public static TreatmentRooms treatmentRooms = new TreatmentRooms(NUMBER_OF_TREATMENT_ROOMS);

    //TODO: currentEvents class
    private static AssessmentEvent currentAssessmentEvent;
    private static AdmittingToHospitalEvent currentAdmittingToHospitalEvent;
    private static ArrayList<DepartureEvent> currentDepartureEvents = new ArrayList<>();
    private static boolean admissionNurseAvailable = true;
    private static StartTreatmentEvent[] currentStartTreatmentEvents = new StartTreatmentEvent[NUMBER_OF_TREATMENT_ROOMS];

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

        handleArrivalEvents(patientType, processingTime);
        handleAssessmentEvents();
        handleStartTreatmentEvents();
        handleAdmittingToHospitalEvents();
        handleDepartureEvents();
    }

    /**
     * Move AssessmentEvents forward.
     * If there are no patients being assessed and there is someone in queue, asses them.
     * Only one patient can assessed at a time.
     */
    private static void handleAssessmentEvents() {
        //Assess 1 patient in queue if no patient is being assessed currently.
        if ((currentAssessmentEvent == null || currentAssessmentEvent.isDone()) && !assessmentQueue.isEmpty()) {
            //print result if patient is done and add them to waiting queue
            if (currentAssessmentEvent != null && currentAssessmentEvent.isDone()) {
                System.out.println(currentAssessmentEvent);
                waitingQueue.add(currentAssessmentEvent.getPatient());
                assessmentQueue.remove();
            }
            if (!assessmentQueue.isEmpty()) {
                currentAssessmentEvent = new AssessmentEvent(assessmentQueue.getHead().getPatient());
                currentAssessmentEvent.start();
            }
        }
        //Increase waiting time for everyone that is treated and waiting to be admitted. (i = 0 is currently being assessed so skip it)
        for (int i = 1; i < assessmentQueue.size(); i++) {
            assessmentQueue.get(i).increaseWaitingTime();
        }
    }

    /**
     *
     */
    private static void handleStartTreatmentEvents() {
        //Assess 1 patient in queue if no patient is being assessed currently.
        if (treatmentRooms.anyRoomAvailable() && !waitingQueue.isEmpty()) {
            //print result if patient is done and add them to waiting queue

            try {
                StartTreatmentEvent startTreatmentEvent = new StartTreatmentEvent(waitingQueue.remove());
                currentStartTreatmentEvents[treatmentRooms.getRoomAvailable()] = startTreatmentEvent;
                treatmentRooms.placePatient(treatmentRooms.getRoomAvailable(), startTreatmentEvent);
                startTreatmentEvent.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (StartTreatmentEvent event : currentStartTreatmentEvents) {
            if (event != null && event.isDone()) {
                System.out.println(event.toString());
                //TODO: End treatment event
            }
        }
        //Increase waiting time for everyone that is not being assessed. (i = 0 is currently being assessed so skip it)
        for (int i = 1; i < waitingQueue.size(); i++) {
            waitingQueue.get(i).increaseWaitingTime();
        }
    }

    /**
     *
     */
    private static void handleAdmittingToHospitalEvents() {
        //Assess 1 patient in queue if no patient is being assessed currently.
        if ((currentAdmittingToHospitalEvent == null || currentAdmittingToHospitalEvent.isDone()) && treatmentRooms.anyRoomBeingUsed()) {


            //If patient admitted to hospital
            if (currentAdmittingToHospitalEvent != null && currentAdmittingToHospitalEvent.isDone()) {
                System.out.println(currentAdmittingToHospitalEvent.toString());
                admissionNurseAvailable = true;
                currentAdmittingToHospitalEvent.getPatient().setDepartureTime(getCurrentClockTime()); //patient has now "departed"
                treatmentRooms.releasePatient(currentAdmittingToHospitalEvent.getPatient());
            }

            ArrayList<Patient> priority1Patients = treatmentRooms.getDonePriority1Patients();

            if (treatmentRooms.anyRoomBeingUsed() && admissionNurseAvailable && !priority1Patients.isEmpty()) { //should be treatment rooms
                Patient temp = priority1Patients.get(0);
                if (priority1Patients.size() > 1) {//gets priority 1 patient that has waited the longest
                    for(Patient patient: priority1Patients){
                        if(patient.getWaitingTime() > temp.getWaitingTime()){
                            temp = patient;
                        }
                    }
                }
                admissionNurseAvailable = false;
                currentAdmittingToHospitalEvent = new AdmittingToHospitalEvent(temp); //gets patient waiting in treatment room
                currentAdmittingToHospitalEvent.start();
            }
        }
        //this increases wait time of priority 1 patients that are in the treatment room and are not addmitted
        for(Patient patient: treatmentRooms.getDonePriority1Patients()){
            if(patient != currentAdmittingToHospitalEvent.getPatient()){
                patient.increaseWaitingTime();
            }
        }
    }

    /**
     *
     */
    private static void handleDepartureEvents() {
        //If any room is occupied...
        if (treatmentRooms.anyRoomBeingUsed()) {
            //...Remove and print finished departures
            for (int i = 0; i < currentDepartureEvents.size(); i++) {

                if (currentDepartureEvents.get(i).isDone() && currentDepartureEvents.get(i).getPatient().getPriority() != 1) {
                    System.out.println(currentDepartureEvents.get(i));
                    treatmentRooms.releasePatient(currentDepartureEvents.get(i).getPatient());
                    currentDepartureEvents.get(i).getPatient().setDepartureTime(getCurrentClockTime());
                    System.out.println(currentDepartureEvents.get(i).getPatient().getDepartureTime());
                    currentDepartureEvents.remove(currentDepartureEvents.get(i));
                }
            }

            //...Create new departures
            if (treatmentRooms.anyRoomBeingUsed()) {
                StartTreatmentEvent[] treatmentEvents = treatmentRooms.getTreatmentEvents();
                for(StartTreatmentEvent event: treatmentEvents){
                    if(event != null && event.getPatient().getPriority() != 1 && event.isDone() && !currentDepartureEvents.contains(event)){
                        DepartureEvent departureEvent = new DepartureEvent(event.getPatient());
                        currentDepartureEvents.add(departureEvent);
                        departureEvent.start();

                    }
                }
            }
        }
    }

    /**
     * @param patientType
     * @param processingTime
     */
    private static void handleArrivalEvents(ArrayList<Character> patientType, ArrayList<Integer> processingTime) {
        //create arrivals and add to corresponding queues
        for (int i = 0; i < patientType.size(); i++) {
            ArrivalEvent arrivalEvent = new ArrivalEvent(patientType.get(i), processingTime.get(i));

            if (patientType.get(i) == Patient.CODE_W) {
                assessmentQueue.add(arrivalEvent.getPatient());
            } else if (patientType.get(i) == Patient.CODE_E) {
                waitingQueue.add(arrivalEvent.getPatient());
            } else {
                System.out.println("ERROR: simulation.Patient has unknown type. Using W as a result.");
                assessmentQueue.add(arrivalEvent.getPatient());
            }

        }
    }


}
