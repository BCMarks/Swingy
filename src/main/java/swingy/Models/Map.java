package swingy.Models;

public class Map {
    private int size;
    private int floor;

    public Map(Hero hero) {
        this.size = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
        this.floor = hero.getWins() + 1;
        generateVillains();
    }

    private void generateVillains() {
        //create random number of villains with varying strength capped at appropriate level
        //spread across map
    }
}