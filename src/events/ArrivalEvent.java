package events;

import simulation.Patient;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Simulation.assessmentQueue;
import static simulation.Simulation.waitingQueue;
/**
 * This file is part of a solution to
 * CPSC300 Assignment 1 Fall 2021
 * <p>
 * This class takes care of the arrival event that happens in the hospital simulation
 * Patients are created and given their startTime, patientType and treatment time
 * Also Displays if walk-in or emergency has arrived
 * walk-in patients are put in the
 * Event finishes when patient enters waiting room
 *
 * @author The Boyz
 * @version 1
 */
public class ArrivalEvent extends Event {

    private static final int ARRIVAL_PROCESSING_TIME = 0;

    public ArrivalEvent(char patientType, int treatmentTime) {
        super();
        this.processingTime = ARRIVAL_PROCESSING_TIME;
        patient = new Patient(startTime, patientType, treatmentTime);
    }

    /**
     * Starts ArrivalEvent
     */
    @Override
    public void start() {
        super.start();
        patient.setCurrentEvent(this);
        shouldStart = false;
    }

    /**
     * Records patient arrival time, places walk-in patients in the assessment line, places emergency patients in the
     * waiting room and records their assessment time immediately, and prints a report of what patient is undergoing
     */
    @Override
    public void finish() {
        patient.setArrivalTime(getCurrentClockTime());
        patient.setCurrentEvent(null);
        if (patient.getType() == Patient.CODE_W) {
            assessmentQueue.add(new AssessmentEvent(patient));
        } else if (patient.getType() == Patient.CODE_E) {
            patient.setAssessmentTime(getCurrentClockTime());
            waitingQueue.add(new StartTreatmentEvent(patient));
        } else {
            System.out.println("ERROR: simulation.Patient has unknown type. Using W as a result.");
            patient.setType('W');
            assessmentQueue.add(new AssessmentEvent(patient));
        }
        System.out.println(this);
        if(patient.getType() == Patient.CODE_E){
            System.out.println("Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber() + " (Priority " + patient.getPriority() + ") enters waiting room");
        }
        isDone = true;
    }

    /**
     * Returns a String reporting the current time, patient number, and what type of patient is arriving
     * @return The current time, patient number, and what type of patient is arriving
     */
    @Override
    public String toString() {
        if (patient.getType() == Patient.CODE_E){
            patient.setAssessmentCompletedTime(getCurrentClockTime());
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber() + " (Emergency) arrives" ;
        } else if (patient.getType() == Patient.CODE_W){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber() + " (Walk-in) arrives" ;
        } else {
            return "Error: simulation.Patient not initialized (in to String).";
        }
    }
}
