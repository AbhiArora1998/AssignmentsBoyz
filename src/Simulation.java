import java.util.ArrayList;

public final class Simulation {

    public static final int NUMBER_OF_TREATMENT_ROOMS = 3;

    private static LinearQueue assessmentQueue  = new LinearQueue();
    private static WaitingQueue waitingQueue = new WaitingQueue();
    private static TreatmentRoom[] treatmentRooms = new TreatmentRoom[NUMBER_OF_TREATMENT_ROOMS];; //TODO: treatment rooms taking care of themselves.

    private Simulation(){
        /* empty */
    }

    public static void doOneClockCycle(ArrayList<Character> patientType, ArrayList<Integer> processingTime){

        //create arrivals and add to corresponding queues
        for (int i = 0; i < patientType.size(); i++) {
            ArrivalEvent arrivalEvent = new ArrivalEvent(patientType.get(i) ,processingTime.get(i) );

            if(patientType.get(i) == Patient.CODE_W){
                assessmentQueue.add(arrivalEvent.getPatient());
            } else {
                waitingQueue.add(arrivalEvent.getPatient());
            }

        }

        System.out.println(assessmentQueue.toString());
        System.out.println(waitingQueue.toString());
        //Assess patients in queue.

    }
}
