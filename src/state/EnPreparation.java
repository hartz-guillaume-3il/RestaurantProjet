package state;

import model.Commande;

public class EnPreparation extends EtatCommande {

	public EnPreparation(Commande commande) {
		super(commande);
	}

	@Override
	public void avancerEtat() {
		commande.setEtat(new Prete(commande));
		System.out.println("Commande #" + commande.getId() + " est prête.");
		commande.notifyObservers();
	}

	@Override
	public String getNomEtat() {
		return "En préparation";
	}
}
