import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.net.URL;

public class Main {
    public static void main(String[] args)throws FileNotFoundException {
        Scanner inputReader = new Scanner(System.in);
        boolean filework = false;
        Scanner fileInput = new Scanner(System.in); //needs to be set so java is happy
        while (!(filework)) {
            System.out.println("Please enter input file name: ");
            String dataFileName = inputReader.nextLine();
            //File dataFile = new File(dataFileName);
            System.out.println(dataFileName); //canExecute()
            URL url = Main.class.getResource("data1.txt");
            File dataFile = new File(url.getPath()); //url.getPath()
            System.out.println(url.getPath());
            if (dataFile.canRead()) {
                fileInput = new Scanner(dataFile);
                filework =true;
            } else System.out.println("ERROR INVAILD INPUT FILE");
        }
        while (fileInput.hasNext()) {
            char patientType = ' ';
            int timeOfPatient = 0;
            String patientString = fileInput.nextLine();
            System.out.println("Simulation begins... ");
        }

    }
}