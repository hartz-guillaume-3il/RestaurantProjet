package model;

import java.util.List;

public class Boisson extends MenuItem {
	private boolean alcoolisee;
	private List<Ingredient> ingredients; 

	public Boisson(String nom, double prix, String description, boolean alcoolisee, List<Ingredient> ingredients) {
		super(nom, prix, description);
		this.alcoolisee = alcoolisee;
		this.ingredients = ingredients;
	}

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

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
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
