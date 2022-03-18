public class Vol {
    private String compagnie;
    private Aeroport aeroportSource;
    private Aeroport aeroportDestination;




    public Vol(String compagnie, Aeroport aeroportSource, Aeroport aeroportDestination){
        this.compagnie=compagnie;
        this.aeroportSource = aeroportSource;
        this.aeroportDestination=aeroportDestination;


    }

    public Aeroport getAeroportSource() {
        return aeroportSource;
    }

    public Aeroport getAeroportDestination() {
        return aeroportDestination;
    }
}

