package swingy.Controllers;

import swingy.Views.interfaces.HomeView;

public class HomeController {
    private HomeView view;

    public HomeController(HomeView mode) {
        this.view = mode;
    }

    public void heroCreate() {
        this.view.create();
        //controller: CreateController
        //view: CreateView
    }

    public void heroLoad() {
        this.view.load();
        //controller: LoadController
        //view: LoadView
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