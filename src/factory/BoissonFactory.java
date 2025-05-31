package factory;

import java.util.List;

import model.Boisson;
import model.Ingredient;
import model.MenuItem;

public class BoissonFactory {
	public static MenuItem createItem(String nom, double prix, String description, boolean alcoolisee,
			List<Ingredient> ingredients) {
		return new Boisson(nom, prix, description, alcoolisee, ingredients);
	}
}
