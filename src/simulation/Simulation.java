package simulation;

import events.ArrivalEvent;
import events.AssessmentEvent;
import queues.LinearQueue;
import queues.TreatmentRoom;
import queues.WaitingQueue;

import java.util.ArrayList;

public final class Simulation {

    public static final int NUMBER_OF_TREATMENT_ROOMS = 3;

    //TODO: change queues to hold events instead of patients? Maybe
    private static final LinearQueue assessmentQueue = new LinearQueue();
    private static final WaitingQueue waitingQueue = new WaitingQueue();
    private static TreatmentRoom[] treatmentRooms = new TreatmentRoom[NUMBER_OF_TREATMENT_ROOMS];
    ; //TODO: treatment rooms taking care of themselves.
    private static AssessmentEvent currentAssessmentEvent;

    private Simulation() {
        /* empty */
    }

    public static LinearQueue getAssessmentQueue() {
        return assessmentQueue;
    }

    public static WaitingQueue getWaitingQueue() {
        return waitingQueue;
    }
    /*
    This method is called to enter the information of the patient and the processing time.
    It then calls handlerArrivalEvent to accommodate the patient accordingly
     */
    public static void doOneClockCycle(ArrayList<Character> patientType, ArrayList<Integer> processingTime) {

        handleArrivalEvents(patientType, processingTime);

        handleAssessmentEvents();
    }

    private static void handleAssessmentEvents() {
        //Assess 1 patient in queue if no patient is being assessed currently.
        if ((currentAssessmentEvent == null || currentAssessmentEvent.isDone()) && !assessmentQueue.isEmpty()) {
            //print result if patient is done and add them to waiting queue
            if(currentAssessmentEvent != null && currentAssessmentEvent.isDone()){
                System.out.println(currentAssessmentEvent);
                waitingQueue.add(currentAssessmentEvent.getPatient());
                assessmentQueue.remove();
            }
            if(!assessmentQueue.isEmpty()){
                currentAssessmentEvent = new AssessmentEvent(assessmentQueue.getHead().getPatient());
                currentAssessmentEvent.start();
            }
        }

        //Increase waiting time for everyone that is not being assessed. (i = 0 is currently being assessed so skip it)
        for (int i = 1; i < assessmentQueue.size(); i++) {
            assessmentQueue.get(i).increaseWaitingTime();
        }
    }

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
