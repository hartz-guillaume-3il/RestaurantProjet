package decorator;

import model.Facture;

public class TVAFactureDecorator extends FactureDecorator {
	private static final double TVA = 0.20;

	public TVAFactureDecorator(Facture facture) {
		super(facture);
		double montantAvecTVA = facture.getMontantAPayer() * (1 + TVA);
		this.montantAPayer = montantAvecTVA;
	}

	@Override
	public void genererFacture() {
		super.genererFacture();
		double tva = facture.getMontantAPayer() * TVA;
		System.out.println("TVA ajoutée (20%) : " + tva + "€");
		System.out.println("Montant TTC : " + getMontantAPayer() + "€");
	}

	@Override
	public double getMontantAPayer() {
		return montantAPayer;
	}

	@Override
	public String toString() {
		return "Facture pour Commande #" + commande.getId() + ", Montant TTC : " + getMontantAPayer() + "€, "
				+ (isPayee() ? "Payée" : "Non payée");
	}
}
