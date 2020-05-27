package swingy.Models;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database2 {
    private static File output = new File("heroes.txt");
    
    public Database2() {
    }

    public ArrayList<Hero> getAllHeroes() {
        ArrayList<Hero> heroes = new ArrayList<Hero>();
        try {
            Scanner scanner = new Scanner(output);

            while (scanner.hasNextLine()) {
                String[] rs = scanner.nextLine().split(" ");
                heroes.add(new Hero(rs[0], rs[1].replace("_", " "), Integer.parseInt(rs[2]), Integer.parseInt(rs[3]), rs[4], rs[5], rs[6], Integer.parseInt(rs[7]), Boolean.parseBoolean(rs[8])));
            }
            scanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return heroes;
    }

    public void insertHero(Hero hero) {
        try {
            FileWriter writer = new FileWriter("heroes.txt", true);
            writer.write(hero.getName()+" "+hero.getJob().replace(" ", "_")+" "+hero.getLevel()+" "+hero.getXP()+" "+hero.getInventory().getArtefactName("Weapon")+" "+hero.getInventory().getArtefactName("Armour")+" "+hero.getInventory().getArtefactName("Helm")+" "+hero.getWins()+" "+hero.getStatus()+" "+hero.getStats().getStat("attack")+" "+hero.getStats().getStat("defense")+" "+hero.getStats().getStat("health")+"\n");
            writer.close();
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void updateHero(Hero hero) {
        try {
            Connection con = DriverManager.getConnection(dbConnection);
            Statement stmt = con.createStatement();
            String Weapon = null, Armour = null, Helm = null;
            for (Artefact artefact : hero.getInventory().getInventory()) {
                switch(artefact.getType()) {
                    case "Weapon":
                        Weapon = artefact.getName();
                        break;
                    case "Armour":
                        Armour = artefact.getName();
                        break;
                    case "Helm":
                        Helm = artefact.getName();
                        break;
                    default:
                        break;
                }
            }
            stmt.execute("UPDATE Heroes SET HeroLevel = '"+hero.getLevel()+"', HeroXP = '"+hero.getXP()+"', Weapon = '"+Weapon+"', Armour = '"+Armour+"', Helm = '"+Helm+"', Wins = '"+hero.getWins()+"', Alive = '"+hero.getStatus()+"' WHERE HeroName = '" + hero.getName()+"'");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}