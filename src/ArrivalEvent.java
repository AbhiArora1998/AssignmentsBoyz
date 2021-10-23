import java.util.Random;

public class ArrivalEvent extends Event{

    private final int arrivalTime;
    private final char code;
    private final int treatmentTime;
    private Patient patient;
    private AssessmentQueue assessmentQueue;
    private final Random random = new Random(1000);

    public ArrivalEvent(int arrivalTime, char code, int treatmentTime) {
        this.arrivalTime = arrivalTime;
        this.code = code;
        this.treatmentTime = treatmentTime;
    }

    @Override
    public void happens() {
        new Patient(arrivalTime, code, treatmentTime);
    }

    public void priorityCheck(Patient patient){
        patient.getPriority();
    }
}
