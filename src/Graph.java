import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {

    HashSet<Aeroport> ensembleAeroport = new HashSet<Aeroport>();
    HashSet<Vol> ensembleVol = new HashSet<Vol>();

    public Graph(File aeroports, File vols){

        FileReader filereader = null;
        BufferedReader bufferedReader = null;

        try {

            filereader = new FileReader(aeroports);
            bufferedReader = new BufferedReader(filereader);

            String strCurrentLine;

            while ((strCurrentLine = bufferedReader.readLine()) != null) {
                System.out.println(strCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {

                if (bufferedReader != null)
                    bufferedReader.close();

                if (filereader != null)
                    filereader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void calculerItineraireMinimisantNombreVol(String s, String s2) {

    }

    public void calculerItineraireMiniminantDistance(String s, String s2) {

    }

}