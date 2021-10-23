public class ArrivalEvent extends Event{

    private final char patientType;
    private final int treatmentTime;
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
