package factory;

import model.Entree;
import model.MenuItem;
import model.Ingredient;

import java.util.List;

public class EntreeFactory {
    public static MenuItem createItem(String nom, double prix, String description, boolean froide, List<Ingredient> ingredients) {
        return new Entree(nom, prix, description, froide, ingredients);
    }
}
