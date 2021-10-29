package events;

import simulation.Patient;
import simulation.Simulation;

public class StartTreatmentEvent extends Event {

    Patient patient;
    public StartTreatmentEvent(Patient patient) {
        super();
        this.patient = patient;
        processingTime = patient.getTreatmentTime();
    }

    @Override
    public void start(){
        super.start();
        Simulation.treatmentRoom.enterTreatmentRoom(patient);

    }
}
