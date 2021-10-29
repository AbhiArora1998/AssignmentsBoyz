package queues;

import simulation.Patient;

public class TreatmentRoom {
    private Patient[] patients = new Patient[3] ;
    WaitingQueue waitingQueue;

    private boolean roomIsFree1;
    private boolean roomIsFree2;
    private boolean roomIsFree3;
    private boolean patient1IsDone;
    private boolean patient2IsDone;
    private boolean patient3IsDone;
    private boolean[] patientIsDone = {false,false,false};


    public TreatmentRoom(){

        this.roomIsFree1 = true;
        this.roomIsFree2 = true;
        this.roomIsFree3 = true;

         this.patient1IsDone= true;
       this.patient2IsDone=true;
         this.patient3IsDone=true;

    }

    public void enterTreatmentRoom(Patient patient){
        if(roomIsFree1){
            patients[0] = patient;
            patient1IsDone = false;


        }else if(roomIsFree2){

            patients[1] = patient;
            patient2IsDone = false;
        }else if(roomIsFree3){
            patients[2] = patient;
            patient3IsDone = false;
        }else {
            System.out.println("The rooms are busy please wait");
        }
    }

    public int roomCheck(){
        if(roomIsFree1){

            return 1;
        }else if(roomIsFree2){

            return 2;

        }else if(roomIsFree3){

            return 3;
        }else {
            System.out.println("The rooms are busy please wait");
        }
        return 0;
    }

    public void patientTreated(Patient[] patients){

    }

    public void setPatient1IsDone(boolean patient1IsDone) {
        this.patient1IsDone = patient1IsDone;
    }

    public void setPatient2IsDone(boolean patient2IsDone) {
        this.patient2IsDone = patient2IsDone;
    }

    public void setPatient3IsDone(boolean patient3IsDone) {
        this.patient3IsDone = patient3IsDone;
    }

    public void setPatientIsDone(int patientNum, boolean patient3IsDone) {
        this.patientIsDone[patientNum] = patientIsDone[patientNum];
    }

    public boolean getPatientIsDone(int patientNum) {
        return patientIsDone[patientNum];
    }

    public Patient setRoomIsFree1() {
        Patient temp = patients[0];
        patients[0] = null;
        this.roomIsFree1 = true;
        return temp;
    }

    public Patient setRoomIsFree2() {
       Patient temp = patients[1];
        patients[1] = null;
        this.roomIsFree2 = true;
        return temp;
    }

    public Patient setRoomIsFree3() {
        Patient temp = patients[2];
        patients[2] = null;
        this.roomIsFree3 = true;
        return temp;
    }
    public Patient setRoomIsFree(int room) {
        Patient temp = patients[room];
        patients[room] = null;
        this.roomIsFree3 = true;
        return temp;
    }

    public Patient[] getPatients() {
        return patients;
    }

    public void waitingQueueRemove(){
        waitingQueue.remove();
    }
}
