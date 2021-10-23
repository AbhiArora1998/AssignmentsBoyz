public final class Simulation {

    public static final int NUMBER_OF_TREATMENT_ROOMS = 3;

    private static LinearQueue assessmentQueue;
    private static WaitingQueue waitingQueue;
    private static TreatmentRoom[] treatmentRooms; //TODO: treatment rooms taking care of themselves.

    public Simulation(){
        treatmentRooms = new TreatmentRoom[NUMBER_OF_TREATMENT_ROOMS];
        waitingQueue = new WaitingQueue();
        assessmentQueue = new LinearQueue();
    }

    public void doOneClockCycle(char patientType[], int processingTime[]){

        //create arrivals and add to corresponding queues
        for (int i = 0; i < patientType.length; i++) {
            if(patientType[i] == Patient.CODE_W){
                assessmentQueue.add((new ArrivalEvent(patientType[i],processingTime[i])).getPatient());
            } else {
                waitingQueue.add((new ArrivalEvent(patientType[i],processingTime[i])).getPatient());
            }

        }

        //Assess patients in queue.

    }
}
