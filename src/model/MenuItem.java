package model;

public abstract class MenuItem {
    protected String nom;
    protected double prix;
    protected String description;

    public abstract String getType();
}
