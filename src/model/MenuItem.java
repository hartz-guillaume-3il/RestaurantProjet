package model;

public abstract class MenuItem {
	protected String nom;
	protected double prix;
	protected String description;
	private String type;

	public MenuItem(String nom, double prix, String description) {
		this.nom = nom;
		this.prix = prix;
		this.description = description;
	}

	public MenuItem(String ligne) {
		String[] parties = ligne.split(";");
		this.nom = parties[0];
		this.prix = Double.parseDouble(parties[1]);
		this.type = parties[2];
	}

	public String getNom() {
		return nom;
	}

	public double getPrix() {
		return prix;
	}

	public String getDescription() {
		return description;
	}

	public abstract String getType();

	@Override
	public String toString() {
		return getType() + " - " + nom + " (" + prix + "€): " + description + " - " + type;
	}
}
