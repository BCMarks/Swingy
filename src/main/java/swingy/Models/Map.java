package swingy.Models;

import javax.validation.constraints.Min;

public class Map {
    @Min(81)
    private int tiles;
    @Min(41)
    private int center;
    @Min(9)
    private int size;
    @Min(1)
    private int floor;

    public Map(Hero hero) {
        this.size = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
        this.floor = hero.getWins() + 1;
        this.tiles = size * size;
        this.center = (tiles + 1) / 2;
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