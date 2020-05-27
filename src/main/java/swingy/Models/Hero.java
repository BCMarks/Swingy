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

    public Hero(String name, String job) {
        this.alive = true;
        this.game_wins = 0;
        this.hero_name = name;
        this.hero_class = job;
        this.hero_level = 1;
        this.hero_xp = 0;
        this.hero_stats = new Stats(job); // Base stats determined by custom classes
        this.hero_backpack = new Backpack();
    }

    public Hero(String name, String job, int level, int exp, String weapon, String armour, String helm, int wins, boolean status) {
        this.alive = status;
        this.game_wins = wins;
        this.hero_name = name;
        this.hero_class = job;
        this.hero_level = level;
        this.hero_xp = exp;
        this.hero_stats = new Stats(job); // Base stats determined by custom classes
        this.hero_backpack = new Backpack(weapon, armour, helm);
    }

    //public void updateHero(String parameter, String value) {
    //    switch(parameter)
    //}

    private void setStats() {
        Integer atk = this.hero_stats.getStat("attack");
        Integer def = this.hero_stats.getStat("defense");
        Integer hp = this.hero_stats.getStat("hp");

        switch(this.hero_class) {
            case "Big Swordfish Tank":
                atk += 1;
                def += 2;
                hp += 1;
                break;
            case "Big Villain Arsenal":
                atk += 2;
                def += 1;
                hp += 1;
                break;
            case "Healthy Lt Gabriel Cash Coder":
                atk += 1;
                def += 1;
                hp += 2;
                break;
            default:
                break;
        }
        this.hero_stats = new Stats(atk, def, hp);
    }

    private void setLevel() {
        Double xp_required = this.hero_level * 1000 + Math.pow(this.hero_level - 1, 2) * 450;
        if(this.hero_xp >= xp_required) {
            this.hero_level += 1;
            this.hero_xp -= xp_required;
            setStats(); 
        }
    }

    public void setXP(Integer xp_gain) {
        this.hero_xp += xp_gain;
        setLevel();
    }

    public void battle(Boolean fleed) {
        Double roll = Math.random(); //please refine battle simulation
        if(fleed)
            roll -= 0.1; //cowardice penalty
        if(roll >= 0.5) {
            setXP(7);
            //random chance for artefact drop and replacement yay or nay
        }
        else 
            this.alive = false;
    }

    public void flee() {
        Double roll = Math.random();
        if(roll >= 0.5)
            System.out.println("success");
        else 
            battle(true);
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
}