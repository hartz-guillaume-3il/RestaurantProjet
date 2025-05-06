package factory;

public class Plat extends MenuItemFactory {

	public Plat(String nom, double prix) {
		super(nom, prix);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return "Plat";
	}

}
