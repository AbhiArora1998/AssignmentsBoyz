import java.util.Random;

public class ArrivalEvent extends Event{

    private final char patientType;
    private final int treatmentTime;
    private AssessmentQueue assessmentQueue;
    private final Random random = new Random(1000);

    public ArrivalEvent(char patientType, int treatmentTime) {
        super();
        this.patientType = patientType;
        this.treatmentTime = treatmentTime;

        start();
    }

    public ArrivalEvent(char patientType, int treatmentTime, int priority) {
        super();
        this.patientType = patientType;
        this.treatmentTime = treatmentTime;

        start(priority);
    }

    public void start(int priority){
        super.start();
        new Patient(creationTime, patientType, treatmentTime, priority);
    }

    @Override
    public void start() {
        super.start();
        new Patient(creationTime, patientType, treatmentTime);
    }
}
