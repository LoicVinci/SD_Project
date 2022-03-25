import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public class Graph {

    private HashMap<String, Aeroport> ensembleAeroport = new HashMap<String, Aeroport>();
    private Map<Aeroport, Set<Vol>> volsSortants = new HashMap<Aeroport, Set<Vol>>();

    public Graph(File aeroports, File vols){

        FileReader filereader = null;
        BufferedReader bufferedReader = null;
        FileReader filereader2 = null;
        BufferedReader bufferedReader2 = null;

        try {

            filereader = new FileReader(aeroports);
            bufferedReader = new BufferedReader(filereader);
            filereader2 = new FileReader(vols);
            bufferedReader2 = new BufferedReader(filereader2);

            String strCurrentLine;

            while ((strCurrentLine = bufferedReader.readLine()) != null) {
                String[] attributsAeroports = strCurrentLine.split(",", -1);
                Aeroport aeroport = new Aeroport(attributsAeroports[0],attributsAeroports[1],
                    attributsAeroports[2],attributsAeroports[3],
                    Double.parseDouble(attributsAeroports[4]),Double.parseDouble(attributsAeroports[5]));
                ensembleAeroport.put(aeroport.getCodeIATA(), aeroport);
                Set<Vol> ensembleVols = new HashSet<>();
                volsSortants.put(aeroport, ensembleVols);
            }
            while((strCurrentLine = bufferedReader2.readLine()) != null) {
                String[] attributsVols = strCurrentLine.split(",", -1);
                Vol vol = new Vol(attributsVols[0], ensembleAeroport.get(attributsVols[1]),
                    ensembleAeroport.get(attributsVols[2]));
                for(Aeroport aeroport : volsSortants.keySet()) {
                    if(aeroport.equals(vol.getAeroportSource())) {
                        volsSortants.get(aeroport).add(vol);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {

                if (bufferedReader != null)
                    bufferedReader.close();

                if (filereader != null)
                    filereader.close();

                if (bufferedReader2 != null)
                    bufferedReader2.close();

                if (filereader2 != null)
                    filereader2.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void calculerItineraireMinimisantNombreVol(String a1, String a2) {
        ArrayDeque<Aeroport> fileAeroport = new ArrayDeque<Aeroport>();
        HashSet<Aeroport> aeroportsVisite = new HashSet<Aeroport>();
        HashMap<Aeroport, Vol> ensembleVol = new HashMap<Aeroport, Vol>();

        Aeroport aeroportCourant = ensembleAeroport.get(a1);

        fileAeroport.addLast(aeroportCourant);
        aeroportsVisite.add(aeroportCourant);

        //Algorythme BFS
        boolean cibleAtteinte = false;
        while (aeroportCourant != null && !cibleAtteinte) {
            aeroportCourant = fileAeroport.removeFirst();
            for (Vol vol : volsSortants.get(aeroportCourant)) {
                if (!aeroportsVisite.contains(vol.getAeroportDestination())) {
                    fileAeroport.addLast(vol.getAeroportDestination());
                    aeroportsVisite.add(vol.getAeroportDestination());
                    ensembleVol.put(vol.getAeroportDestination(), vol);
                    cibleAtteinte = aeroportCourant.equals(ensembleAeroport.get(a2));
                }
            }
        }

        //Chemin Inverse
        ArrayList<Vol> vols = new ArrayList<>();
        boolean fin = false;
        while(!fin) {
            Vol vol = ensembleVol.get(aeroportCourant);
            vols.add(vol);
            if(vol.getAeroportSource().equals(ensembleAeroport.get(a1))) {
                fin = true;
            }
            aeroportCourant = vol.getAeroportSource();
        }

        //Affichage des vols
        int i = 0;
        for(Vol vol : vols) {
            i++;
            System.out.println(i + ". " + vol.toString());
        }
    }

    public void calculerItineraireMiniminantDistance(String a1, String a2) {

        HashMap<Aeroport, Double> aeroportProvisoire = new HashMap<>();
        HashMap<Aeroport, Double> aeroportDefinits = new HashMap<>();
        HashMap<Aeroport, Vol> ensembleVol = new HashMap<>();

        Aeroport aeroportDepart = ensembleAeroport.get(a1);
        Aeroport aeroportCible = ensembleAeroport.get(a2);
        Aeroport aeroportActuel = aeroportDepart;

        aeroportProvisoire.put(aeroportDepart, 0.0);

        while(!aeroportProvisoire.isEmpty() && !aeroportDefinits.containsKey(aeroportCible)) {

            //selectionner min value dans provisoire
            Double min = Collections.min(aeroportProvisoire.values());
            for (Aeroport aeroport : aeroportProvisoire.keySet()) {
                if (aeroportProvisoire.get(aeroport) == min) {
                    aeroportActuel = aeroport;
                    break;
                }
            }

            //ajouter dans definitives
            aeroportDefinits.put(aeroportActuel, aeroportProvisoire.get(aeroportActuel));
            aeroportProvisoire.remove(aeroportActuel);

            //calculer etiquette provisoire
            for (Vol vol : volsSortants.get(aeroportActuel)) {
                Aeroport aeroportSource = vol.getAeroportSource();
                Aeroport aeroportDestination = vol.getAeroportDestination();
                Double distance =
                    Util.distance(aeroportSource.getCLatitude(), aeroportSource.getLongitude(),
                        aeroportDestination.getCLatitude(), aeroportDestination.getLongitude());
                distance += aeroportDefinits.get(aeroportActuel);
                if(!aeroportDefinits.containsKey(aeroportDestination)) {
                    if(aeroportProvisoire.get(aeroportDestination) == null) {
                        aeroportProvisoire.put(aeroportDestination, distance);
                    } else if(distance < aeroportProvisoire.get(aeroportDestination)) {
                        aeroportProvisoire.put(aeroportDestination, distance);
                    }
                }
            }
        }
        System.out.println(aeroportDefinits.get(aeroportCible));
    }

}