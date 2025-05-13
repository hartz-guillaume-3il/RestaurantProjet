package factory;

import model.MenuItem;

public class Boisson extends MenuItem {

	public Boisson(String nom, double prix, String description) {
		super(nom, prix,description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return "Boisson";
	}

}
