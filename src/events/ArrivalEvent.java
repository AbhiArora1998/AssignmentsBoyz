package events;

import queues.LinearQueue;
import simulation.Patient;

import java.util.Random;

public class ArrivalEvent extends Event {

    private final char patientType;
    private final int treatmentTime;
    private LinearQueue linearQueue;
    private final Random random = new Random(1000);
    private Patient patient;

    public ArrivalEvent(char patientType, int treatmentTime) {
        super();
        this.patientType = patientType;
        this.treatmentTime = treatmentTime;

        start();
        /*
        Checks to see if the patient type is E
         */
        if(patientType == Patient.CODE_E){
            patient.setAssessmentTime(startTime);
        }

        System.out.println(this);
    }

    public ArrivalEvent(char patientType, int treatmentTime, int priority) {
        super();
        this.patientType = patientType;
        this.treatmentTime = treatmentTime;

        start(priority);
        if(patientType == Patient.CODE_E){
            patient.setAssessmentTime(startTime);
        }
        System.out.println(this);
    }

    /**
     * Create a new simulation.Patient
     * @param priority The severity of the patient's symptoms, used to determine which patients
     *                 are to be treated earliest
     */
    public void start(int priority){
        super.start();
        patient = new Patient(creationTime, patientType, treatmentTime, priority);
    }

    @Override
    public void start() {
        super.start();
        patient = new Patient(creationTime, patientType, treatmentTime);
    }

    public Patient getPatient() {
        return patient;
    }

    @Override
    public String toString() {
        if (patient.getType() == Patient.CODE_E){
            return "Time " + creationTime + ":  " + patient.getPatientNumber() + " (Emergency) arrives" ;
        } else if (patient.getType() == Patient.CODE_W){
            return "Time " + creationTime + ":  " + patient.getPatientNumber() + " (Walk-in) arrives" ;
        } else {
            return "Error: simulation.Patient not initialized (in to String).";
        }
    }
}
