package events;

import simulation.Patient;

public class AssessmentEvent extends Event {

    private static final int ASSESSMENT_PROCESSING_TIME = 4;
    private Patient patient;

    public AssessmentEvent(Patient patient) {
        super();
        processingTime = ASSESSMENT_PROCESSING_TIME;
        this.patient = patient;
    }

    @Override
    public void start(){
        super.start();
        patient.setAssessmentTime(startTime);
        System.out.println(this);
    }

    @Override
    public String toString() {
        if(!isDone()){
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " starts assessment (waited " + patient.getWaitingTime() +")";
        } else {
            return "Time " + (startTime + processingTime) + ":  " + patient.getPatientNumber()
                    + " assessment completed (Priority now " + patient.getPriority() +")";
        }

    }

    public Patient getPatient() {
        return patient;
    }
}
