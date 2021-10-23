import java.util.Random;

public class Patient {

    public static final int CODE_MISSING = 0, CODE_W = 1, CODE_E = 2;
    public static final int PRIORITY_MISSING = 0;
    private int patientNumber = 0;
    private int code = 0;
    private int priority = 0;
    private int treatmentTime = 0;
    static Random random = new Random(1000);

    public Patient(int patientNumber, int code, int treatmentTime) {
        this.code = code;
        this.patientNumber = patientNumber;
        this.treatmentTime = treatmentTime;

        if(code == CODE_E){
            priority = 1;
        } else {
            priority = random.nextInt(5)+1;
        }
    }

    public int getPatientNumber() {
        return patientNumber;
    }

    public int getCode() {
        return code;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
