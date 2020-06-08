package swingy.Models;

import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List<Artefact> inventory;
    private Stats backpackStats;

    public Backpack()  {
        this.inventory = new ArrayList<Artefact>();
        setBackpackStats();
    }

    public Backpack(String weapon, String armour, String helm)  {
        this.inventory = new ArrayList<Artefact>();
        if (!weapon.equals("null"))
            this.inventory.add(new Artefact("Weapon", weapon));
        if (!armour.equals("null"))
            this.inventory.add(new Artefact("Armour", armour));
        if (!helm.equals("null"))
            this.inventory.add(new Artefact("Helm", helm));
        setBackpackStats();
    }

    public void acceptArtefact(String type, String name) {
        for (Artefact artefact : inventory) {
            if (artefact.getType().equals(type)) {
                inventory.remove(artefact);
                break;
            }
        }
        inventory.add(new Artefact(type, name));
        setBackpackStats();
    }

    public String getArtefactName(String type) {
        for (Artefact artefact : inventory) {
            if (artefact.getType().equals(type))
                return artefact.getName();
        }
        return "null";
    }

    public List<Artefact> getInventory() {
        return this.inventory;
    }

    public Stats getBackpackStats() {
        return this.backpackStats;
    }

    private void setBackpackStats() {
        int atk = 0;
        int def = 0;
        int hp = 0;
        int bonus = 1;
        for (Artefact artefact : inventory) {
            switch (artefact.getType()) {
                case "Weapon":
                    atk = artefact.getValue();
                    break;
                case "Armour":
                    def = artefact.getValue();
                    break;
                default:
                    hp = artefact.getValue();
            }
        }
        if (inventory.size() == 3 &&
            (inventory.get(0).getColour().equals(inventory.get(1).getColour()) &&
            inventory.get(0).getColour().equals(inventory.get(2).getColour())) &&
            (inventory.get(0).getCondition().equals(inventory.get(1).getCondition()) &&
            inventory.get(0).getCondition().equals(inventory.get(2).getCondition()))) {
            bonus = 3;
        }
        this.backpackStats = new Stats(bonus * atk, bonus * def, bonus * hp);
    }
}