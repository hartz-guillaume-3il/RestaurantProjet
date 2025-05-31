package model;

public abstract class Employe {
	protected String nom;
	protected int id;
	protected String role;

	public Employe(String nom, int id, String role) {
		this.nom = nom;
		this.id = id;
		this.role = role;
	}

	public abstract void afficherInfos();

	public String getNom() {
		return nom;
	}

	public int getId() {
		return id;
	}

	public String getRole() {
		return role;
	}
}
