package swingy.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import swingy.Models.Hero;
import swingy.Models.Villain;

public class DatabaseVillains {
    private static File output = new File("villains.txt");
    private ArrayList<Villain> villainList;
    
    public DatabaseVillains() {
        villainList = setupVillainList();
    }

    public ArrayList<Villain> getAllVillains(Hero hero) {
        ArrayList<Villain> herosVillainList = new ArrayList<Villain>();
        for (Villain villain : villainList) {
            if (villain.getRivalHero().equals(hero.getName()) && villain.getStatus()) {
                herosVillainList.add(villain);
            }
        }
        return herosVillainList;
    }

    public void insertVillain(Villain villain) {
        try {
            FileWriter writer = new FileWriter("villains.txt", true);
            writer.write(villain.getRivalHero()+","+villain.getName()+","+villain.getJob()+","+villain.getLevel()+","+villain.getXP()+","+villain.getInventory().getArtefactName("Weapon")+","+villain.getInventory().getArtefactName("Armour")+","+villain.getInventory().getArtefactName("Helm")+","+villain.getLocation()+","+villain.getStatus()+"\n");
            writer.close();
            villainList.add(villain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeVillains(Hero hero) {
        ArrayList<Villain> newList = new ArrayList<Villain>();
        for (Villain villain : villainList) {
            if (!villain.getRivalHero().equals(hero.getName())) {
                newList.add(villain);
            }
        }
        this.villainList = newList;
        updateDatabase();
    }

    public void updatevillain(Villain villain) {
        for (Villain establishedvillain : villainList) {
            if (establishedvillain.getRivalHero().equals(villain.getRivalHero()) &&
                establishedvillain.getLocation() == villain.getLocation()) {
                establishedvillain.setStatus(villain.getStatus());
                break;
            }
        }
        updateDatabase();
    }

    private void updateDatabase() {
        try {
            FileWriter writer = new FileWriter("villains.txt", false);
            for (Villain villain : villainList) {
                writer.write(villain.getRivalHero()+","+villain.getName()+","+villain.getJob()+","+villain.getLevel()+","+villain.getXP()+","+villain.getInventory().getArtefactName("Weapon")+","+villain.getInventory().getArtefactName("Armour")+","+villain.getInventory().getArtefactName("Helm")+","+villain.getLocation()+","+villain.getStatus()+"\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Villain> setupVillainList() {
        ArrayList<Villain> villains = new ArrayList<Villain>();
        try {
            Scanner scanner = new Scanner(output);
            while (scanner.hasNextLine()) {
                String[] rs = scanner.nextLine().split(",");
                villains.add(new Villain(rs[0], rs[1], rs[2], Integer.parseInt(rs[3]), Integer.parseInt(rs[4]), rs[5], rs[6], rs[7], Integer.parseInt(rs[8]), Boolean.parseBoolean(rs[9])));
            }
            scanner.close();
        }
        catch (Exception e) {
            System.out.println("Villain database has been lost. Creating new database.");
        }
        return villains;
    }
}