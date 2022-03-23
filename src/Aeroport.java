public class Aeroport {

    private String codeIATA;
    private String nom;
    private String ville;
    private String pays;
    private Double longitude;
    private Double latitude;

    public Aeroport() {
    }

    public Aeroport(String codeIATA, String nom, String ville, String pays, Double longitude, Double latitude){
        this.codeIATA= codeIATA;
        this.latitude= latitude;
        this.longitude= longitude;
        this.ville= ville;
        this.pays = pays;
        this.nom= nom;
    }

    public String getCodeIATA() {
        return codeIATA;
    }
    public double getCLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Aeroport{" +
            "codeIATA='" + codeIATA + '\'' +
            ", nom='" + nom + '\'' +
            ", ville='" + ville + '\'' +
            ", pays='" + pays + '\'' +
            ", longitude=" + longitude +
            ", latitude=" + latitude +
            '}';
    }
}
