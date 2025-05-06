package model;

import java.util.List;

public class Entree extends MenuItem {
    private boolean froide;
    private List<Ingredient> ingredients;

    public Entree(String nom, double prix, String description, boolean froide, List<Ingredient> ingredients) {
        super(nom, prix, description);
        this.froide = froide;
        this.ingredients = ingredients;
    }

    public boolean isFroide() {
        return froide;
    }

    public void setFroide(boolean froide) {
        this.froide = froide;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String getType() {
        return "Entrée";
    }

    @Override
    public String toString() {
        return super.toString() + (froide ? " (froide)" : " (chaude)");
    }
}
