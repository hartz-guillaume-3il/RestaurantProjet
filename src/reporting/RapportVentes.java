package reporting;

import model.Commande;
import model.MenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RapportVentes {

	/**
	 * @param commandes
	 */
	public static void genererRapport(List<Commande> commandes) {
		if (commandes == null || commandes.isEmpty()) {
			System.out.println("Aucune vente enregistrée.");
			return;
		}

		double totalVentes = 0;
		Map<String, Integer> platsCommandes = new HashMap<>();

		for (Commande commande : commandes) {
			totalVentes += commande.calculerMontant();
			for (MenuItem item : commande.getItems()) {
				platsCommandes.put(item.getNom(), platsCommandes.getOrDefault(item.getNom(), 0) + 1);
			}
		}

		System.out.println("\n=== Rapport de Ventes ===");
		System.out.printf("Total des ventes : %.2f €\n", totalVentes);
		System.out.println("Plats les plus commandés :");
		platsCommandes.forEach((plat, nb) -> System.out.println("- " + plat + ": " + nb + " fois"));
		System.out.println("==========================");
	}
}
