package factory;
import model.MenuItem;
import factory.Plat;
import factory.Boisson;


public class MenuItemFactory {
    public static MenuItem createMenuItem(String type, String nom, double prix, String description) {
        switch (type.toLowerCase()) {
            case "plat":
                return new Plat(nom, prix, description);
            case "boisson":
                return new Boisson(nom, prix, description);
            default:
                throw new IllegalArgumentException("Type d'élément inconnu : " + type);
        }
    }
}
