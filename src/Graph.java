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

        //Surement à changer
        Aeroport aeroportActuel = new Aeroport();
        Aeroport aeroportSource = new Aeroport();
        Aeroport aeroportCible = new Aeroport();

        //Recherche Aeroport Source & Aeroport Cible
        for(Aeroport aeroport : volsSortants.keySet()) {
            if(aeroport.getCodeIATA().equals(a1)) {
                aeroportSource = aeroport;
                aeroportActuel = aeroport;
                fileAeroport.add(aeroportActuel);
            }
            if(aeroport.getCodeIATA().equals(a2)) {
                aeroportCible = aeroport;
            }
        }
        //sert a garder le dernier aeroport juste avant celui cible
        Aeroport aerorportPrecedent= aeroportActuel;
        Set<Vol> vols;
        while(!fileAeroport.isEmpty() && !aeroportActuel.equals(aeroportCible)) {

            aeroportsVisite.add(aeroportActuel);
            vols = volsSortants.get(aeroportActuel);

            for(Vol vol : vols) {
                if(!aeroportsVisite.contains(vol.getAeroportDestination())) {
                    fileAeroport.add(vol.getAeroportDestination());
                    ensembleVol.put(vol.getAeroportSource(), vol);
                }
            }
            aerorportPrecedent=aeroportActuel;
            aeroportActuel = fileAeroport.poll();

        }
        int nbreDaeroports=0;
        HashSet<Vol> affichageVols = new HashSet<Vol>();
        while(!aeroportSource.equals(aeroportActuel)) {
            //aeroportActuel = ;

            Set<Vol> volsPrecedent = volsSortants.get(aeroportActuel );
            for (Vol vol: volsPrecedent
            ) {
                if(vol.getAeroportDestination().equals(aeroportActuel)) {
                    System.out.println(vol);

                }
                break;

            }

            affichageVols.add(ensembleVol.get(aeroportActuel));
        }

        for(int i = 0; i < affichageVols.size(); i++) {
            //System.out.println(affichageVols.g);
        }

    }

    public void calculerItineraireMiniminantDistance(String s, String s2) {
        int nombreAeroports = volsSortants.size();
        HashMap<Aeroport, Double> aeroportDistance = new HashMap<>();
        HashMap<Aeroport, Double> aeroportDistanceDef = new HashMap<>();
        Aeroport aeroportActuel = new Aeroport();
        ArrayList<Aeroport> visités = new ArrayList<>();
        int cpt=0;


        Aeroport aeroportMin = new Aeroport();

        Aeroport source = new Aeroport();
        System.out.println(source.getCodeIATA());

        Aeroport destination = new Aeroport();

        while (source.getCodeIATA() == null || destination.getCodeIATA() == null) {
            System.out.println("ok");

            for (Aeroport aeroport : volsSortants.keySet()
            ) {
                if (aeroport.getCodeIATA().equals(s)) {

                    source = aeroport;
                    System.out.println(source.getCodeIATA());


                }
                if (aeroport.getCodeIATA().equals(s2)) {

                    destination = aeroport;
                    System.out.println(destination.getCodeIATA());

                }
            }
        }



        aeroportActuel=source;

        Set<Vol> volsAeroportActuel = volsSortants.get(aeroportActuel);
        Double distance ;
        double distanceMin=999999999;
        double toAdd=0;
        visités.add(aeroportActuel);
        Aeroport volDestination= new Aeroport();
        while(!aeroportActuel.equals(destination)){



                distanceMin=999999999;
                //remplissage du tableau temporaire
                for (Vol vol: volsAeroportActuel
                ) {
                   volDestination= vol.getAeroportDestination();
                    distance=Util.distance(aeroportActuel.getCLatitude(),aeroportActuel.getLongitude(),
                        volDestination.getCLatitude(),volDestination.getLongitude())+toAdd;//calculate distance between get
                    aeroportDistance.put(volDestination,distance);

                    //on trouve quel aeroport sera a la moins longue distance du precedent;
                    if(distance<distanceMin)distanceMin=distance;
                    aeroportMin=vol.getAeroportDestination();

                }

                aeroportActuel=aeroportMin;
                System.out.println(aeroportActuel.getCodeIATA());

                visités.add(aeroportMin);
                toAdd=distanceMin;

                //remplissage du tableau definitif;
                aeroportDistanceDef.put(aeroportMin,distanceMin);

                volsAeroportActuel = volsSortants.get(aeroportActuel);
                cpt++;




            }
        System.out.println("boucle finie");


        System.out.println(aeroportDistanceDef.get(destination));
    }



}