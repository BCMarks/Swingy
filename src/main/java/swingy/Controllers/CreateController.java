package swingy.Controllers;

import swingy.App;
import swingy.Models.Hero;
import swingy.Utilities.DatabaseText;
import swingy.Views.interfaces.CreateView;

public class CreateController {
    private CreateView view;
    private static DatabaseText db;

    public CreateController(CreateView mode) {
        this.view = mode;
        db = App.getDatabase();
    }

    public void confirm() {
        this.view.confirm();
    }

    public void cancel() {
        this.view.cancel();
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

    public boolean isValidHeroName(String name) {
        if (name.length() < 3 || name.length() > 20 || name.toLowerCase().equals("admin") || !name.matches("^[a-zA-Z0-9]*$") || nameExists(name)) {
            return false;
        }
        return true;
    }

    public boolean nameExists(String name) {
        for (Hero establishedHero : db.getAllHeroes()) {
            if (establishedHero.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}