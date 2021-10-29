package events;

import simulation.Patient;
import simulation.Simulation;

public class AdmittingToHospital extends Event {

    private static final int ADMITTING_PROCESSING_TIME = 3;
    private Patient patient;

    public AdmittingToHospital(Patient patient) {
        super();
        processingTime = ADMITTING_PROCESSING_TIME;
        this.patient = patient;
    }

    @Override
    public void start(){
        super.start();
        patient.setAdmittingToHospitalTime(startTime);
        System.out.println(this);
    }

    @Override
    public String toString() {
        if(!isDone()){
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() +") admitted to Hospital";
        } else {
            return "Time " + (startTime + processingTime) + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() +") departs (" + "MISSING TREATMENT ROOM NUMBER AVAILABLE" + " room still available";
        }

    }

    public Patient getPatient() {
        return patient;
    }
}
