public class StartTreatmentEvent extends Event{

    private int startTime;

    public StartTreatmentEvent(Patient patient) {
        processingTime = patient.getTreatmentTime();
        startTime = Clock.getTime();
    }

    @Override
    public void happens() {
        if ((Clock.getTime() - startTime) >= processingTime){
            done = true;
        }
    }
}
