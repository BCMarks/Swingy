package swingy.Controllers;

import java.util.ArrayList;

import swingy.App;
import swingy.Models.Hero;
import swingy.Models.Map;
import swingy.Models.Villain;
import swingy.Utilities.DatabaseText;
import swingy.Utilities.DatabaseVillains;
import swingy.Views.interfaces.GameView;

public class GameController {
    private GameView view;
    private static DatabaseVillains dbv;
    private static DatabaseText db;

    public GameController(GameView mode) {
        this.view = mode;
        dbv = new DatabaseVillains();
        db = App.getDatabase();
    }

    public ArrayList<Villain> generateVillains(Map map, Hero hero) {
        ArrayList<Villain> villains = dbv.getAllVillains(hero);
        ArrayList<Hero> allHeroes = db.getAllHeroes();
        ArrayList<Hero> deadHeroes = new ArrayList<Hero>();
        for (Hero establishedHero : allHeroes) {
            if (!establishedHero.getStatus()) {
                deadHeroes.add(establishedHero);
            }
        }
        if (villains.size() > 0) {
            return villains;
        }
        Villain baddie;
        double roll;
        for (int i = 0; i < map.getMapSize(); i++) {
            roll = Math.random();
            if (roll > 1 - map.getFloor() * 0.02) {
                baddie = new Villain("Ancient Dragon", hero);
            } else if (roll > 1 - map.getFloor() * 0.1) {
                baddie = new Villain("Banished Sorceror", hero);
            } else if (roll > 1 - map.getFloor() * 0.3) {
                baddie = new Villain("Skeleton Soldier", hero);
            } else {
                baddie = new Villain("Giant Rat", hero);
            }
            int potentialLocation = (int)((Math.random() * (map.getTileCount() - 1)) + 1);
            while (isOccupied(villains, map, potentialLocation)) {
                potentialLocation = (int)((Math.random() * (map.getTileCount() - 1)) + 1);
            }
            baddie.setLocation(potentialLocation);
            villains.add(baddie);
            dbv.insertVillain(baddie);
        }
        for (Hero antihero : deadHeroes) {
            if (Math.random() > 0.5 && antihero.getWins() == map.getFloor() - 1) {
                int potentialLocation = (int)((Math.random() * (map.getTileCount() - 1)) + 1);
                while (isOccupied(villains, map, potentialLocation)) {
                    potentialLocation = (int)((Math.random() * (map.getTileCount() - 1)) + 1);
                }
                baddie = new Villain(antihero.getName(),
                    antihero.getJob(), antihero.getLevel(),
                    antihero.getInventory().getArtefactName("Weapon"),
                    antihero.getInventory().getArtefactName("Armour"),
                    antihero.getInventory().getArtefactName("Helm"),
                    hero
                );
                baddie.setLocation(potentialLocation);
                villains.add(baddie);
                dbv.insertVillain(baddie);
            }
        }
        return villains;
    }

    private boolean isOccupied(ArrayList<Villain> villains, Map map, int potentialLocation) {
        if (map.getMapCenter() == potentialLocation) {
            return true;
        }
        for (Villain villain : villains) {
            if (villain.getLocation() == potentialLocation) {
                return true;
            }
        }
        return false;
    }

    public void gameLost() {
        this.view.gameOver();
    }

    public void gameWon() {
        this.view.gameVictory();
    }

    public void removeVillains(Hero hero) {
        dbv.removeVillains(hero);
    }

    public boolean move(String direction, Map map, ArrayList<Villain> villains) {
        return this.view.moveHero(direction, map, villains);
    }

    public boolean fight(boolean isCoward, Hero hero, Villain villain) {
        int heroHealth = hero.getStats().getStat("health");
        int heroAttack = hero.getStats().getStat("attack");
        int heroDefense = hero.getStats().getStat("defense");
        int villainHealth = villain.getStats().getStat("health");
        int villainAttack = villain.getStats().getStat("attack");
        int villainDefense = villain.getStats().getStat("defense");
        if (isCoward) {
            while (villain.getStatus() && hero.getStatus()) { 
                heroHealth = villainTurn(hero, villainAttack, heroDefense, heroHealth);
                if (hero.getStatus()) {
                    villainHealth = heroTurn(hero, villain, heroAttack, villainDefense, villainHealth);
                }
            }
        } else {
            while (villain.getStatus() && hero.getStatus()) {
                villainHealth = heroTurn(hero, villain, heroAttack, villainDefense, villainHealth);
                if (villain.getStatus()) {
                    heroHealth = villainTurn(hero, villainAttack, heroDefense, heroHealth);
                }
            }
        }
        return hero.getStatus();
    }

    private int heroTurn(Hero hero, Villain villain, int heroAttack, int villainDefense, int villainHealth) {
        int damage;
        double critRoll;
        if (heroAttack > villainDefense) {
            critRoll = Math.random();
            if (critRoll >= 0.97) {
                damage = 2 * heroAttack - villainDefense;
            } else {
                damage = heroAttack - villainDefense;
            }
        } else {
            damage = 1;
        }
        villainHealth -= damage;
        if (villainHealth <= 0) {
            villain.setStatus(false);
            dbv.updatevillain(villain);
            hero.increaseXP(villain.getXP());
            this.view.grantSpoils(villain);
        }
        return villainHealth;
    }

    private int villainTurn(Hero hero, int villainAttack, int heroDefense, int heroHealth) {
        int damage;
        if (villainAttack > heroDefense) {
            double critRoll = Math.random();
            if (critRoll >= 0.97) {
                damage = 2 * villainAttack - heroDefense;
            } else {
                damage = villainAttack - heroDefense;
            }
        } else {
            damage = 1;
        }
        heroHealth -= damage;
        if (heroHealth <= 0) {
            hero.setStatus(false);
        }
        return heroHealth;
    }

    public boolean flee() {
        double roll = Math.random();
        if (roll >= 0.5) {
            return true;
        } else {
            return false;
        }
    }

    public void help() {
        this.view.help();
    }

    public void quit() {
        this.view.quit();
    }

    public void switchMode() {
        this.view.switchMode();
    }
}