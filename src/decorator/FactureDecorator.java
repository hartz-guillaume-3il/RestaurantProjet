package decorator;

import model.Facture;
import model.Commande;

public abstract class FactureDecorator extends Facture {
	protected Facture facture;

	public FactureDecorator(Facture facture) {
		super(facture.getCommande(), facture.getMontantAPayer());
		this.facture = facture;
	}

	@Override
	public void genererFacture() {
		facture.genererFacture();
	}

	@Override
	public boolean isPayee() {
		return facture.isPayee();
	}

	@Override
	public void setPayee(boolean payee) {
		facture.setPayee(payee);
	}

	@Override
	public String toString() {
		return facture.toString();
	}
}
