package simulation;

import events.AdmittingToHospital;
import events.ArrivalEvent;
import events.AssessmentEvent;
import events.DepartureEvent;
import events.StartTreatmentEvent;
import queues.LinearQueue;
import queues.TreatmentRoom;
import queues.WaitingQueue;

import java.util.ArrayList;

import static simulation.Clock.getCurrentClockTime;

public final class Simulation {

    public static final int NUMBER_OF_TREATMENT_ROOMS = 3;

    //TODO: change queues to hold events instead of patients? Maybe
    private static final LinearQueue assessmentQueue = new LinearQueue();
    private static final WaitingQueue waitingQueue = new WaitingQueue();

    ; //TODO: treatment rooms taking care of themselves.
    private static AssessmentEvent currentAssessmentEvent;
    private static AdmittingToHospital currentAdmittingToHospitalEvent;
    private static ArrayList<DepartureEvent> currentDepartureEvents;
    private static boolean admissionNurseAvailable = true;
    public static  TreatmentRoom treatmentRoom = new TreatmentRoom();
   private static StartTreatmentEvent[] currentStartTreatmentEvent = new StartTreatmentEvent[3];

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
        handleStartTreatmentEvents();
        handleAdmittingToHospitalEvents();
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
        //Increase waiting time for everyone that is treated and waiting to be admitted. (i = 0 is currently being assessed so skip it)
        for (int i = 1; i < assessmentQueue.size(); i++) {
            assessmentQueue.get(i).increaseWaitingTime();
        }
    }

    private static void handleStartTreatmentEvents() {
        //Assess 1 patient in queue if no patient is being assessed currently.
        if (treatmentRoom.roomCheck() != 0 && !waitingQueue.isEmpty()) {
            //print result if patient is done and add them to waiting queue

            currentStartTreatmentEvent[treatmentRoom.roomCheck()-1] = new StartTreatmentEvent(waitingQueue.remove());
            currentStartTreatmentEvent[treatmentRoom.roomCheck()-1].start();

            for (StartTreatmentEvent starTreamentEvent: currentStartTreatmentEvent) {
                if(starTreamentEvent != null && starTreamentEvent.isDone()){
                    System.out.println(starTreamentEvent.toString());
                }
            }

        }

        //Increase waiting time for everyone that is not being assessed. (i = 0 is currently being assessed so skip it)
        for (int i = 1; i < assessmentQueue.size(); i++) {
            assessmentQueue.get(i).increaseWaitingTime();
        }
    }



    private static void handleAdmittingToHospitalEvents() {
        //Assess 1 patient in queue if no patient is being assessed currently.
        if ((currentAdmittingToHospitalEvent == null || currentAdmittingToHospitalEvent.isDone()) && treatmentRoom.getPatients()!=null) {
            //print result if patient is done and add them to waiting queue
            if(currentAdmittingToHospitalEvent != null && currentAdmittingToHospitalEvent.isDone()){
                System.out.println(currentAdmittingToHospitalEvent);
                admissionNurseAvailable = true;
                Patient tempPatient;
                currentAdmittingToHospitalEvent.getPatient().setDepartureTime(getCurrentClockTime());
                Patient [] tempPatients = treatmentRoom.getPatients();
                int patientRoom =0;
                for (int i = 0; i <= 2; i++) { if (currentAdmittingToHospitalEvent.getPatient() == tempPatients[i] ){patientRoom = i;}}
                treatmentRoom.setRoomIsFree(patientRoom);
                //patient priority 1 departs HOW TO MAKE PATIENT DEPART?
            }
            ArrayList <Patient> priority1Patients =new ArrayList<>();
            boolean isTherepriority1 = false;
            Patient [] tempPatients = treatmentRoom.getPatients();
            for (int i = 0; i <= 2; i++) { if (tempPatients[i].getPriority() == 1 && treatmentRoom.getPatientIsDone(i) ){priority1Patients.add(tempPatients[i]); isTherepriority1 =true;}}
            if(treatmentRoom.getPatients()!=null && admissionNurseAvailable && isTherepriority1){ //should be treatment rooms
                Patient temp =priority1Patients.get(0);
                if (priority1Patients.size() > 1){//gets priority 1 patient that has waited the longest
                    for(int i = 0; i <= priority1Patients.size(); i++){if (priority1Patients.get(i).getWaitingTime()> temp.getWaitingTime()){temp = priority1Patients.get(i);}}
                }
                admissionNurseAvailable = false;
                currentAdmittingToHospitalEvent = new AdmittingToHospital(temp); //gets patient waiting in treatment room
                currentAdmittingToHospitalEvent.start();
            }
        }
        //Increase waiting time for everyone that is not being assessed. (i = 0 is currently being assessed so skip it)
        //needs to be changed from assesmentqueue to treatment rooms patients
        for (int i = 1; i < assessmentQueue.size(); i++) {
            if (assessmentQueue.get(i).getPriority() == 1) {assessmentQueue.get(i).increaseWaitingTime();}
        }
    }

    private static void handleDepartureEvents() {                                         //AND TREATMENT FOR PATIENT IS DONE
        //Assess 1 patient in queue if no patient is being assessed currently.                   line under should be treatments rooms
        if (!assessmentQueue.isEmpty()) {
            //print result if patient is done and add them to waiting queue
            for (int i = 1; i < currentDepartureEvents.size(); i++) {
                if (currentDepartureEvents != null && currentDepartureEvents.get(i).isDone()) {
                    System.out.println(currentDepartureEvents.get(i));
                    //opens treatment room
                    //patient priority 2-5 departs
                } //line under here changed to treatment rooms
            }
            if(!assessmentQueue.isEmpty()){ //should be treatment rooms
                //currentAdmittingToHospitalEvent = new AdmittingToHospital() gets patient waiting in treatment room
                //currentAdmittingToHospitalEvent.start();
            }
        }
        //Increase waiting time for everyone that is not being assessed. (i = 0 is currently being assessed so skip it)
        //needs to be changed from assesmentqueue to treatment rooms patients
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
