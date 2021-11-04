package events;

import simulation.Patient;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Simulation.treatmentRooms;
import static simulation.Simulation.waitingQueue;

public class TreatmentCompletedEvent extends Event {

    public TreatmentCompletedEvent(Patient patient) {
        super();
        this.patient = patient;
        processingTime = 0;
    }

    @Override
    public void start(){
        super.start();
        patient.setCurrentEvent(this);
        System.out.println(this);

        if(patient.getPriority() != Patient.HIGHEST_PRIORITY){
            patient.setCurrentEvent(new DepartureEvent(patient));
            patient.getCurrentEvent().setShouldStart(true);
        }

        shouldStart = false;
    }

    @Override
    public void finish() {
        patient.setCurrentEvent(null);
        isDone = true;
        System.out.println(this);
    }

    @Override
    public String toString() {
        if(isDone()){
            patient.setFinishTreatmentTime(getCurrentClockTime());
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") finishes treatment";
        } else if (hasStarted && startTime == getCurrentClockTime()){
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") starts treatment"
                    + " (waited " + (getCurrentClockTime()- patient.getAssessmentCompletedTime())//(getCurrentClockTime() - patient.getAssessmentTime()) + " START TREATMENT EVENT assessetime " + patient.getAssessmentTime()
                    + ", "+ treatmentRooms.roomsAvailable() + " rm(s) remain)";
        } else if (hasStarted){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " is STILL being treated (time remaining: " + (processingTime - (getCurrentClockTime() - startTime) + ").");
        } else {
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") is WAITING for treatment"
                    + " (waited " + patient.getWaitingTime()
                    + ", "+ treatmentRooms.roomsAvailable() + " rm(s) remain)";
        }
    }
}
