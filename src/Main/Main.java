package Main;

import facade.RestaurantFacade;
import model.*;
import state.EtatCommande;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        Client client = new Client("Alice", "0601020304");

        Table table = new Table(1, 4);

    
        List<MenuItem> items = new ArrayList<>();
        items.add(new Plat("Pizza Margherita", 9.5, "Pizza classique avec tomate et mozzarella", true, new ArrayList<>()));
        items.add(new Boisson("Eau Plate", 1.5, "Bouteille d'eau 50cl", false));

       
        RestaurantFacade facade = new RestaurantFacade();

   
        System.out.println("\n--- Test Réservation ---");
        boolean reservee = facade.reserverTable(table, client);
        System.out.println("Réservation réussie : " + reservee);

        
        System.out.println("\n--- Test Commande ---");
        Commande commande = facade.prendreCommande(client, table, items);
        System.out.println("Commande créée : " + commande);

        System.out.println("\n--- Test Transitions d'États ---");
        while (!(commande.getEtat().getNomEtat().equals("Payée"))) {
            commande.avancerEtat();
            System.out.println("État courant : " + commande.getEtat().getNomEtat());
        }

        
        System.out.println("\n--- Test Facturation ---");
        Facture facture = facade.payerCommande(commande);
        facture.genererFacture();
    }
}
