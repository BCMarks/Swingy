package swingy.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import swingy.Models.Hero;

public class DatabaseText {
    private static File output = new File("heroes.txt");
    private ArrayList<Hero> heroList;
    
    public DatabaseText() {
        heroList = setupHeroList();
    }

    public ArrayList<Hero> getAllHeroes() {
        return this.heroList;
    }

    public boolean isClassUnlocked() {
        for (Hero hero : heroList) {
            if (hero.isOALCUnlocked()) {
                return true;
            }
        }
        return false;
    }

    public boolean isArenaUnlocked() {
        for (Hero hero : heroList) {
            if (hero.isArenaUnlocked()) {
                return true;
            }
        }
        return false;
    }

    public boolean insertHero(Hero hero) {
        boolean isOriginalName = true;
        for (Hero establishedHero : heroList) {
            if (establishedHero.getName().equals(hero.getName())) {
                isOriginalName = false;
                break;
            }
        }
        if (isOriginalName) {
            try {
                FileWriter writer = new FileWriter("heroes.txt", true);
                writer.write(
                    hero.getName()+","+
                    hero.getJob()+","+
                    hero.getLevel()+","+
                    hero.getXP()+","+
                    hero.getInventory().getArtefactName("Weapon")+","+
                    hero.getInventory().getArtefactName("Armour")+","+
                    hero.getInventory().getArtefactName("Helm")+","+
                    hero.getWins()+","+hero.getStatus()+","+
                    hero.getStats().getStat("attack")+","+
                    hero.getStats().getStat("defense")+","+
                    hero.getStats().getStat("health")+","+
                    hero.getLocation()+","+
                    hero.isArenaUnlocked()+","+
                    hero.isOALCUnlocked()+"\n"
                );
                writer.close();
                heroList.add(hero);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public void updateHero(Hero hero) {
        for (Hero establishedHero : heroList) {
            if (establishedHero.getName().equals(hero.getName())) {
                establishedHero.setInventory(hero.getInventory());
                establishedHero.setLevel(hero.getLevel());
                establishedHero.setStats(hero.getStats());
                establishedHero.setStatus(hero.getStatus());
                establishedHero.setWins(hero.getWins());
                establishedHero.setXP(hero.getXP());
                establishedHero.setLocation(hero.getLocation());
                break;
            }
        }
        updateDatabase();
    }

    private void updateDatabase() {
        try {
            FileWriter writer = new FileWriter("heroes.txt", false);
            for (Hero hero : heroList) {
                writer.write(
                    hero.getName()+","+
                    hero.getJob()+","+
                    hero.getLevel()+","+
                    hero.getXP()+","+
                    hero.getInventory().getArtefactName("Weapon")+","+
                    hero.getInventory().getArtefactName("Armour")+","+
                    hero.getInventory().getArtefactName("Helm")+","+
                    hero.getWins()+","+hero.getStatus()+","+
                    hero.getStats().getStat("attack")+","+
                    hero.getStats().getStat("defense")+","+
                    hero.getStats().getStat("health")+","+
                    hero.getLocation()+","+
                    hero.isArenaUnlocked()+","+
                    hero.isOALCUnlocked()+"\n"
                );
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Hero> setupHeroList() {
        ArrayList<Hero> heroes = new ArrayList<Hero>();
        try {
            Scanner scanner = new Scanner(output);
            while (scanner.hasNextLine()) {
                String[] rs = scanner.nextLine().split(",");
                heroes.add(new Hero(rs[0], rs[1], Integer.parseInt(rs[2]), Integer.parseInt(rs[3]), rs[4], rs[5], rs[6], Integer.parseInt(rs[7]), Boolean.parseBoolean(rs[8]), Integer.parseInt(rs[12]), Boolean.parseBoolean(rs[13]), Boolean.parseBoolean(rs[14])));
            }
            scanner.close();
        }
        catch (Exception e) {
            System.out.println("Database has been lost. Creating new database.");;
        }
        return heroes;
    }
}