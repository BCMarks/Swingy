package swingy.Views.interfaces;

public interface CreateView {
    public void setup();
    public void help();
    public void switchMode(String name, int job);
    public void confirm();
    public void cancel();
    public void quit();
}