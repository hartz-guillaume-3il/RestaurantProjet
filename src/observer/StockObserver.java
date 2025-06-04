package observer;

import model.Commande;
import model.MenuItem;
// le stock est mis à jour après une commande
public class StockObserver implements Observer {

	@Override
	public void update(Commande commande) {
		System.out.println("StockObserver : mise à jour du stock suite à la commande " + commande.getId());
		for (MenuItem item : commande.getItems()) {
			System.out.println(" - Décrément du stock pour : " + item.getNom());
		}
	}
}