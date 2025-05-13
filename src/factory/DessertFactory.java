package factory;

import model.Dessert;
import model.MenuItem;
import model.Ingredient;

import java.util.List;

public class DessertFactory {
    public static MenuItem createItem(String nom, double prix, String description, boolean sucre, List<Ingredient> ingredients) {
        return new Dessert(nom, prix, description, sucre, ingredients);
    }
}
