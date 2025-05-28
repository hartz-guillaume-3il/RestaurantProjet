package model;

public class Boisson extends MenuItem {
	private boolean alcoolisee; // true = alcoolisée, false = non alcoolisée

	// Constructeur principal
	public Boisson(String nom, double prix, String description, boolean alcoolisee) {
		super(nom, prix, description);
		this.alcoolisee = alcoolisee;
	}

	// Constructeur depuis une ligne du fichier
	public Boisson(String ligne) {
		super(ligne);
		String[] parties = ligne.split(";");
		this.alcoolisee = Boolean.parseBoolean(parties[4]);
	}

	public boolean isAlcoolisee() {
		return alcoolisee;
	}

	public void setAlcoolisee(boolean alcoolisee) {
		this.alcoolisee = alcoolisee;
	}

	@Override
	public String getType() {
		return "Boisson";
	}

	@Override
	public String toString() {
		String alcool = (alcoolisee) ? "alcoolisée" : "non alcoolisée";
		return "- " + getType() + " - " + getNom() + " (" + getPrix() + "€): " + getDescription() + " (" + alcool + ")";
	}
}
