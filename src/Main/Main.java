package Main;

import facade.RestaurantFacade;
import model.*;
import state.EtatCommande;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Créer un client
        Client client = new Client("Alice", "0601020304");

        // Créer une table
        Table table = new Table(1, 4);

        // Créer des éléments de menu
        List<MenuItem> items = new ArrayList<>();
        items.add(new Plat("Pizza Margherita", 9.5, "Pizza classique avec tomate et mozzarella", true, new ArrayList<>()));
        items.add(new Boisson("Eau Plate", 1.5, "Bouteille d'eau 50cl", false));

        // Initialiser la façade
        RestaurantFacade facade = new RestaurantFacade();

        // Tester la réservation de table
        System.out.println("\n--- Test Réservation ---");
        boolean reservee = facade.reserverTable(table, client);
        System.out.println("Réservation réussie : " + reservee);

        // Tester la création de commande
        System.out.println("\n--- Test Commande ---");
        Commande commande = facade.prendreCommande(client, table, items);
        System.out.println("Commande créée : " + commande);

        // Tester les transitions d'état
        System.out.println("\n--- Test Transitions d'États ---");
        while (!(commande.getEtat().getNomEtat().equals("Payée"))) {
            commande.avancerEtat();
            System.out.println("État courant : " + commande.getEtat().getNomEtat());
        }

        // Tester la facturation
        System.out.println("\n--- Test Facturation ---");
        Facture facture = facade.payerCommande(commande);
        facture.genererFacture();
    }
}
