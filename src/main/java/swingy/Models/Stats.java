package swingy.Models;

public class Stats {
    private int attack;
    private int defense;
    private int health;

    public Stats(String job) {
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
                this.attack = 2;
                this.defense = 5;
                this.health = 10;
                break;
            case "Big Villain Arsenal":
                this.attack = 5;
                this.defense = 2;
                this.health = 10;
                break;
            case "Healthy Lt Gabriel Cash Coder":
                this.attack = 3;
                this.defense = 3;
                this.health = 14;
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