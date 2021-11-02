package events;

import simulation.Patient;
import simulation.Simulation;

import static simulation.Clock.getCurrentClockTime;

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

    }

    @Override
    public String toString() {
        if(!isDone()){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() +", waited " + patient.getWaitingTime()+ ") is being admitted to Hospital";
        } else {
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ", waited " + patient.getWaitingTime() + ") admitted to Hospital";
        }
    }

    public Patient getPatient() {
        return patient;
    }
}
