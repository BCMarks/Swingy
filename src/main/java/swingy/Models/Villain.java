package swingy.Models;

public class Villain {
    private String rival_hero;
    private String villain_name;
    private String villain_class;
    private int villain_level;
    private int villain_xp;
    private Stats villain_stats;
    private Backpack villain_backpack;
    private boolean undead;
    private int location;

    public Villain(String job, Hero hero) {
        this.rival_hero = hero.getName();
        this.undead = true;
        this.villain_name = "";
        this.villain_class = job;
        this.villain_level = 1;
        this.villain_xp = setXP(job);
        this.villain_stats = new Stats(job, 1);
        this.villain_backpack = setLoottable();
    }

    private Backpack setLoottable() {
        String weapon = "null";
        String armour = "null";
        String helm = "null";
        double roll = Math.random();
        if (roll > 0.75) {
            weapon = new Artefact("Weapon").getName();
        } else if (roll > 0.5) {
            armour = new Artefact("Armour").getName();
        } else if (roll > 0.25) {
            helm = new Artefact("Helm").getName();
        }
        return new Backpack(weapon, armour, helm);
    }

    public Villain(String name, String job, int level, String weapon, String armour, String helm, Hero hero) {
        this.rival_hero = hero.getName();
        this.undead = true;
        this.villain_name = name;
        this.villain_class = job;
        this.villain_level = level;
        this.villain_xp = level * 700;
        this.villain_backpack = new Backpack(weapon, armour, helm);
        this.villain_stats = new Stats(job, level, this.villain_backpack.getBackpackStats());
    }

    public Villain(String rival, String name, String job, int level, int xp, String weapon, String armour, String helm, int location, boolean status) {
        this.rival_hero = rival;
        this.undead = status;
        this.villain_name = name;
        this.villain_class = job;
        this.villain_level = level;
        this.villain_xp = xp;
        this.villain_backpack = new Backpack(weapon, armour, helm);
        if (name.length() > 0) {
            this.villain_stats = new Stats(job, level, this.villain_backpack.getBackpackStats());
        } else {
            this.villain_stats = new Stats(job, 1);
        }
        this.location = location;
    }

    private int setXP(String job) {
        int xp;
        switch (job) {
            case "Skeleton Soldier":
                xp = 600;
                break;
            case "Banished Sorceror":
                xp = 1300;
                break;
            case "Ancient Dragon":
                xp = 6000;
                break;
            default:
                xp = 250;
        }
        return xp;
    }

    //battle method for arena

    public void setLocation(int location) {
        this.location = location;
    }

    public void setStatus(boolean status) {
        this.undead = status;
    }

    public String getName() {
        return this.villain_name;
    }

    public String getJob() {
        return this.villain_class;
    }

    public int getLevel() {
        return this.villain_level;
    }

    public int getXP() {
        return this.villain_xp;
    }

    public Backpack getInventory() {
        return this.villain_backpack;
    }

    public boolean getStatus() {
        return this.undead;
    }

    public Stats getStats() {
        return this.villain_stats;
    }

    public int getLocation() {
        return this.location;
    }

    public String getRivalHero() {
        return this.rival_hero;
    }
}