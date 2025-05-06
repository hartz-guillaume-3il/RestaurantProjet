package factory;

public abstract  class MenuItemFactory {
protected String nom;
protected double prix;

public MenuItemFactory(String nom, double prix) {
	this.nom = nom;
	this.prix = prix;
}

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	this.nom = nom;
}

public double getPrix() {
	return prix;
}

public void setPrix(double prix) {
	this.prix = prix;
}

public abstract String getType();
}
