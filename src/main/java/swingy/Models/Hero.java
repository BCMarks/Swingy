package swingy.Models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import swingy.Controllers.GameController;

public class Hero {
    @Size(min = 2, max = 20)
    private String hero_name;
    @NotEmpty
    private String hero_class;
    @Min(1)
    private int hero_level;
    @NotNull
    private int hero_xp;
    @NotNull
    private Stats hero_stats;
    @NotNull
    private Backpack hero_backpack;
    @NotNull
    private int game_wins;
    @NotNull
    private boolean alive;
    @NotNull
    private int location;
    @NotNull
    private boolean unlockedArena;
    @NotNull
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

    private void updateLevel(GameController controller) {
        double xp_required = this.hero_level * 1000 + Math.pow(this.hero_level - 1, 2) * 450;
        if (this.hero_xp >= xp_required) {
            this.hero_level += 1;
            controller.displayLevelUp();
            this.hero_xp -= xp_required;
            this.hero_stats = new Stats(hero_class, hero_level, this.hero_backpack.getBackpackStats());
        }
    }

    public void increaseXP(Integer xp_gain, GameController controller) {
        this.hero_xp += xp_gain;
        controller.displayXPGain(xp_gain);
        updateLevel(controller);
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