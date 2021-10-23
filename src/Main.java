import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)throws FileNotFoundException {
        char patientType = 0;
        int timeOfPatient = 0;


        Scanner inputReader = new Scanner(System.in);
        boolean filework = false;
        Scanner fileInput = new Scanner(System.in);
        while (!(filework)) {
            System.out.println("Please enter input file name: ");
            String dataFileName = inputReader.nextLine();
            File dataFile = new File(dataFileName);
            if (dataFile.canRead()) {
                fileInput = new Scanner(dataFile);
                filework =true;
            }
        }


            String patientString = fileInput.nextLine();

            System.out.println("Simulation begins... ");


    }
}