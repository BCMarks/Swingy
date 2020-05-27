package swingy.Models;

public class Artefact {
    private String type;
    private int quality;
    private String name = "Default name";

    public Artefact(String type, int quality) {
        //Mayhaps consider indicating quality with a naming system
        //[adjective] [adjective] [type] of [verb] each modifier gives +1 to +5on top of base +1 of type
        this.type = type;
        this.quality = quality;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }
}