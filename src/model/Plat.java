package model;

import java.util.List;

public class Plat extends MenuItem {
	private boolean vegetarien;
	private List<Ingredient> ingredients;

	public Plat(String nom, double prix, String description, boolean vegetarien, List<Ingredient> ingredients) {
		super(nom, prix, description);
		this.vegetarien = vegetarien;
		this.ingredients = ingredients;
	}

	public Plat(String ligne) {
		super(ligne);
		String[] parties = ligne.split(";");
		this.vegetarien = Boolean.parseBoolean(parties[4]);
	}

	public boolean isVegetarien() {
		return vegetarien;
	}

	public void setVegetarien(boolean vegetarien) {
		this.vegetarien = vegetarien;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String getType() {
		return "Plat";
	}

	@Override
	public String toString() {
		return super.toString() + (vegetarien ? " (végétarien)" : "");
	}
}
