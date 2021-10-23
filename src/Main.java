import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;
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
        char patientType = linebreakdownArray[1].charAt(0);
        int durationOfPatient =Integer.parseInt(linebreakdownArray[2]);
        int time = 0;
        while ((time < 1000)&&(fileInput.hasNext())) {

            while (time == timeOfPatient){

                patientString = fileInput.nextLine();
                linebreakdownArray = patientString.split(" ",3);
                timeOfPatient = Integer.parseInt(linebreakdownArray[0]);
                patientType = linebreakdownArray[1].charAt(0);
                durationOfPatient =Integer.parseInt(linebreakdownArray[2]);
            }
            time++;
        }
    }
    /*
    public static void getValidConsonant(String patientString)
    {
        while (in.hasNextLine())
        {
            String consonantStr = in.nextLine();
            if (isConsonantDec(consonantStr)&&(numOfConsonant(consonantStr)>=consonantNum))
                out.println(consonantStr);
        }*/
    //File dataFile = new File(dataFileName);
    //canExecute()
    //URL url = Main.class.getResource("data1.txt");
    //System.out.println(url.getPath());
    //Path path = Path.of(url.getPath());
    //File dataFile = new File(url.getPath());
    //System.out.println(path);
    //File dataFile = new File("C:\\Users\\emile\\Desktop\\School\\Sept 2021\\CPSC 300-3 Software Engineering\\Assignment\\1\\code\\src\\data1.txt");
    //System.out.println(dataFile2.getAbsolutePath());
    //File dataFile = new File("data1.txt");
    /*for(String str :  returnedArray2){
                timeOfPatient = Integer.parseInt(str);
                System.out.println(" Output2 : "+str);
            } */
}