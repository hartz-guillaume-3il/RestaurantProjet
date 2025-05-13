package ui.console;

import facade.RestaurantFacade;
import model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ConsoleUI {

    private RestaurantFacade facade;
    private List<Table> tables;
    private List<Client> clients;

    public ConsoleUI() {
        facade = new RestaurantFacade();
        tables = new ArrayList<>();
        clients = new ArrayList<>();
        initialiserTables();
    }

    private void initialiserTables() {
        tables.add(new Table(1, 4));
        tables.add(new Table(2, 2));
        tables.add(new Table(3, 6));
    }

    public void lancer() {
        boolean continuer = true;
        while (continuer) {
            String[] options = {"Gérer les Réservations", "Gérer les Commandes", "Quitter"};
            int choix = JOptionPane.showOptionDialog(null, "Système de Gestion de Restaurant", "Menu Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choix) {
                case 0 -> gererReservations();
                case 1 -> gererCommandes();
                case 2 -> continuer = false;
                default -> continuer = false;
            }
        }
    }

    private void gererReservations() {
        String nom = JOptionPane.showInputDialog("Nom du client :");
        String tel = JOptionPane.showInputDialog("Téléphone du client :");
        Client client = new Client(nom, tel);
        clients.add(client);

        String tablesDispo = getTablesDisponibles();
        String numeroStr = JOptionPane.showInputDialog("Tables disponibles :\n" + tablesDispo + "\nNuméro de table :");
        int numero = Integer.parseInt(numeroStr);
        Table table = trouverTableParNumero(numero);

        if (table != null && facade.reserverTable(table, client)) {
            JOptionPane.showMessageDialog(null, "Réservation réussie !");
        } else {
            JOptionPane.showMessageDialog(null, "Échec de la réservation.");
        }
    }

    private void gererCommandes() {
        String nomClient = JOptionPane.showInputDialog("Nom du client :");
        Client client = trouverClientParNom(nomClient);
        if (client == null) {
            JOptionPane.showMessageDialog(null, "Client non trouvé.");
            return;
        }

        String tablesOccupees = getTablesOccupees();
        String numeroStr = JOptionPane.showInputDialog("Tables occupées :\n" + tablesOccupees + "\nNuméro de table :");
        int numero = Integer.parseInt(numeroStr);
        Table table = trouverTableParNumero(numero);
        if (table == null || !table.isOccupee()) {
            JOptionPane.showMessageDialog(null, "Table non trouvée ou non occupée.");
            return;
        }

        List<MenuItem> items = saisirItems();
        Commande commande = facade.prendreCommande(client, table, items);

        while (!commande.getEtat().getNomEtat().equals("Payée")) {
            JOptionPane.showMessageDialog(null, "État actuel : " + commande.getEtat().getNomEtat());
            commande.avancerEtat();
        }

        Facture facture = facade.payerCommande(commande);
        JOptionPane.showMessageDialog(null, "Facture générée :\n" + facture.toString());
    }

    private String getTablesDisponibles() {
        StringBuilder sb = new StringBuilder();
        for (Table table : tables) {
            if (!table.isOccupee()) {
                sb.append(table).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : "Aucune table disponible";
    }

    private String getTablesOccupees() {
        StringBuilder sb = new StringBuilder();
        for (Table table : tables) {
            if (table.isOccupee()) {
                sb.append(table).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : "Aucune table occupée";
    }

    private Table trouverTableParNumero(int numero) {
        for (Table table : tables) {
            if (table.getNumero() == numero) {
                return table;
            }
        }
        return null;
    }

    private Client trouverClientParNom(String nom) {
        for (Client client : clients) {
            if (client.getNom().equalsIgnoreCase(nom)) {
                return client;
            }
        }
        return null;
    }

    private List<MenuItem> saisirItems() {
        List<MenuItem> items = new ArrayList<>();
        boolean continuer = true;
        while (continuer) {
            String nom = JOptionPane.showInputDialog("Nom de l'article (ou vide pour terminer) :");
            if (nom == null || nom.trim().isEmpty()) {
                continuer = false;
                continue;
            }

            double prix = Double.parseDouble(JOptionPane.showInputDialog("Prix de l'article :"));
            String desc = JOptionPane.showInputDialog("Description de l'article :");

            items.add(new Plat(nom, prix, desc, false, new ArrayList<>()));
        }
        return items;
    }
}
