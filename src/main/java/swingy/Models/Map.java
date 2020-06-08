package swingy.Models;

public class Map {
    private int tiles;
    private int center;
    private int size;
    private int floor;
    private Hero hero; //remove this

    public Map(Hero hero) {
        this.size = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
        this.floor = hero.getWins() + 1;
        this.tiles = size * size;
        this.center = (tiles + 1) / 2;
        this.hero = hero; //remove this
    }

    public void mapDetails() { //remove this
        System.out.println("Welcome to floor " + this.floor);
        System.out.println("This floor is " + this.size + " x " + this.size);
        System.out.println("\nWill " + this.hero.getName() + " survive?");
    }

    public int getFloor() {
        return this.floor;
    }

    public int getMapSize() {
        return this.size;
    }

    public int getMapCenter() {
        return this.center;
    }

    public int getTileCount() {
        return this.tiles;
    }
}