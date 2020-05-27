package swingy.Controllers;

import swingy.Models.Hero;

public class GameController {

    private Hero hero;

    public GameController(Hero hero) {
        this.hero = hero;
    }

    public void gameLost() {
        this.hero = new Hero(this.hero.getName(), this.hero.getJob(), this.hero.getLevel(), this.hero.getXP(), this.hero.getInventory().getArtefactName("weapon"), this.hero.getInventory().getArtefactName("armour"), this.hero.getInventory().getArtefactName("helm"), this.hero.getWins(), false);
        //controller: HomeController
        //view: HomeView
    }

    public void gameWon() {
        //controller: GameController
        //view: GameView
    }

    public void move(String direction) {

    }

    public void encounter() {

    }

    public void fight() {

    }

    public void run() {

    }


}