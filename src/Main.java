import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)throws FileNotFoundException {
        Scanner inputReader = new Scanner(System.in);
        boolean filework = false;
        Scanner fileInput = new Scanner(System.in); //needs to be set so java is happy
        while (!(filework)) {
            System.out.println("Please enter input file name: ");
            String dataFileName = inputReader.nextLine();
            File dataFile = new File(dataFileName);
            if (dataFile.canRead()) {
                fileInput = new Scanner(dataFile);
                filework =true;
            }
        }
            char patientType = ' ';
            int timeOfPatient = 0;
            String patientString = fileInput.nextLine();

            System.out.println("Simulation begins... ");


    }
}