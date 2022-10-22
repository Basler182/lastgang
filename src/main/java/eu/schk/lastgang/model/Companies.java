package eu.schk.lastgang.model;

public enum Companies {

    FERTIGGERICHTE_IZMIR_UEBEL_SE("Fertiggerichte Izmir Übel SE",
            "D:\\git\\emilm\\lastgang\\src\\main\\resources\\eu\\schk\\lastgang\\csv\\loadprofile_1.csv",
            875.0),
    J_SANDLER_SONNE_BETONWERKE_AG("J. Sandler & Söhne Betonwerke AG",
            "D:\\git\\emilm\\lastgang\\src\\main\\resources\\eu\\schk\\lastgang\\csv\\loadprofile_2.csv",
            1289.73),
    JOSEF_RISS_TEXTILFASERN_KG("Josef Riss Textilfasern KG",
            "D:\\git\\emilm\\lastgang\\src\\main\\resources\\eu\\schk\\lastgang\\csv\\loadprofile_3.csv",
            14137.2),
    KRATZER_BESCHICHTUNGEN_SE("Kratzer Beschichtungen SE",
            "D:\\git\\emilm\\lastgang\\src\\main\\resources\\eu\\schk\\lastgang\\csv\\loadprofile_4.csv",
            1574.8
    ),
    MAYERMILCH_GENUSSMOLKEREI_AG("Mayermilch Genussmolkerei AG",
            "D:\\git\\emilm\\lastgang\\src\\main\\resources\\eu\\schk\\lastgang\\csv\\loadprofile_5.csv",
            1404.67
    ),
    METALLWERKE_RADAU_GMBH("Metallwerke Radau GmbH & Co. KG, Werk 1",
            "D:\\git\\emilm\\lastgang\\src\\main\\resources\\eu\\schk\\lastgang\\csv\\loadprofile_6.csv",
            4192.0
    ),
    METALLWERKE_RADAU_GMBH2("Metallwerke Radau GmbH & Co. KG, Werk 2",
            "D:\\git\\emilm\\lastgang\\src\\main\\resources\\eu\\schk\\lastgang\\csv\\loadprofile_7.csv",
            2637.04
    ),
    MUELLER_RECYCLING("Firma Müller Recycling GmbH",
            "D:\\git\\emilm\\lastgang\\src\\main\\resources\\eu\\schk\\lastgang\\csv\\loadprofile_8.csv",
            898.83
    ),
    WUCHER_BAUELEMENTE("Wucher Bauelemente KG",
            "D:\\git\\emilm\\lastgang\\src\\main\\resources\\eu\\schk\\lastgang\\csv\\loadprofile_9.csv",
            1072.01
    );

    private final String name;
    private final String path;
    private final Double threshold;

    Companies(String name, String path, Double threshold) {
        this.name = name;
        this.path = path;
        this.threshold = threshold;
    }


    public static Companies getCompanyByName(String name) {
        for (Companies company : Companies.values()) {
            if (company.getName().equals(name)) {
                return company;
            }
        }
        throw new IllegalArgumentException("No company with name " + name + " found.");
    }


    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Double getThreshold() {
        return threshold;
    }
}
