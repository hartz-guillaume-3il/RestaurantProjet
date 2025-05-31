package model;

public class Ingredient {
	private String nom;
	private int quantiteStock;

	public Ingredient(String nom, int quantiteStock) {
		this.nom = nom;
		this.quantiteStock = quantiteStock;
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

	public void ajusterStock(int delta) {
		this.quantiteStock += delta;
		if (this.quantiteStock < 0) {
			this.quantiteStock = 0;
		}
	}

	@Override
	public String toString() {
		return "Ingredient : " + nom + ", Quantitée disponible en stock (nbr) : " + quantiteStock;
	}

}
