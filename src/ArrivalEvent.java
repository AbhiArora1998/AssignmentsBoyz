import java.util.Random;

public class ArrivalEvent extends Event{

    private final int arrivalTime;
    private final char patientType;
    private final int treatmentTime;
    private AssessmentQueue assessmentQueue;
    private final Random random = new Random(1000);

    public ArrivalEvent(int arrivalTime, char patientType, int treatmentTime) {
        this.arrivalTime = arrivalTime;
        this.patientType = patientType;
        this.treatmentTime = treatmentTime;

        happens();

        done = true;
    }

    @Override
    public void happens() {
         new Patient(arrivalTime, patientType, treatmentTime);
    }
}
