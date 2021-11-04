package events;

import simulation.Patient;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Simulation.*;

public class AssessmentEvent extends Event {

    private static final int ASSESSMENT_PROCESSING_TIME = 4;
    private static AssessmentEvent currentEvent;

    public AssessmentEvent(Patient patient) {
        super();
        processingTime = ASSESSMENT_PROCESSING_TIME;
        this.patient = patient;

    }

    @Override
    public void start(){
        if(currentEvent == null){
            super.start();
            patient.setAssessmentTime(getCurrentClockTime());
            currentEvent = this;
            System.out.println(this);
            patient.setCurrentEvent(this);
        }
            shouldStart = false;
    }

    @Override
    public void finish() {
        currentEvent = null;
        waitingQueue.add(new StartTreatmentEvent(patient));
        assessmentQueue.remove();
        patient.setCurrentEvent(null);
        isDone = true;
        System.out.println(this);
        System.out.println("Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber() + " (Priority " + patient.getPriority() + ") enters waiting room");
    }

    @Override
    public String toString() {

        if(isDone()){
            patient.setAssessmentCompletedTime(getCurrentClockTime());
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " assessment completed (Priority now " + patient.getPriority() +")";
        } else if (hasStarted && startTime == getCurrentClockTime()){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " starts assessment (waited " + patient.getWaitingTime() +")";
        } else if (hasStarted){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " is STILL being assessed (time remaining: " + (processingTime - (getCurrentClockTime() - startTime) + ").");
        } else {
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") is WAITING to be assessed"
                    + " (waited " + patient.getWaitingTime() + ")";
        }
    }
}
