package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Fall 2021
 *
 * This finds and reads a given text file
 *
 * @author The Boyz
 * @version 1
 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner inputReader = new Scanner(System.in);
        boolean filework = false;
        Scanner fileInput = new Scanner(System.in); //needs to be set so java is happy

        while (!(filework)) {
            System.out.println("Please enter input file name: ");
            String dataFileName = inputReader.nextLine(); //get file name
            File dataFile = new File(dataFileName);
            /*
            checks to see if the file exists and readable then
            saves the input in the fileInput
             */
            if (dataFile.canRead()) {
                fileInput = new Scanner(dataFile);
                filework = true;
            } else System.out.println("ERROR TEXT FILE COULD NOT BE FOUND");
        }
        System.out.println("Simulation begins...\n");

        String patientString = fileInput.nextLine();
        String[] linebreakdownArray = patientString.split(" ", 3);// splits the string whenever space  is provided
        int arrivalTime = Integer.parseInt(linebreakdownArray[0]);
        char patientType;
        int processingTime;

        ArrayList<Character> patientTypeArray;
        ArrayList<Integer> processingTimeArray;

        boolean run = true;
        boolean scanFile = true;
        while (run) {

            if (Simulation.assessmentQueue.isEmpty() &&
                    Simulation.waitingQueue.isEmpty() &&
                    !Simulation.treatmentRooms.anyRoomBeingUsed() &&
                    !scanFile){
                run = false;
            }
            patientTypeArray = new ArrayList<>();
            processingTimeArray = new ArrayList<>();

            while (Clock.getCurrentClockTime() == arrivalTime && scanFile) {
                if(!fileInput.hasNextLine()){// if the next line does not exit then it stops reading the textFile
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
        System.out.println("\n...All events complete. Final Summary:\n");
        String format = " %10d  %10d  %10d  %10d  %10d  %10s  %10d %n";
        System.out.format("    Patient    Priority     Arrival  Assessment   Treatment   Departure     Waiting %n");
        System.out.format("     Number                    Time        Time    Required        Time        Time %n");
        System.out.format("------------------------------------------------------------------------------------%n");
        float sum= 0;
        Patient.patients.sort(Comparator.comparing(Patient::getPriority)); //This organizes the patient based on priority
        for (Patient patient: Patient.patients) {
            System.out.format(format,
                    patient.getPatientNumber(),
                    patient.getPriority(),
                    patient.getArrivalTime(),
                    patient.getAssessmentTime(),
                    patient.getTreatmentTime(),
                    patient.getDepartureTime(),
                    patient.getWaitingTime());
            sum += patient.getWaitingTime();
        }
        System.out.println("\n Patients seen in total: " + Patient.patients.size());
        System.out.println(" Average waiting time per patient: "+ sum/(float)Patient.patients.size());
        System.out.println("\n----");
    }
}
