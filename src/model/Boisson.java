package model;

public class Boisson extends MenuItem {
	private boolean alcoolisee;

	public Boisson(String nom, double prix, String description, boolean alcoolisee) {
		super(nom, prix, description);
		this.alcoolisee = alcoolisee;
	}

	public Boisson(String ligne) {
		super(ligne);
		String[] parties = ligne.split(";");
		this.alcoolisee = Boolean.parseBoolean(parties[4]);
	}

	public boolean isAlcoolisee() {
		return alcoolisee;
	}

	public void setAlcoolisee(boolean alcoolisee) {
		this.alcoolisee = alcoolisee;
	}

	@Override
	public String getType() {
		return "Boisson";
	}

	@Override
	public String toString() {
		return super.toString() + (alcoolisee ? " (alcoolisée)" : " (non alcoolisée)");
	}
}
