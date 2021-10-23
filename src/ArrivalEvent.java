import java.util.Random;

public class ArrivalEvent extends Event{

    private final int time;
    private final char code;
    private final int treatmentTime;
    private Patient patient;
    private AssessmentQueue assessmentQueue;
    private final Random random = new Random(1000);

    public ArrivalEvent(int time, char code, int treatmentTime) {
        this.time = time;
        this.code = code;
        this.treatmentTime = treatmentTime;
    }

    @Override
    public void happens() {
        new Patient(code, treatmentTime, random.nextInt(5)+1);
    }

    public void priorityCheck(Patient patient){
        patient.getPriority();
    }

    @Override
    public void departure(Patient patient) {
           assessmentQueue.remove(patient);
    }
}
