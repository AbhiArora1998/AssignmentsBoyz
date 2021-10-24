package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
the class takes the input from the text file that we have created

 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner inputReader = new Scanner(System.in);
        boolean filework = false;
        Scanner fileInput = new Scanner(System.in); //needs to be set so javaa is happy
        while (!(filework)) {
            System.out.println("Please enter input file name: ");
            String dataFileName = inputReader.nextLine(); //get file name
            File dataFile = new File(dataFileName);
            //System.out.println(dataFile.getAbsolutePath()); //file location
            if (dataFile.canRead()) {
                fileInput = new Scanner(dataFile);
                filework = true;
            } else System.out.println("ERROR INVAILD INPUT FILE");
        }
        System.out.println("simulation.Simulation begins... :");

        String patientString = fileInput.nextLine();
        String[] linebreakdownArray = patientString.split(" ", 3);
        int arrivalTime = Integer.parseInt(linebreakdownArray[0]);
        char patientType;
        int processingTime;

        ArrayList<Character> patientTypeArray;
        ArrayList<Integer> processingTimeArray;

        boolean run = true;
        boolean scanFile = true;
        while (run) {

//            if (Simulation.getAssessmentQueue().isEmpty() &&
//                    Simulation.getWaitingQueue().isEmpty() &&
//                    !scanFile){
//                run = false;
//            }

            //TODO: replace following if-statement with above once waitingQueue and treatmentEvents work
            if (Simulation.getAssessmentQueue().isEmpty() &&
                    !scanFile){
                run = false;
            }


            patientTypeArray = new ArrayList<>();
            processingTimeArray = new ArrayList<>();

            //Debug:
            //System.out.println("CLOCK: " + Clock.getCurrentClockTime());
            //End Debug

            while (Clock.getCurrentClockTime() == arrivalTime && scanFile) {
                if(!fileInput.hasNextLine()){
                    scanFile = false;
                }

                linebreakdownArray = patientString.split(" ", 3);
                patientType = linebreakdownArray[1].charAt(0);
                processingTime = Integer.parseInt(linebreakdownArray[2]);

                patientTypeArray.add(patientType);
                processingTimeArray.add(processingTime);

                if (scanFile) {
                    patientString = fileInput.nextLine();
                    linebreakdownArray = patientString.split(" ", 3);
                    arrivalTime = Integer.parseInt(linebreakdownArray[0]);
                } else {
                    break;
                }

            }

            Simulation.doOneClockCycle(patientTypeArray, processingTimeArray);

            Clock.advanceClock();

        }
        //TODO: Waiting queue no working properly. Some patients missing?
        //System.out.println(Simulation.getWaitingQueue().toString());

        //TODO: finish adding missing numbers into summary table.
        System.out.println("\n...All events complete. Final Summary:\n");

        String format = " %10d  %10d  %10d  %10d  %10d  %10s  %10d %n";

        System.out.format("    Patient    Priority     Arrival  Assessment   Treatment   Departure     Waiting %n");
        System.out.format("     Number                    Time        Time    Required        Time        Time %n");
        System.out.format("------------------------------------------------------------------------------------%n");
        float sum= 0;
        for (Patient patient: Patient.patients) {
            System.out.format(format,
                    patient.getPatientNumber(),
                    patient.getPriority(),
                    patient.getArrivalTime(),
                    patient.getAssessmentTime(),
                    patient.getTreatmentTime(),
                    "???",
                    patient.getWaitingTime());
            sum += patient.getWaitingTime();
        }

        System.out.println("\n Patients seen in total: " + Patient.patients.size());

        System.out.println(" Average waiting time per patient: "+ sum/(float)Patient.patients.size());

        System.out.println("\n----");
    }
}