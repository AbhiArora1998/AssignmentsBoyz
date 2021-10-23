import java.util.Random;

public class ArrivalEvent extends Event{

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
    }

    public ArrivalEvent(char patientType, int treatmentTime, int priority) {
        super();
        this.patientType = patientType;
        this.treatmentTime = treatmentTime;

        start(priority);
    }

    /**
     * Create a new Patient
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
        new Patient(creationTime, patientType, treatmentTime);
    }

    public Patient getPatient() {
        return patient;
    }
}
