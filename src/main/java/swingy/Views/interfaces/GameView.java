package swingy.Views.interfaces;

import java.util.ArrayList;

import swingy.Models.Hero;
import swingy.Models.Map;
import swingy.Models.Villain;

public interface GameView {
    public void setup(Hero hero);
    public boolean moveHero(String direction, Map map, ArrayList<Villain> villains);
    public void gameOver();
    public void gameVictory();
    public void grantSpoils(Villain villain);
    public void help();
    public void switchMode();
    public void quit();
}