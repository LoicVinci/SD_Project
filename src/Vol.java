public class Vol {
    private String compagnie;
    private String codeIATASource;
    private String codeIATADestination;




    public Vol(String compagnie, String codeIATASource,String codeIATADestination){
        this.compagnie=compagnie;
        this.codeIATADestination=codeIATADestination;
        this.codeIATASource=codeIATASource;


    }

    public String getCodeIATASource() {
        return codeIATASource;
    }
}

