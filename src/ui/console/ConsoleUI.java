package ui.console;

import facade.RestaurantFacade;
import strategy.PaymentStrategy;
import strategy.EspecePaiement;
import strategy.PayPalPaiement;
import strategy.CarteBancairePaiement;
import strategy.CryptoPaiement;
import model.MenuItem;
import model.*;
import observer.StockObserver;
import observer.CuisineObserver;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConsoleUI {

	private RestaurantFacade facade;
	private List<Table> tables;
	private List<Client> clients;
	private List<MenuItem> menuItems;
	private StockObserver stockObserver;
	private CuisineObserver cuisineObserver;

	public ConsoleUI() {
		facade = new RestaurantFacade();
		tables = new ArrayList<>();
		clients = new ArrayList<>();
		menuItems = new ArrayList<>();
		initialiserTables();
		stockObserver = new StockObserver();
		cuisineObserver = new CuisineObserver();
		chargerMenus();
	}

	private void initialiserTables() {
		tables.clear();
		tables.add(new Table(1, 4));
		tables.add(new Table(2, 2));
		tables.add(new Table(3, 6));
		tables.add(new Table(4, 4));
		tables.add(new Table(5, 2));
		tables.add(new Table(6, 8));
	}

	public void lancer() {
		UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
		UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 13));

		boolean continuer = true;
		while (continuer) {
			String[] options = { "Gérer les Réservations", "Gérer les Commandes", "Gérer le Menu", "Quitter" };
			int choix = JOptionPane.showOptionDialog(null,
					"Bienvenue dans le Système de Gestion du Restaurant\n\nVeuillez sélectionner une option :",
					"Menu Principal", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
					options[0]);

			switch (choix) {
			case 0 -> gererReservations();
			case 1 -> gererCommandes();
			case 2 -> gererMenu();
			case 3, JOptionPane.CLOSED_OPTION -> continuer = false;
			default -> continuer = false;
			}
		}
		JOptionPane.showMessageDialog(null, "Merci d'avoir utilisé notre système. Au revoir !");
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

		commande.addObserver(stockObserver);
		commande.addObserver(cuisineObserver);

		String[] etats = { "En cours", "Prête", "Servie", "Payée" };
		int etatChoisi;
		do {
			etatChoisi = JOptionPane.showOptionDialog(null,
					"État actuel : " + commande.getEtat().getNomEtat() + "\nSélectionnez le prochain état :",
					"Modification de l'état de la commande", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, etats, etats[0]);

			if (etatChoisi >= 0 && !commande.getEtat().getNomEtat().equals("Payée")) {
				commande.avancerEtat();
			}
		} while (etatChoisi >= 0 && !commande.getEtat().getNomEtat().equals("Payée"));

		PaymentStrategy strategy = choisirMethodePaiement();
		Facture facture = facade.payerCommande(commande, strategy);
		JOptionPane.showMessageDialog(null, "Facture générée :\n" + facture.toString());
	}

	private void gererMenu() {
		boolean continuer = true;
		while (continuer) {
			String[] options = { "Ajouter Plat", "Ajouter Entrée", "Ajouter Dessert", "Ajouter Boisson",
					"Afficher Menu", "Retour" };
			int choix = JOptionPane.showOptionDialog(null, "Gestion du Menu", "Menu", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			switch (choix) {
			case 0 -> ajouterPlat();
			case 1 -> ajouterEntree();
			case 2 -> ajouterDessert();
			case 3 -> ajouterBoisson();
			case 4 -> afficherMenu();
			case 5, JOptionPane.CLOSED_OPTION -> continuer = false;
			default -> continuer = false;
			}
		}
	}

	private void ajouterPlat() {
		MenuItem plat = creerMenuItem("Plat", false);
		if (plat != null) {
			menuItems.add(plat);
			sauvegarderMenus();
			JOptionPane.showMessageDialog(null, "Plat ajouté : " + plat);
		}
	}

	private void ajouterEntree() {
		MenuItem entree = creerMenuItem("Entrée", false);
		if (entree != null) {
			menuItems.add(entree);
			sauvegarderMenus();
			JOptionPane.showMessageDialog(null, "Entrée ajoutée : " + entree);
		}
	}

	private void ajouterDessert() {
		MenuItem dessert = creerMenuItem("Dessert", false);
		if (dessert != null) {
			menuItems.add(dessert);
			sauvegarderMenus();
			JOptionPane.showMessageDialog(null, "Dessert ajouté : " + dessert);
		}
	}

	private void ajouterBoisson() {
		MenuItem boisson = creerMenuItem("Boisson", true);
		if (boisson != null) {
			menuItems.add(boisson);
			sauvegarderMenus();
			JOptionPane.showMessageDialog(null, "Boisson ajoutée : " + boisson);
		}
	}

	private MenuItem creerMenuItem(String type, boolean demandeAlcool) {
		String nom = JOptionPane.showInputDialog("Nom du " + type + " :");
		if (nom == null || nom.trim().isEmpty())
			return null;

		double prix = Double.parseDouble(JOptionPane.showInputDialog("Prix du " + type + " :"));
		String description = JOptionPane.showInputDialog("Description :");

		if (type.equalsIgnoreCase("Boisson")) {
			int alcoolOption = JOptionPane.showConfirmDialog(null, "Est-ce une boisson alcoolisée ?");
			boolean alcoolisee = (alcoolOption == JOptionPane.YES_OPTION);
			return new Boisson(nom, prix, description, alcoolisee);
		} else if (type.equalsIgnoreCase("Dessert")) {
			int SaleOption = JOptionPane.showConfirmDialog(null, "Le dessert est-il sucré ?");
			boolean sale = (SaleOption == JOptionPane.YES_OPTION);
			return new Dessert(nom, prix, description, sale, new ArrayList<>());
		} else if (type.equalsIgnoreCase("Entrée")) {
			int froideOption = JOptionPane.showConfirmDialog(null, "L'entrée est-elle froide ?");
			boolean froide = (froideOption == JOptionPane.YES_OPTION);
			return new Entree(nom, prix, description, froide, new ArrayList<>());
		} else {
			int VegeOption = JOptionPane.showConfirmDialog(null, "Le plat est-il végétarien ?");
			boolean vegetarien = (VegeOption == JOptionPane.YES_OPTION);
			return new Plat(nom, prix, description, vegetarien, new ArrayList<>());
		}
	}

	private void afficherMenu() {
		if (menuItems.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Aucun article dans le menu.");
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Menu Actuel :\n--------------------------\n");
		for (MenuItem item : menuItems) {
			sb.append("- ").append(item).append("\n");
		}
		JOptionPane.showMessageDialog(null, sb.toString());
	}

	private List<MenuItem> saisirItems() {
		List<MenuItem> items = new ArrayList<>();
		while (true) {
			afficherMenu();
			String nomItem = JOptionPane.showInputDialog("Nom de l'article à ajouter (ou vide pour terminer) :");
			if (nomItem == null || nomItem.trim().isEmpty())
				break;
			MenuItem item = trouverMenuItemParNom(nomItem);
			if (item != null) {
				items.add(item);
			} else {
				JOptionPane.showMessageDialog(null, "Article non trouvé.");
			}
		}
		return items;
	}

	private MenuItem trouverMenuItemParNom(String nom) {
		for (MenuItem item : menuItems) {
			if (item.getNom().equalsIgnoreCase(nom)) {
				return item;
			}
		}
		return null;
	}

	private String getTablesDisponibles() {
		StringBuilder sb = new StringBuilder();
		for (Table table : tables) {
			if (!table.isOccupee())
				sb.append(table).append("\n");
		}
		return sb.length() > 0 ? sb.toString() : "Aucune table disponible";
	}

	private String getTablesOccupees() {
		StringBuilder sb = new StringBuilder();
		for (Table table : tables) {
			if (table.isOccupee())
				sb.append(table).append("\n");
		}
		return sb.length() > 0 ? sb.toString() : "Aucune table occupée";
	}

	private Table trouverTableParNumero(int numero) {
		for (Table table : tables) {
			if (table.getNumero() == numero)
				return table;
		}
		return null;
	}

	private Client trouverClientParNom(String nom) {
		for (Client client : clients) {
			if (client.getNom().equalsIgnoreCase(nom))
				return client;
		}
		return null;
	}

	private PaymentStrategy choisirMethodePaiement() {
		String[] options = { "Espèces", "Carte bancaire", "Crypto Monnaie", "Virement Paypal" };
		int choix = JOptionPane.showOptionDialog(null, "Choisissez une méthode de paiement :", "Paiement",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		return switch (choix) {
		case 0 -> new EspecePaiement();
		case 1 -> new CarteBancairePaiement();
		case 2 -> new CryptoPaiement();
		case 3 -> new PayPalPaiement();
		default -> new EspecePaiement();
		};
	}

	private void sauvegarderMenus() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("menus.txt"))) {
			for (MenuItem menu : menuItems) {
				writer.write(menu.getType() + ";" + menu.getNom() + ";" + menu.getPrix() + ";" + menu.getDescription());
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la sauvegarde des menus : " + e.getMessage());
		}
	}

	private void chargerMenus() {
		File fichier = new File("menus.txt");
		if (!fichier.exists()) {
			System.err.println("Fichier menus.txt introuvable !");
			return;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			while ((ligne = reader.readLine()) != null) {
				String[] parties = ligne.split(";");
				if (parties.length >= 4) {
					String type = parties[0].trim();
					String nom = parties[1].trim();
					double prix = Double.parseDouble(parties[2].trim());
					String description = parties[3].trim();

					MenuItem item = null;
					switch (type) {
					case "Plat" -> item = new Plat(nom, prix, description, false, new ArrayList<>());
					case "Entrée" -> item = new Entree(nom, prix, description, false, new ArrayList<>());
					case "Dessert" -> item = new Dessert(nom, prix, description, false, new ArrayList<>());
					case "Boisson" -> item = new Boisson(nom, prix, description, false);
					}

					if (item != null) {
						menuItems.add(item);
					}
				}
			}
		} catch (IOException | NumberFormatException e) {
			System.err.println("Erreur lors du chargement des menus : " + e.getMessage());
		}
	}

}
