package events;

import simulation.Patient;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Simulation.assessmentQueue;
import static simulation.Simulation.waitingQueue;

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
