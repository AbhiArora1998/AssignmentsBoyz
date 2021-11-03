package events;

import simulation.Patient;
import simulation.Simulation;

import static simulation.Clock.getCurrentClockTime;

public class AdmittingToHospitalEvent extends Event {

    private static final int ADMITTING_PROCESSING_TIME = 3;
    private static AdmittingToHospitalEvent currentAdmittingToHospitalEvent = null;

    public AdmittingToHospitalEvent(Patient patient) {
        super();
        processingTime = ADMITTING_PROCESSING_TIME;
        this.patient = patient;
    }

    @Override
    public void start(){
        if(currentAdmittingToHospitalEvent == null){
            super.start();
            patient.setAdmittingToHospitalTime(getCurrentClockTime());
            patient.setCurrentEvent(this);

        }
            shouldStart = false;


    }

    @Override
    public void finish() {
        currentAdmittingToHospitalEvent = null;
        patient.setCurrentEvent(new DepartureEvent(patient));
        isDone = true;
        System.out.println(this);
    }

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

    public static AdmittingToHospitalEvent getCurrentAdmittingToHospitalEvent() {
        return currentAdmittingToHospitalEvent;
    }

    public static void setCurrentAdmittingToHospitalEvent(AdmittingToHospitalEvent currentAdmittingToHospitalEvent) {
        AdmittingToHospitalEvent.currentAdmittingToHospitalEvent = currentAdmittingToHospitalEvent;
    }
}
