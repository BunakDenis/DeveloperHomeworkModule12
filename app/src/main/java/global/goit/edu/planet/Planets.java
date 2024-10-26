package global.goit.edu.planet;

public enum Planets {

    MER("Mercury"),
    EAR("Earth"),
    VEN ("Venus"),
    MARS ("Mars"),
    JUP ("Jupiter"),
    SAT ("Saturn"),
    URAN ("Uranus"),
    NEP ("Neptune"),
    PLUT("Pluto");

    public final String name;

    private Planets(String name) {
        this.name = name;
    }
}
