package swingy.Models;

import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List<Artefact> inventory;

    public Backpack()  {
        this.inventory = new ArrayList<Artefact>();
    }

    public Backpack(String weapon, String armour, String helm)  {
        this.inventory = new ArrayList<Artefact>();
        if (weapon != null)
            this.inventory.add(new Artefact("Weapon", 5));
        if (armour != null)
            this.inventory.add(new Artefact("Armour", 5));
        if (helm != null)
            this.inventory.add(new Artefact("Helm", 5));
    }

    public void acceptArtefact(String type, int quality) {
        for (Artefact artefact : inventory) {
            if (artefact.getType().equals(type)) {
                inventory.remove(artefact);
                break;
            }
        }
        inventory.add(new Artefact(type, quality));
    }

    public String getArtefactName(String type) {
        for (Artefact artefact : inventory) {
            if(artefact.getType().equals(type))
                return artefact.getName();
        }
        return null;
    }

    public List<Artefact> getInventory() {
        return this.inventory;
    }
}