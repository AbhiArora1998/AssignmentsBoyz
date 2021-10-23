public class Patient {

    public static final int CODE_MISSING = 0, CODE_W = 1, CODE_E = 2;
    public static final int PRIORITY_MISSING = 0;
    private int patientNumber = 0;
    private int code = 0;
    private int priority = 0;

    public Patient(int patientNumber, int code) {
        this.code = code;
        this.patientNumber = patientNumber;

        if(code == CODE_E){
            priority = 1;
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
