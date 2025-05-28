package model;

import java.util.List;

public class Dessert extends MenuItem {
    private boolean sucre;
    private List<Ingredient> ingredients;

    public Dessert(String nom, double prix, String description, boolean sucre, List<Ingredient> ingredients) {
        super(nom, prix, description);
        this.sucre = sucre;
        this.ingredients = ingredients;
    }
    public Dessert(String ligne) {
        super(ligne);
    }
    public boolean isSucre() {
        return sucre;
    }

    public void setSucre(boolean sucre) {
        this.sucre = sucre;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String getType() {
        return "Dessert";
    }

    @Override
    public String toString() {
        return super.toString() + (sucre ? " (sucré)" : " (non sucré)");
    }
}
