public class TreatmentRoom {
    private Patient currentPatient;

    public TreatmentRoom(){
        /* empty */
    }

    public void setCurrentPatient(Patient patient){
        currentPatient = patient;
    }

    public Patient getCurrentPatient(){
        return currentPatient;
    }
}
