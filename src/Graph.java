import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {

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
            HashMap<String, Aeroport> ensembleAeroport = new HashMap<String, Aeroport>();

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

        Aeroport aeroportCourant = new Aeroport();
        Aeroport aeroportDepart = new Aeroport();
        Aeroport aeroportCible = new Aeroport();

        //Recherche Aeroport Courant
        for(Aeroport aeroport : volsSortants.keySet()) {
            if(aeroport.getCodeIATA().equals(a1)) {
                aeroportCourant = aeroport;
                aeroportDepart = aeroport;
            }
            if(aeroport.getCodeIATA().equals(a2)) {
                aeroportCible = aeroport;
            }
        }

        //Algo
        boolean cibleAtteinte = false;
        while (aeroportCourant != null && !cibleAtteinte) {

            for (Vol vol : volsSortants.get(aeroportCourant)) {

                if (!aeroportsVisite.contains(vol.getAeroportDestination())) {
                    fileAeroport.push(vol.getAeroportDestination());
                    aeroportsVisite.add(vol.getAeroportDestination());
                    ensembleVol.put(vol.getAeroportDestination(), vol);

                    //Cible atteinte
                    if(aeroportCourant.equals(aeroportCible))
                        cibleAtteinte = true;
                }
            }
            if(!cibleAtteinte) {
                aeroportCourant = fileAeroport.poll();
            }
        }

        //Afficher vols
        ArrayList<Vol> vols = new ArrayList<>();
        boolean fin = false;
        while(!fin) {
            Vol vol = ensembleVol.get(aeroportCourant);
            vols.add(vol);
            if(vol.getAeroportSource().equals(aeroportDepart)) {
                fin = true;
            }
            aeroportCourant = vol.getAeroportSource();
        }
        int i = 0;
        for(Vol vol : vols) {
            i++;
            System.out.println(i + ". " + vol.toString());
        }
    }

    /*
    public void calculerItineraireMinimisantNombreVol(String a1, String a2) {
        ArrayDeque<Aeroport> fileAeroport = new ArrayDeque<Aeroport>();
        HashSet<Aeroport> aeroportsVisite = new HashSet<Aeroport>();
        HashMap<Aeroport, Vol> ensembleVol = new HashMap<Aeroport, Vol>();

        //Surement à changer
        Aeroport aeroportCourant = new Aeroport();
        Aeroport aeroportCible = new Aeroport();
        Aeroport aeroportPrec = new Aeroport();

        //Recherche Aeroport Source & Aeroport Cible
        for(Aeroport aeroport : volsSortants.keySet()) {
            if(aeroport.getCodeIATA().equals(a1)) {
                aeroportCourant = aeroport;
                fileAeroport.add(aeroportCourant);
            }
            if(aeroport.getCodeIATA().equals(a2)) {
                aeroportCible = aeroport;
            }
        }

        Set<Vol> vols;
        while(!fileAeroport.isEmpty() && !aeroportCourant.equals(aeroportCible)) {

            aeroportsVisite.add(aeroportCourant);
            vols = volsSortants.get(aeroportCourant);

            for(Vol vol : vols) {
                if(!aeroportsVisite.contains(vol.getAeroportDestination())) {
                    fileAeroport.add(vol.getAeroportDestination());
                    ensembleVol.put(vol.getAeroportSource(), vol);
                }
            }
            aeroportPrec = aeroportCourant;
            aeroportCourant = fileAeroport.poll();

        }

        HashSet<Vol> affichageVols = new HashSet<Vol>();
        aeroportCourant = aeroportPrec;
        boolean fin = false;
        while(!fin) {
            Vol vol = ensembleVol.get(aeroportCourant);
            if(vol == null) {
                fin = true;
            }
            else {
                aeroportCourant = vol.getAeroportSource();
                affichageVols.add(vol);
            }
        }
        System.out.println(affichageVols);
        for(Vol vol : affichageVols) {
            System.out.println(vol);
        }
        System.out.println("fin !");
    }
     */

    public void calculerItineraireMiniminantDistance(String s, String s2) {

    }

}