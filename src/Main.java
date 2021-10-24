import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
/*
the class takes the input from the text file that we have created

 */
public class Main {
    public static void main(String[] args)throws FileNotFoundException {
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
                filework =true;
            } else System.out.println("ERROR INVAILD INPUT FILE");
        }
        System.out.println("Simulation begins... :");

        String patientString = fileInput.nextLine();
        String [] linebreakdownArray = patientString.split(" ",3);
        int timeOfPatient = Integer.parseInt(linebreakdownArray[0]);
        System.out.println(timeOfPatient);
        char patientType = linebreakdownArray[1].charAt(0);
        int processingTime =Integer.parseInt(linebreakdownArray[2]);

        ArrayList<Character> patientTypeArray = new ArrayList<>();
        ArrayList<Integer> processingTimeArray = new ArrayList<>();

        if(timeOfPatient == Clock.getTime()){
            patientTypeArray.add(patientType);
            processingTimeArray.add(processingTime);

            Simulation.doOneClockCycle(patientTypeArray, processingTimeArray);
        }
        Clock.advanceClock();


        while (fileInput.hasNext()) {
            System.out.println(Clock.getTime());
            patientTypeArray = new ArrayList<>();
            processingTimeArray = new ArrayList<>();

            while (Clock.getTime() == timeOfPatient){

                patientString = fileInput.nextLine();
                linebreakdownArray = patientString.split(" ",3);
                timeOfPatient = Integer.parseInt(linebreakdownArray[0]);
                patientType = linebreakdownArray[1].charAt(0);
                processingTime =Integer.parseInt(linebreakdownArray[2]);

                patientTypeArray.add(patientType);
                processingTimeArray.add(processingTime);
            }

            Simulation.doOneClockCycle(patientTypeArray, processingTimeArray);

            Clock.advanceClock();
        }
    }
}