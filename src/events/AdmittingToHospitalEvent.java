package events;

import simulation.Patient;
import static simulation.Clock.getCurrentClockTime;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Fall 2021
 *
 * Executes when patients with priority 1 are admitted to the hospital
 *
 * @author The Boyz
 * @version 1
 */

public class AdmittingToHospitalEvent extends Event {

    private static final int ADMITTING_PROCESSING_TIME = 3;
    private static AdmittingToHospitalEvent currentAdmittingToHospitalEvent = null;

    public AdmittingToHospitalEvent(Patient patient) {
        super();
        processingTime = ADMITTING_PROCESSING_TIME;
        this.patient = patient;
    }

    /**
     * If the admission nurse is not busy with another patient, starts AdmittingToHospitalEvent
     */
    @Override
    public void start(){
        if(currentAdmittingToHospitalEvent == null){
            super.start();
            patient.setCurrentEvent(this);
        }
        shouldStart = false;
    }

    /**
     * Having healed sufficiently, patient departs from their hospital room and heads home
     */
    @Override
    public void finish() {
        currentAdmittingToHospitalEvent = null;
        patient.setCurrentEvent(new DepartureEvent(patient));
        isDone = true;
        System.out.println(this);
    }

    /**
     * Returns a String reporting the current time, patient number and priority, and what the patient is undergoing
     * @return The current time, patient number and priority, and what the patient is undergoing
     */
    @Override
    public String toString() {
        if(!isDone()){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() +", waited " + patient.getWaitingTime()+ ") is being admitted to Hospital";
        } else {
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ", waited " + (getCurrentClockTime() - (patient.getFinishTreatmentTime()+ADMITTING_PROCESSING_TIME)) + ") admitted to Hospital";
        }
    }
}
