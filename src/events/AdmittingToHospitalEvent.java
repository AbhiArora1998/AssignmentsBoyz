package events;

import simulation.Patient;
import simulation.Simulation;

public class AdmittingToHospitalEvent extends Event {

    private static final int ADMITTING_PROCESSING_TIME = 3;
    private Patient patient;

    public AdmittingToHospitalEvent(Patient patient) {
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
        if(isDone()){
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() +", waited " + patient.getWaitingTime()+ ") admitted to Hospital";
        }
        return null;
    }

    public Patient getPatient() {
        return patient;
    }
}
