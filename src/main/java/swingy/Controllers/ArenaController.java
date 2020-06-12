package swingy.Controllers;

import swingy.App;
import swingy.Models.Hero;
import swingy.Models.Villain;
import swingy.Utilities.DatabaseText;
import swingy.Views.interfaces.ArenaView;

public class ArenaController {
    private ArenaView view;
    private static DatabaseText db;

    public ArenaController(ArenaView mode) {
        this.view = mode;
        db = App.getDatabase();
    }

    public void begin() {
        this.view.begin();
    }

    public boolean fight(Hero heroOne, Hero heroTwo, Villain villainOne, Villain villainTwo) {
        int oneHealth;
        int oneAttack;
        int oneDefense;
        int twoHealth;
        int twoAttack;
        int twoDefense;

        if (heroOne == null) {
            oneHealth = villainOne.getStats().getStat("health");
            oneAttack = villainOne.getStats().getStat("attack");
            oneDefense = villainOne.getStats().getStat("defense");
        } else {
            oneHealth = heroOne.getStats().getStat("health");
            oneAttack = heroOne.getStats().getStat("attack");
            oneDefense = heroOne.getStats().getStat("defense");
        }

        if (heroTwo == null) {
            twoHealth = villainTwo.getStats().getStat("health");
            twoAttack = villainTwo.getStats().getStat("attack");
            twoDefense = villainTwo.getStats().getStat("defense");
        } else {
            twoHealth = heroTwo.getStats().getStat("health");
            twoAttack = heroTwo.getStats().getStat("attack");
            twoDefense = heroTwo.getStats().getStat("defense");
        }

        while (twoHealth > 0 && oneHealth > 0) {
            twoHealth = attackTurn(oneAttack, twoDefense, twoHealth);
            if (twoHealth > 0) {
                oneHealth = attackTurn(twoAttack, oneDefense, oneHealth);
            }
        }
        if (twoHealth > 0) {
            return false;
        } else {
            return true;
        }
    }

    private int attackTurn(int offAttack, int defDefense, int defHealth) {
        int damage;
        if (offAttack > defDefense) {
            double critRoll = Math.random();
            if (critRoll >= 0.97) {
                damage = 2 * offAttack - defDefense;
            } else {
                damage = offAttack - defDefense;
            }
        } else {
            damage = 1;
        }
        defHealth -= damage;
        return defHealth;
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

    public void home() {
        this.view.home();
    }

}