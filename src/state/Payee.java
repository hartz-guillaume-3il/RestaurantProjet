package state;

import model.Commande;

public class Payee extends EtatCommande {

	public Payee(Commande commande) {
		super(commande);
	}

	@Override
	public void avancerEtat() {
		System.out.println("Commande #" + commande.getId() + " est déjà payée, aucun autre état.");
	}

	@Override
	public String getNomEtat() {
		return "Payée";
	}
}
