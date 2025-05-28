package state;

import model.Commande;

public class Prete extends EtatCommande {

	public Prete(Commande commande) {
		super(commande);
	}

	@Override
	public void avancerEtat() {
		commande.setEtat(new Livree(commande));
		System.out.println("Commande #" + commande.getId() + " est livrée au client.");
		commande.notifyObservers();
	}

	@Override
	public String getNomEtat() {
		return "Prête";
	}
}
