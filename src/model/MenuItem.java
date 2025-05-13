package model;

public abstract class MenuItem {
    protected String nom;
    protected double prix;
    protected String description;

    public MenuItem(String nom, double prix, String description) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
    }
    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public String getDescription() { return description; }

    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " - " + nom + " (" + prix + "€): " + description;
    }
}

