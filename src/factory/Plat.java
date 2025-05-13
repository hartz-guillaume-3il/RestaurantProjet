package factory;

import model.MenuItem;

public class Plat extends MenuItem{

	public Plat(String nom, double prix,String description) {
		super(nom, prix,description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return "Plat";
	}

}
