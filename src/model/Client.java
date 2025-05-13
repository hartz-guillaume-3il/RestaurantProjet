package model;

public class Client {
	private String nom;
	private String telephone;

	public Client(String nom, String telephone) {
		this.nom = nom;
		this.telephone = telephone;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "Client : " + nom + " (Tel: " + telephone + ")";
	}
}
