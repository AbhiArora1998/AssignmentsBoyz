import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inputReader = new Scanner(System.in);
        System.out.println("Please enter input file name: ");
        String dataFileName = inputReader.nextLine();
        File dataFile = new File(dataFileName);

        int patientStartNum = 28064212;
        System.out.println("Simulation begins... ");

    }
}