package swingy.Models;

import javax.validation.constraints.Min;

public class Stats {
    @Min(1)
    private int attack;
    @Min(1)
    private int defense;
    @Min(1)
    private int health;

    public Stats(String job, int level) {
        switch(job){
            case "Giant Rat":
                this.attack = 2;
                this.defense = 2;
                this.health = 6;
                break;
            case "Skeleton Soldier":
                this.attack = 4;
                this.defense = 3;
                this.health = 10;
                break;
            case "Banished Sorceror":
                this.attack = 10;
                this.defense = 1;
                this.health = 8;
                break;
            case "Ancient Dragon":
                this.attack = 40;
                this.defense = 40;
                this.health = 100;
                break;
            case "Big Swordfish Tank":
                this.attack = 2 + (level - 1);
                this.defense = 5 + 2 * (level - 1);
                this.health = 10 + (level - 1);
                break;
            case "Big Villain Arsenal":
                this.attack = 5 + 2 * (level - 1);
                this.defense = 2 + (level - 1);
                this.health = 10 + (level - 1);
                break;
            case "Healthy Lt Gabriel Cash Coder":
                this.attack = 2 + (level - 1);
                this.defense = 2 + (level - 1);
                this.health = 21 + 2 * (level - 1);
                break;
            case "Old Aries Lickable Cat": 
                this.attack = 10 + (level / 2);
                this.defense = 10 +  (level / 2);
                this.health = 42 + (level / 2);
                break;
            default:
                this.attack = 1;
                this.defense = 1;
                this.health = 1;
        }
    }

    public Stats(int atk, int def, int hp) {
        this.attack = atk;
        this.defense = def;
        this.health = hp;
    }

    public Stats(String job, int level, Stats backpackStats) {
        switch(job) {
            case "Giant Rat":
                this.attack = 2;
                this.defense = 2;
                this.health = 6;
                break;
            case "Skeleton Soldier":
                this.attack = 4;
                this.defense = 3;
                this.health = 10;
                break;
            case "Banished Sorceror":
                this.attack = 10;
                this.defense = 1;
                this.health = 8;
                break;
            case "Ancient Dragon":
                this.attack = 40;
                this.defense = 40;
                this.health = 100;
                break;
            case "Big Swordfish Tank":
                this.attack = 2 + (level - 1);
                this.defense = 5 + 2 * (level - 1);
                this.health = 10 + (level - 1);
                break;
            case "Big Villain Arsenal":
                this.attack = 5 + 2 * (level - 1);
                this.defense = 2 + (level - 1);
                this.health = 10 + (level - 1);
                break;
            case "Healthy Lt Gabriel Cash Coder":
                this.attack = 2 + (level - 1);
                this.defense = 2 + (level - 1);
                this.health = 21 + 2 * (level - 1);
                break;
            case "Old Aries Lickable Cat": 
                this.attack = 10 + (level / 2);
                this.defense = 10 +  (level / 2);
                this.health = 42 + (level / 2);
                break;
            default:
                this.attack = 1;
                this.defense = 1;
                this.health = 1;
        }
        this.attack += backpackStats.getStat("attack");
        this.defense += backpackStats.getStat("defense");
        this.health += backpackStats.getStat("health");
    }

    public int getStat(String property) {
        switch(property){
            case "attack":
                return this.attack;
            case "defense":
                return this.defense;
            default:
                return this.health;
        }
    }
}