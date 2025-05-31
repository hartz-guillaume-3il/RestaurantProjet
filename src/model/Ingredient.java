package model;

public class Ingredient {
    private String nom;
    private int quantiteStock;
    private String unite;

    public Ingredient(String nom, int quantiteStock, String unite) {
        this.nom = nom;
        this.quantiteStock = quantiteStock;
        this.unite = unite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public void ajusterStock(int delta) {
        this.quantiteStock += delta;
        if (this.quantiteStock < 0) {
            this.quantiteStock = 0;
        }
    }

    @Override
    public String toString() {
        return "Ingrédient : " + nom + ", Quantité en stock : " + quantiteStock + " " + unite;
    }
}
