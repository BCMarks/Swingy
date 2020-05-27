package swingy.Controllers;

import swingy.Views.interfaces.LoadView;

public class LoadController {

    private LoadView view;

    public LoadController(LoadView mode) {
        this.view = mode;
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
}