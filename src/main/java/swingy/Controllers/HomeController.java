package swingy.Controllers;

import swingy.Views.interfaces.HomeView;

public class HomeController {
    private HomeView view;

    public HomeController(HomeView mode) {
        this.view = mode;
    }

    public void heroCreate() {
        this.view.create();
    }

    public void heroLoad() {
        this.view.load();
    }

    public void arena() {
        this.view.arena();
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