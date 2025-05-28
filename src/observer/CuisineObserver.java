package observer;

import model.Commande;
import model.MenuItem;

public class CuisineObserver implements Observer {

	@Override
	public void update(Commande commande) {
		System.out.println("CuisineObserver : mise à jour de la cuisine suite à la commande " + commande.getId());
		for (MenuItem item : commande.getItems()) {
			System.out.println(" - Préparation en cuisine pour : " + item.getNom());
		}
	}
}