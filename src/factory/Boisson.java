package factory;

public class Boisson extends MenuItemFactory  {

	public Boisson(String nom, double prix) {
		super(nom, prix);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return "Boisson";
	}

}
