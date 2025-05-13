package model;

public class Facture {
	private Commande commande;
	private double montantAPayer;
	private boolean payee;

	public Facture(Commande commande, double montantAPayer) {
		this.commande = commande;
		this.montantAPayer = montantAPayer;
		this.payee = false;
	}

	public void genererFacture() {
		System.out.println("----- Facture -----");
		System.out.println(commande);
		System.out.println("Montant à payer : " + montantAPayer + "€");
		System.out.println("Statut : " + (payee ? "Payée" : "Non payée"));
		System.out.println("-------------------");
	}

	// Getters et Setters
	public Commande getCommande() {
		return commande;
	}

	public double getMontantAPayer() {
		return montantAPayer;
	}

	public boolean isPayee() {
		return payee;
	}

	public void setPayee(boolean payee) {
		this.payee = payee;
	}

	@Override
	public String toString() {
		return "Facture pour Commande #" + commande.getId() + ", Montant : " + montantAPayer + "€, "
				+ (payee ? "Payée" : "Non payée");
	}
}
