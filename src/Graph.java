import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Graph{
    public Graph(File aeroports, File vols){

    }
    private static final String FILENAME = "C:\\Users\\quent\\Downloads\\Projet-20220311\\vols.txt";
    private static final String FILENAME2 = "C:\\Users\\quent\\Downloads\\Projet-20220311\\aeroports.txt";


    public static void main(String[] args) {

        BufferedReader bufferedreader = null;
        FileReader filereader = null;
        FileReader fileReader2 = null;
        BufferedReader bufferedReader2 = null;


        try {

            filereader = new FileReader(FILENAME);
            fileReader2 = new FileReader((FILENAME2));
            bufferedreader = new BufferedReader(filereader);
            bufferedReader2= new BufferedReader((fileReader2));


            String strCurrentLine;

            while ((strCurrentLine = bufferedreader.readLine()) != null) {
                System.out.println(strCurrentLine);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bufferedreader != null)
                    bufferedreader.close();

                if (filereader != null)
                    filereader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}