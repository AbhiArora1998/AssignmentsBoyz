package events;

import simulation.Patient;
import simulation.Simulation;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Simulation.treatmentRooms;

public class StartTreatmentEvent extends Event {

    private Patient patient;
    public StartTreatmentEvent(Patient patient) {
        super();
        this.patient = patient;
        processingTime = patient.getTreatmentTime();
    }

    @Override
    public void start(){
        super.start();
        System.out.println("Time " + startTime + ":  " + patient.getPatientNumber() + " (Priority " + patient.getPriority() + ") starts treatment (waited " + patient.getWaitingTime() + ", "+ treatmentRooms.roomsAvailable() + " rm(s) remain)");
    }

    public Patient getPatient() {
        return patient;
    }

    @Override
    public String toString() {
        if(!isDone()){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " is still being treated (time remaining: " + (processingTime - (getCurrentClockTime() - startTime) + ").");
        } else {
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber() + " (Priority " + patient.getPriority() + ") finishes treatment";
        }

    }
}
