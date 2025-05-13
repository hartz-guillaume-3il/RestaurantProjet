package factory;

import model.Plat;
import model.MenuItem;
import model.Ingredient;

import java.util.List;

public class PlatFactory {
    public static MenuItem createItem(String nom, double prix, String description, boolean vegetarien, List<Ingredient> ingredients) {
        return new Plat(nom, prix, description, vegetarien, ingredients);
    }
}
