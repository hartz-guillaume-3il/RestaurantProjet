package factory;

import model.MenuItem;
import model.Ingredient;

import java.util.List;

public class MenuItemFactory {

	public static MenuItem createMenuItem(String type, String nom, double prix, String description, boolean flag,
			List<Ingredient> ingredients) {
		switch (type.toLowerCase()) {
		case "plat":
			return PlatFactory.createItem(nom, prix, description, flag, ingredients);
		case "dessert":
			return DessertFactory.createItem(nom, prix, description, flag, ingredients);
		case "entrée":
		case "entree":
			return EntreeFactory.createItem(nom, prix, description, flag, ingredients);
		case "boisson":
			return BoissonFactory.createItem(nom, prix, description, flag, ingredients);
		default:
			throw new IllegalArgumentException("Type d'élément inconnu : " + type);
		}
	}
}
