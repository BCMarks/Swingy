package swingy.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import swingy.Models.Artefact;
import swingy.Models.Hero;

public class DatabaseSQL {
    //private static String dbInit = "jdbc:sqlserver://localhost:1433;user=admin;password=admin";
    private static String dbConnection = "jdbc:sqlserver://localhost:1433;databaseName=dbSwingy;user=admin;password=admin";
    private static String dbInit = "jdbc:sqlserver://localhost:1433";

    public DatabaseSQL() {
        try {
            //consider using a different database
            Connection con = DriverManager.getConnection(dbInit, "admin", "admin");
            Statement stmt = con.createStatement();
            stmt.execute("If(db_id(N'dbSwingy') IS NULL) BEGIN CREATE DATABASE dbSwingy; END;");
            con.close();

            con = DriverManager.getConnection(dbConnection);
            stmt = con.createStatement();
            stmt.execute("IF (NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dbo' AND  TABLE_NAME = 'Heroes')) BEGIN CREATE TABLE Heroes(HeroID int Identity(1,1) PRIMARY KEY NOT NULL,HeroName varchar(20) NOT NULL, HeroClass VARCHAR(50) NOT NULL, HeroLevel INT DEFAULT '1', HeroXP INT DEFAULT 0, Weapon VARCHAR(50) DEFAULT NULL, Armour VARCHAR(50) DEFAULT NULL, Helm VARCHAR(50) DEFAULT NULL, Wins INT DEFAULT 0, Alive BIT DEFAULT 1); END");
           // ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            //while (rs.next()) {
              //  System.out.println(rs.getString("name") + " " + rs.getString("database_id"));
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Hero> getAllHeroes() {
        ArrayList<Hero> heroes = new ArrayList<Hero>();
        try {
            Connection con = DriverManager.getConnection(dbConnection);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Heroes");

            while (rs.next()) {
                heroes.add(new Hero(rs.getString("HeroName"), rs.getString("HeroClass"), rs.getInt("HeroLevel"), rs.getInt("HeroXP"), rs.getString("Weapon"), rs.getString("Armour"), rs.getString("Helm"), rs.getInt("Wins"), rs.getBoolean("Alive"), 0, true, true));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return heroes;
    }

    public void insertHero(Hero hero) {
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
            stmt.execute("INSERT INTO Heroes (HeroName, HeroClass, HeroLevel, HeroXP, Weapon, Armour, Helm, Wins, Alive) VALUES ('"+hero.getName()+"','"+hero.getJob()+"','"+hero.getLevel()+"','"+hero.getXP()+"','"+Weapon+"','"+Armour+"','"+Helm+"','"+hero.getWins()+"','"+hero.getStatus()+"')");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHero(Hero hero) {
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
    }
}