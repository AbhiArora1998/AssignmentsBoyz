import java.util.ArrayList;
import java.util.Random;

public class Patient {

    public static final char CODE_MISSING = 0, CODE_W = 'W', CODE_E = 'E';
    public static final int PRIORITY_MISSING = 0;

    private static int nextPatientNumber = 28064212;
    public static ArrayList<Patient> patients = new ArrayList<>();

    private int patientNumber = 0;
    private char code = 0;
    private int priority = 0;
    private int treatmentTime = 0;


    public Patient(char code, int treatmentTime, int priority) {
        this.code = code;
        this.patientNumber = nextPatientNumber++;
        this.treatmentTime = treatmentTime;
        this.priority = priority;

        if(code == CODE_E){
            priority = 1;
        }

        patients.add(this);
    }

    public int getPatientNumber() {
        return patientNumber;
    }

    public char getCode() {
        return code;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
