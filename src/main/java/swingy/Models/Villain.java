package swingy.Models;

public class Villain {
    private String villain_name;
    private String villain_class;
    private int villain_level;
    private int villain_xp;
    private Stats villain_stats;
    private Backpack villain_backpack;
    private boolean undead;

    public Villain(String job) {
        this.undead = true;
        this.villain_class = job; //villain class, dead hero class
        this.villain_level = 1; //assign appropriate level
        this.villain_xp = 10; //xp hero gains when he wins
        this.villain_stats = new Stats(job); // Base stats determined by custom classes
        this.villain_backpack = new Backpack(); //create loot table for each villain, for dead hero it is the items they died with
        //items permanently removed from dead hero on defeat
    }

    //only applies for deceased heroes
    public Villain(String name, String job, int level, String weapon, String armour, String helm) {
        this.undead = true;
        this.villain_name = name;
        this.villain_class = job;
        this.villain_level = level;
        this.villain_stats = new Stats(job);
        this.villain_backpack = new Backpack(weapon, armour, helm);
    }
}