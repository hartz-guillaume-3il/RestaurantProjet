package model;

public class Table {
	private int numero;
	private int capacite;
	private boolean occupee;
	private Commande commandeEnCours;

	public Table(int numero, int capacite) {
		this.numero = numero;
		this.capacite = capacite;
		this.occupee = false;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public boolean isOccupee() {
		return occupee;
	}

	public void setOccupee(boolean occupee) {
		this.occupee = occupee;
	}

	public Commande getCommandeEnCours() {
		return commandeEnCours;
	}

	public void setCommandeEnCours(Commande commandeEnCours) {
		this.commandeEnCours = commandeEnCours;
	}

	@Override
	public String toString() {
		return "Table " + numero + " (Capacité: " + capacite + ", Occupée: " + (occupee ? "Oui" : "Non") + ")";
	}
}
