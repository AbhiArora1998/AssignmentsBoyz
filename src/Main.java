import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inputReader = new Scanner(System.in);
        System.out.println("Please enter input file name: ");
        String dataFileName = inputReader.nextLine();
        File dataFile = new File(dataFileName);

        if (dataFile.canRead()) {
            try {
                Scanner in = new Scanner(dataFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        int patientStartNum = 28064212;
        char patientType = ' ';
        int timeOfPatient =0;
        String patientString = in.nextLine();

        System.out.println("Simulation begins... ");

    }
}