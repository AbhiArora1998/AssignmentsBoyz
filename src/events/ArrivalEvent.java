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

    @Override
    public void start() {
        super.start();
        patient.setCurrentEvent(this);
        shouldStart = false;
    }

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
