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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("[%s] %s - %.2f€ : %s", getType(), nom, prix, description);
    }
}
