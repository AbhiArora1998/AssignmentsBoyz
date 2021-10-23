public class StartTreatmentEvent extends Event{

    public StartTreatmentEvent(Patient patient) {
        super();
        processingTime = patient.getTreatmentTime();
    }

    @Override
    public void start(){
        super.start();
    }
}
