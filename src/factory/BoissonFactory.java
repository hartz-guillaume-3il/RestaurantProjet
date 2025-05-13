package factory;

import model.Boisson;
import model.MenuItem;

public class BoissonFactory {
    public static MenuItem createItem(String nom, double prix, String description, boolean alcoolisee) {
        return new Boisson(nom, prix, description, alcoolisee);
    }
}
