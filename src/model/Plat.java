package model;

import java.util.List;

public class Plat extends MenuItem {
    private boolean vegetarien;
    private List<Ingredient> ingredients;

    @Override
    public String getType() {
        return "Plat";
    }
}
