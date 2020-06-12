package swingy.Models;

public class Hero {
    private String hero_name;
    private String hero_class;
    private int hero_level;
    private int hero_xp;
    private Stats hero_stats;
    private Backpack hero_backpack;
    private int game_wins;
    private boolean alive;
    private int location;
    private boolean unlockedArena;
    private boolean unlockedOALC;

    public Hero(String name, String job) {
        this.alive = true;
        this.game_wins = 0;
        this.hero_name = name;
        this.hero_class = job;
        this.hero_level = 1;
        this.hero_xp = 0;
        this.hero_stats = new Stats(job, 1);
        this.hero_backpack = new Backpack();
        this.location = 41;
        this.unlockedArena = false;
        this.unlockedOALC = false;
    }

    public Hero(String name, String job, int level, int exp, String weapon, String armour, String helm, int wins, boolean status, int location, boolean arenaUnlocked, boolean jobUnlocked) {
        this.alive = status;
        this.location = location;
        this.game_wins = wins;
        this.hero_name = name;
        this.hero_class = job;
        this.hero_level = level;
        this.hero_xp = exp;
        this.hero_backpack = new Backpack(weapon, armour, helm);
        this.hero_stats = new Stats(job, level, this.hero_backpack.getBackpackStats());
        this.unlockedArena = arenaUnlocked;
        this.unlockedOALC = jobUnlocked;
    }

    private void updateLevel() {
        double xp_required = this.hero_level * 1000 + Math.pow(this.hero_level - 1, 2) * 450;
        if(this.hero_xp >= xp_required) {
            this.hero_level += 1;
            System.out.println(hero_name + " is now level "+hero_level+"!");
            this.hero_xp -= xp_required;
            this.hero_stats = new Stats(hero_class, hero_level, this.hero_backpack.getBackpackStats());
        }
    }

    public void increaseXP(Integer xp_gain) {
        System.out.println(xp_gain + " xp gained!");
        this.hero_xp += xp_gain;
        updateLevel();
    }

    public void setLevel(int level) {
        this.hero_level = level;
    }

    public void setXP(int xp) {
        this.hero_xp = xp;
    }

    public void setInventory(Backpack backpack) {
        this.hero_backpack = backpack;
    }

    public void setStatus(Boolean alive) {
        this.alive = alive;
    }

    public void setWins(int wins) {
        this.game_wins = wins;
    }

    public void setStats(Stats stats) {
        this.hero_stats = stats;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void activateArena() {
        this.unlockedArena = true;
    }

    public boolean isArenaUnlocked() {
        return this.unlockedArena;
    }

    public void activateOALC() {
        this.unlockedOALC = true;
    }

    public boolean isOALCUnlocked() {
        return this.unlockedOALC;
    }

    public String getName() {
        return this.hero_name;
    }

    public String getJob() {
        return this.hero_class;
    }

    public int getLevel() {
        return this.hero_level;
    }

    public int getXP() {
        return this.hero_xp;
    }

    public Backpack getInventory() {
        return this.hero_backpack;
    }

    public boolean getStatus() {
        return this.alive;
    }

    public int getWins() {
        return this.game_wins;
    }

    public Stats getStats() {
        return this.hero_stats;
    }

    public int getLocation() {
        return this.location;
    }
}