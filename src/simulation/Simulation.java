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

import events.AdmittingToHospital;
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
    private static AdmittingToHospital currentAdmittingToHospitalEvent;
    private static ArrayList<DepartureEvent> currentDepartureEvents;
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

    private static void handleStartTreatmentEvents() {
        //Assess 1 patient in queue if no patient is being assessed currently.
        if (treatmentRooms.anyRoomAvailable() && !waitingQueue.isEmpty()) {
            //print result if patient is done and add them to waiting queue

            try {
                currentStartTreatmentEvents[treatmentRooms.getRoomAvailable()] = new StartTreatmentEvent(waitingQueue.remove());
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (StartTreatmentEvent starTreatmentEvent : currentStartTreatmentEvents) {
                if (starTreatmentEvent != null && starTreatmentEvent.isDone()) {
                    System.out.println(starTreatmentEvent.toString());
                }
            }
        }

        //Increase waiting time for everyone that is not being assessed. (i = 0 is currently being assessed so skip it)
        for (int i = 1; i < waitingQueue.size(); i++) {
            waitingQueue.get(i).increaseWaitingTime();
        }
    }


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


            ArrayList<Patient> priority1Patients = new ArrayList<>();
            boolean isTherepriority1 = false;
            Patient[] tempPatients = treatmentRoom.getPatients();
            for (int i = 0; i <= NUMBER_OF_TREATMENT_ROOMS - 1; i++) {
                if (tempPatients[i].getPriority() == 1 && treatmentRoom.getPatientIsDone(i)) {
                    priority1Patients.add(tempPatients[i]);
                    isTherepriority1 = true;
                }
            }
            if (treatmentRoom.getPatients() != null && admissionNurseAvailable && isTherepriority1) { //should be treatment rooms
                Patient temp = priority1Patients.get(0);
                if (priority1Patients.size() > 1) {//gets priority 1 patient that has waited the longest
                    for (int i = 0; i <= priority1Patients.size(); i++) {
                        if (priority1Patients.get(i).getWaitingTime() > temp.getWaitingTime()) {
                            temp = priority1Patients.get(i);
                        }
                    }
                }
                admissionNurseAvailable = false;
                currentAdmittingToHospitalEvent = new AdmittingToHospital(temp); //gets patient waiting in treatment room
                currentAdmittingToHospitalEvent.start();
            }
        }
        //this increases wait time of priority 1 patients that are in the treatment room and are not addmitted
        if ((currentAdmittingToHospitalEvent != null && !currentAdmittingToHospitalEvent.isDone())) {
            ArrayList<Patient> priority1Patients = new ArrayList<>();
            Patient[] tempPatients = treatmentRoom.getPatients();
            for (int i = 0; i <= NUMBER_OF_TREATMENT_ROOMS - 1; i++) {
                if (tempPatients[i].getPriority() == 1 && treatmentRoom.getPatientIsDone(i) && tempPatients[i] != currentAdmittingToHospitalEvent.getPatient()) {
                    priority1Patients.add(tempPatients[i]);
                }
            }
            for (int i = 1; i < priority1Patients.size(); i++) {
                priority1Patients.get(i).increaseWaitingTime();
            }
        }
    }

    private static void handleDepartureEvents() {                                         //AND TREATMENT FOR PATIENT IS DONE
        //
        if (treatmentRoom.getPatients() != null) {
            //print result if patient is done and add them to waiting queue
            for (int i = 1; i < currentDepartureEvents.size(); i++) {
                if (currentDepartureEvents != null && currentDepartureEvents.get(i).isDone() && currentDepartureEvents.get(i).getPatient().getPriority() != 1) {
                    System.out.println(currentDepartureEvents.get(i));
                    Patient[] tempPatients = treatmentRoom.getPatients();
                    //opens treatment room
                    for (int j = 0; j <= NUMBER_OF_TREATMENT_ROOMS-1; j++) { if (tempPatients[j].getPriority() != 1 && treatmentRoom.getPatientIsDone(j) && tempPatients[j]== currentDepartureEvents.get(i).getPatient()){treatmentRoom.setRoomIsFree(j);}}
                    currentDepartureEvents.get(i).getPatient().setDepartureTime(getCurrentClockTime()); //patient departs
                    currentDepartureEvents.remove(i);
                }
            }
            if(treatmentRoom.getPatients()!=null){ //should be treatment rooms
                Patient [] tempPatients = treatmentRoom.getPatients();
                for (int j = 0; j <= NUMBER_OF_TREATMENT_ROOMS-1; j++) { if (tempPatients[j].getPriority() != 1 && treatmentRoom.getPatientIsDone(j) && !currentDepartureEvents.contains(tempPatients[j])){
                    currentDepartureEvents.add(new DepartureEvent(tempPatients[j]));
                    currentDepartureEvents.indexOf(tempPatients[j]);
                    currentDepartureEvents.get(currentDepartureEvents.indexOf(tempPatients[j])).start();
                }}
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
