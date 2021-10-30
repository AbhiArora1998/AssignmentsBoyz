package events;

import simulation.Patient;
import simulation.Simulation;

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
    }

    public Patient getPatient() {
        return patient;
    }
}
