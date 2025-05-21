package facade;

import model.Client;
import model.Commande;
import model.Facture;
import model.Table;
import model.MenuItem;
import strategy.PaymentStrategy;

import java.util.List;

public class RestaurantFacade {

	public Commande prendreCommande(Client client, Table table, List<MenuItem> items) {
		Commande commande = new Commande(client, table);
		for (MenuItem item : items) {
			commande.ajouterItem(item);
		}
		commande.calculerMontant();
		System.out.println("Commande créée pour le client : " + client.getNom());
		return commande;
	}

	public boolean reserverTable(Table table, Client client) {
		if (!table.isOccupee()) {
			table.setOccupee(true);
			System.out.println("Table " + table.getNumero() + " réservée pour " + client.getNom());
			return true;
		} else {
			System.out.println("Table " + table.getNumero() + " est déjà occupée.");
			return false;
		}
	}

	public Facture payerCommande(Commande commande, PaymentStrategy paymentMethod) {
	    commande.calculerMontant();
	    double montant = commande.getMontantTotal();

	    // Appliquer le moyen de paiement
	    paymentMethod.pay(montant);

	    // Créer et retourner la facture
	    Facture facture = new Facture(commande, montant);
	    facture.setPayee(true);
	    System.out.println("Facture payée avec stratégie pour le montant de : " + montant + "€");
	    return facture;
	}

}
