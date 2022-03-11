import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {

    private Map<Aeroport, Set<Vol>> volsSortants;

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