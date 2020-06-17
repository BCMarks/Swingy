package swingy.Models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import swingy.Resources.Artefact.*;

public class Artefact {
    @NotEmpty
    private String type;
    @NotEmpty
    private String name;
    @NotEmpty
    private String condition;
    @NotEmpty
    private String colour;
    @NotEmpty
    private String piece;
    @Min(4)
    private int value;

    public Artefact(String type) {
        double typeRoll = Math.random();
        double tierColourRoll = Math.random();
        double tierConditionRoll = Math.random();
        int value = 0;
        int multiplier;
        switch (type) {
            case "Weapon":
                if (typeRoll <= 0.6) {
                    piece = artefactTypeWeapon.Paper.toString();
                    multiplier = 2;
                } else if (typeRoll <= 0.9) {
                    piece = artefactTypeWeapon.Scissors.toString();
                    multiplier = 3;
                } else {
                    piece = artefactTypeWeapon.Rock.toString();
                    multiplier = 4;
                }
                break;
            case "Armour":
                if (typeRoll <= 0.6) {
                    piece = artefactTypeArmour.Shirt.toString();
                    multiplier = 2;
                } else if (typeRoll <= 0.9) {
                    piece = artefactTypeArmour.Jacket.toString();
                    multiplier = 3;
                } else {
                    piece = artefactTypeArmour.Onesie.toString();
                    multiplier = 4;
                }
                break;
            default:
                if (typeRoll <= 0.6) {
                    piece = artefactTypeHelm.Wig.toString();
                    multiplier = 2;
                } else if (typeRoll <= 0.9) {
                    piece = artefactTypeHelm.Bandanna.toString();
                    multiplier = 3;
                } else {
                    piece = artefactTypeHelm.Beanie.toString();
                    multiplier = 4;
                }
        }

        if (tierConditionRoll <= 0.4) {
            condition = artefactTierCondition.Damaged.toString();
            value += 1;
        } else if (tierConditionRoll <= 0.7) {
            condition = artefactTierCondition.Standard.toString();
            value += 2;
        } else if (tierConditionRoll <= 0.85) {
            condition = artefactTierCondition.Heroic.toString();
            value += 3;
        } else if (tierConditionRoll <= 0.95) {
            condition = artefactTierCondition.Enchanted.toString();
            value += 4;
        } else {
            condition = artefactTierCondition.Legendary.toString();
            value += 5;
        }

        if (tierColourRoll <= 0.4) {
            colour = artefactTierColour.Red.toString();
            value += 1;
        } else if (tierConditionRoll <= 0.7) {
            colour = artefactTierColour.Orange.toString();
            value += 2;
        } else if (tierConditionRoll <= 0.85) {
            colour = artefactTierColour.Yellow.toString();
            value += 3;
        } else if (tierConditionRoll <= 0.95) {
            colour = artefactTierColour.Green.toString();
            value += 4;
        } else {
            colour = artefactTierColour.Blue.toString();
            value += 5;
        }

        this.type = type;
        this.name = condition + " " + colour + " " + piece;
        this.value = value * multiplier;
    }

    public Artefact(String type, String name) {
        String[] artefactSplit = name.split(" ");
        int value = 0;
        int multiplier;

        switch (artefactSplit[0]) {
            case "Damaged":
                value += 1;
                break;
            case "Standard":
                value += 2;
                break;
            case "Heroic":
                value += 3;
                break;
            case "Enchanted":
                value += 4;
                break;
            default:
                value += 5;
        }

        switch (artefactSplit[1]) {
            case "Red":
                value += 1;
                break;
            case "Orange":
                value += 2;
                break;
            case "Yellow":
                value += 3;
                break;
            case "Green":
                value += 4;
                break;
            default:
                value += 5;
        }

        if (artefactSplit[2].equals("Paper") || artefactSplit[2].equals("Shirt") || artefactSplit[2].equals("Wig")) {
            multiplier = 2;
        } else if (artefactSplit[2].equals("Scissors") || artefactSplit[2].equals("Jacket") || artefactSplit[2].equals("Bandanna")) {
            multiplier = 3;
        } else {
            multiplier = 4;
        }
        this.type = type;
        this.name = name;
        condition = artefactSplit[0];
        colour = artefactSplit[1];
        piece = artefactSplit[2];
        this.value = value * multiplier;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public String getCondition() {
        return this.condition;
    }

    public String getColour() {
        return this.colour;
    }

    public String getPiece() {
        return this.piece;
    }
}