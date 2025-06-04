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
import stockage.RestaurantStockage;
import factory.MenuItemFactory;
import decorator.TVAFactureDecorator;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ConsoleUI {

	private RestaurantFacade facade;
	private RestaurantStockage stockage;
	private List<Table> tables;
	private List<Client> clients;
	private List<Employe> personnel;
	private List<Commande> commandes;
	private StockObserver stockObserver;
	private CuisineObserver cuisineObserver;

	/**
	 * Constructeur : initialise les données et charge les informations existantes.
	 */
	public ConsoleUI() {
		facade = new RestaurantFacade();
		stockage = new RestaurantStockage();
		tables = new ArrayList<>();
		clients = new ArrayList<>();

		stockage.chargerCommandes();
		commandes = stockage.getCommandes();

		stockage.chargerReservations();
		List<Reservation> reservations = stockage.getReservations();

		personnel = stockage.chargerPersonnel();
		initialiserTables();

		stockObserver = new StockObserver();
		cuisineObserver = new CuisineObserver();

		for (Reservation r : reservations) {
			clients.add(r.getClient());
			Table table = trouverTableParNumero(r.getTable().getNumero());
			if (table != null) {
				table.setOccupee(true);
			}
		}
	}

	/**
	 * Initialise les tables avec leurs numéros et capacités.
	 */
	private void initialiserTables() {
		tables.clear();
		tables.add(new Table(1, 4));
		tables.add(new Table(2, 2));
		tables.add(new Table(3, 6));
		tables.add(new Table(4, 4));
		tables.add(new Table(5, 2));
		tables.add(new Table(6, 8));
	}

	/**
	 * Lancement de l'interface principale.
	 */
	public void lancer() {
		UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
		UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 13));

		boolean continuer = true;
		while (continuer) {
			String[] options = { "Gérer les Réservations", "Gérer les Commandes", "Gérer le Menu", "Gérer le Personnel",
					"Gérer les Ingrédients", "Quitter" };

			int choix = JOptionPane.showOptionDialog(null,
					"Bienvenue dans le Système de Gestion du Restaurant\n\nVeuillez sélectionner une option :",
					"Menu Principal", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
					options[0]);

			switch (choix) {
			case 0 -> gererReservations();
			case 1 -> gererCommandes();
			case 2 -> gererMenu();
			case 3 -> gererPersonnel();
			case 4 -> gererIngredients();
			case 5, JOptionPane.CLOSED_OPTION -> continuer = false;
			default -> continuer = false;
			}
		}

		stockage.sauvegarderPersonnel(personnel);
		stockage.sauvegarderReservations();
		stockage.sauvegarderCommandes();
		stockage.sauvegarderIngredients();

		JOptionPane.showMessageDialog(null, "Merci d'avoir utilisé notre système. Au revoir !");
	}

	/**
	 * Gestion du personnel (affichage, ajout, suppression).
	 */
	private void gererPersonnel() {
		String[] options = { "Afficher Personnel", "Ajouter Employé", "Supprimer Employé", "Retour" };
		boolean continuer = true;
		while (continuer) {
			int choix = JOptionPane.showOptionDialog(null, "Gestion du Personnel", "Personnel",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			switch (choix) {
			case 0 -> afficherPersonnel();
			case 1 -> ajouterEmploye();
			case 2 -> supprimerEmploye();
			case 3, JOptionPane.CLOSED_OPTION -> continuer = false;
			default -> continuer = false;
			}
		}
	}

	/**
	 * Ajoute un employé au personnel.
	 */
	private void ajouterEmploye() {
		String nom = JOptionPane.showInputDialog("Nom de l'employé :");
		if (nom == null || nom.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nom invalide.");
			return;
		}

		String[] roles = { "Serveur", "Chef", "Manager" };
		int choixRole = JOptionPane.showOptionDialog(null, "Sélectionnez un rôle :", "Rôle", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

		Employe employe = switch (choixRole) {
		case 0 -> new Serveur(nom, choixRole, tables);
		case 1 -> new Chef(nom, choixRole, tables);
		case 2 -> new Manager(nom, choixRole, tables);
		default -> null;
		};

		if (employe != null) {
			personnel.add(employe);
			JOptionPane.showMessageDialog(null, "Employé ajouté : " + employe.getNom());
		} else {
			JOptionPane.showMessageDialog(null, "Aucun employé ajouté.");
		}
	}

	/**
	 * Affiche le personnel enregistré.
	 */
	private void afficherPersonnel() {
		if (personnel.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Aucun employé enregistré.");
			return;
		}
		StringBuilder sb = new StringBuilder("Personnel actuel :\n");
		for (Employe e : personnel) {
			sb.append("- ").append(e.getNom()).append("\n");
		}
		JOptionPane.showMessageDialog(null, sb.toString());
	}

	/**
	 * Supprime un employé du personnel.
	 */
	private void supprimerEmploye() {
		if (personnel.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Aucun employé à supprimer.");
			return;
		}

		String nom = JOptionPane.showInputDialog("Nom de l'employé à supprimer :");
		if (nom == null || nom.trim().isEmpty())
			return;

		Employe toRemove = null;
		for (Employe e : personnel) {
			if (e.getNom().equalsIgnoreCase(nom)) {
				toRemove = e;
				break;
			}
		}

		if (toRemove != null) {
			personnel.remove(toRemove);
			JOptionPane.showMessageDialog(null, "Employé supprimé : " + toRemove.getNom());
		} else {
			JOptionPane.showMessageDialog(null, "Employé non trouvé.");
		}
	}

	/**
	 * Gestion des ingrédients (affichage, ajout, suppression).
	 */
	private void gererIngredients() {
		String[] options = { "Afficher Ingrédients", "Ajouter Ingrédient", "Supprimer Ingrédient", "Retour" };
		boolean continuer = true;
		while (continuer) {
			int choix = JOptionPane.showOptionDialog(null, "Gestion des Ingrédients", "Ingrédients",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			switch (choix) {
			case 0 -> afficherIngredients();
			case 1 -> ajouterIngredient();
			case 2 -> supprimerIngredient();
			case 3, JOptionPane.CLOSED_OPTION -> continuer = false;
			default -> continuer = false;
			}
		}
	}

	/**
	 * Affiche la liste des ingrédients disponibles.
	 */
	private void afficherIngredients() {
		List<Ingredient> ingredients = stockage.getIngredients();
		if (ingredients.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Aucun ingrédient disponible.");
			return;
		}
		StringBuilder sb = new StringBuilder("Ingrédients disponibles :\n");
		for (Ingredient ingredient : ingredients) {
			sb.append("- ").append(ingredient).append("\n");
		}
		JOptionPane.showMessageDialog(null, sb.toString());
	}

	/**
	 * Ajoute un ingrédient au stock.
	 */
	private void ajouterIngredient() {
		String nom = JOptionPane.showInputDialog("Nom de l'ingrédient :");
		if (nom == null || nom.trim().isEmpty())
			return;

		String unite = JOptionPane.showInputDialog("Choix de l'unité :");
		String quantiteStr = JOptionPane.showInputDialog("Quantité :");

		try {
			int quantite = Integer.parseInt(quantiteStr);
			Ingredient ingredient = new Ingredient(nom, quantite, unite);
			stockage.ajouterIngredient(ingredient);
			JOptionPane.showMessageDialog(null, "Ingrédient ajouté : " + ingredient);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Quantité invalide. Veuillez entrer un nombre.");
		}
	}

	/**
	 * Supprime un ingrédient du stock.
	 */
	private void supprimerIngredient() {
		String nom = JOptionPane.showInputDialog("Nom de l'ingrédient à supprimer :");
		if (nom == null || nom.trim().isEmpty())
			return;

		Ingredient toRemove = null;
		for (Ingredient ingredient : stockage.getIngredients()) {
			if (ingredient.getNom().equalsIgnoreCase(nom)) {
				toRemove = ingredient;
				break;
			}
		}

		if (toRemove != null) {
			stockage.supprimerIngredient(toRemove);
			JOptionPane.showMessageDialog(null, "Ingrédient supprimé : " + toRemove.getNom());
		} else {
			JOptionPane.showMessageDialog(null, "Ingrédient non trouvé.");
		}
	}

	/**
	 * Gestion des réservations (création de nouvelles réservations).
	 */
	private void gererReservations() {
		String nom = JOptionPane.showInputDialog("Nom du client :");
		if (nom == null || nom.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nom invalide.");
			return;
		}

		String tel = JOptionPane.showInputDialog("Téléphone du client :");
		if (tel == null || tel.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Téléphone invalide.");
			return;
		}

		Client client = new Client(nom, tel);
		clients.add(client);

		String tablesDispo = getTablesDisponibles();
		String numeroStr = JOptionPane.showInputDialog("Tables disponibles :\n" + tablesDispo + "\nNuméro de table :");

		if (numeroStr == null || numeroStr.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Numéro de table invalide.");
			return;
		}

		try {
			int numero = Integer.parseInt(numeroStr);
			Table table = trouverTableParNumero(numero);
			if (table != null && facade.reserverTable(table, client)) {
				Reservation reservation = new Reservation(client, table);
				stockage.getReservations().add(reservation);
				JOptionPane.showMessageDialog(null, "Réservation réussie !");
			} else {
				JOptionPane.showMessageDialog(null, "Échec de la réservation.");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Veuillez entrer un numéro de table valide.");
		}
	}

	/**
	 * Gestion des commandes (prise de commande, gestion de l'état et paiement).
	 */
	private void gererCommandes() {
		String nomClient = JOptionPane.showInputDialog("Nom du client :");
		if (nomClient == null || nomClient.trim().isEmpty())
			return;

		Client client = trouverClientParNom(nomClient);
		if (client == null) {
			JOptionPane.showMessageDialog(null, "Client non trouvé.");
			return;
		}

		String tablesOccupees = getTablesOccupees();
		String numeroStr = JOptionPane.showInputDialog("Tables occupées :\n" + tablesOccupees + "\nNuméro de table :");

		if (numeroStr == null || numeroStr.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Numéro invalide.");
			return;
		}

		Table table = null;
		try {
			int numero = Integer.parseInt(numeroStr);
			table = trouverTableParNumero(numero);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Veuillez entrer un numéro valide.");
			return;
		}

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
		Facture factureAvecTVA = new TVAFactureDecorator(facture);
		factureAvecTVA.setPayee(true);

		StringBuilder message = new StringBuilder();
		message.append("----- Facture -----\n");
		message.append(facture.getCommande().toString()).append("\n");
		double tva = facture.getMontantAPayer() * 0.20;
		double montantTTC = factureAvecTVA.getMontantAPayer();
		message.append("Montant HT : ").append(facture.getMontantAPayer()).append(" €\n");
		message.append("TVA (20%) : ").append(String.format("%.2f", tva)).append(" €\n");
		message.append("Montant TTC : ").append(String.format("%.2f", montantTTC)).append(" €\n");
		message.append("Statut : Payée");

		JOptionPane.showMessageDialog(null, message.toString(), "Facture avec TVA", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Gestion du menu (ajout et affichage des articles).
	 */
	private void gererMenu() {
		boolean continuer = true;
		while (continuer) {
			String[] options = { "Ajouter Plat", "Ajouter Entrée", "Ajouter Dessert", "Ajouter Boisson",
					"Afficher Menu", "Retour" };
			int choix = JOptionPane.showOptionDialog(null, "Gestion du Menu", "Menu", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			switch (choix) {
			case 0 -> ajouterEntree();
			case 1 -> ajouterPlat();
			case 2 -> ajouterDessert();
			case 3 -> ajouterBoisson();
			case 4 -> afficherMenu();
			case 5, JOptionPane.CLOSED_OPTION -> continuer = false;
			default -> continuer = false;
			}
		}
	}

	/**
	 * Ajoute un plat au menu.
	 */
	private void ajouterPlat() {
		MenuItem plat = creerMenuItem("Plat", false);
		if (plat != null) {
			stockage.ajouterMenu(plat);
			JOptionPane.showMessageDialog(null, "Plat ajouté : " + plat);
		}
	}

	/**
	 * Ajoute une entrée au menu.
	 */
	private void ajouterEntree() {
		MenuItem entree = creerMenuItem("Entrée", false);
		if (entree != null) {
			stockage.ajouterMenu(entree);
			JOptionPane.showMessageDialog(null, "Entrée ajoutée : " + entree);
		}
	}

	/**
	 * Ajoute un dessert au menu.
	 */
	private void ajouterDessert() {
		MenuItem dessert = creerMenuItem("Dessert", false);
		if (dessert != null) {
			stockage.ajouterMenu(dessert);
			JOptionPane.showMessageDialog(null, "Dessert ajouté : " + dessert);
		}
	}

	/**
	 * Ajoute une boisson au menu.
	 */
	private void ajouterBoisson() {
		MenuItem boisson = creerMenuItem("Boisson", true);
		if (boisson != null) {
			stockage.ajouterMenu(boisson);
			JOptionPane.showMessageDialog(null, "Boisson ajoutée : " + boisson);
		}
	}

	/**
	 * Affiche la liste complète des articles du menu.
	 */
	private void afficherMenu() {
		List<MenuItem> menus = stockage.getMenus();
		if (menus.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Aucun article dans le menu.");
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Menu Actuel :\n--------------------------\n");
		for (MenuItem item : menus) {
			sb.append("- ").append(item).append("\n");
		}
		JOptionPane.showMessageDialog(null, sb.toString());
	}

	/**
	 * Crée un nouvel article de menu (MenuItem) en fonction du type choisi.
	 *
	 * @param type
	 * @param demandeAlcool
	 * @return
	 */
	private MenuItem creerMenuItem(String type, boolean demandeAlcool) {
		String nom = JOptionPane.showInputDialog("Nom du " + type + " :");
		if (nom == null || nom.trim().isEmpty())
			return null;

		String prixStr = JOptionPane.showInputDialog("Prix du " + type + " :");
		double prix;
		try {
			prix = Double.parseDouble(prixStr);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Prix invalide.");
			return null;
		}

		String description = JOptionPane.showInputDialog("Description :");

		List<Ingredient> ingredients = new ArrayList<>();
		int ajouter = JOptionPane.showConfirmDialog(null, "Souhaitez-vous ajouter des ingrédients à ce " + type + " ?");
		if (ajouter == JOptionPane.YES_OPTION) {
			List<Ingredient> tousLesIngredients = stockage.getIngredients();
			if (tousLesIngredients.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Aucun ingrédient disponible !");
			} else {
				boolean continuerAjout = true;
				while (continuerAjout) {
					StringBuilder sb = new StringBuilder("Ingrédients disponibles :\n");
					for (Ingredient ingr : tousLesIngredients) {
						sb.append("- ").append(ingr.getNom()).append("\n");
					}
					String nomIngredient = JOptionPane.showInputDialog(null,
							sb.toString() + "\nEntrez un ingrédient (ou vide pour terminer) :");

					if (nomIngredient == null || nomIngredient.trim().isEmpty()) {
						continuerAjout = false;
					} else {
						Ingredient ingredientTrouve = null;
						for (Ingredient ingr : tousLesIngredients) {
							if (ingr.getNom().equalsIgnoreCase(nomIngredient.trim())) {
								ingredientTrouve = ingr;
								break;
							}
						}
						if (ingredientTrouve != null) {
							ingredients.add(ingredientTrouve);
							JOptionPane.showMessageDialog(null, "Ingrédient ajouté : " + ingredientTrouve.getNom());
						} else {
							JOptionPane.showMessageDialog(null, "Ingrédient non trouvé !");
						}
					}
				}
			}
		}

		boolean flag = false;
		if (type.equalsIgnoreCase("Boisson")) {
			int alcoolOption = JOptionPane.showConfirmDialog(null, "Est-ce une boisson alcoolisée ?");
			flag = (alcoolOption == JOptionPane.YES_OPTION);
		} else if (type.equalsIgnoreCase("Dessert")) {
			int sucreOption = JOptionPane.showConfirmDialog(null, "Le dessert est-il sucré ?");
			flag = (sucreOption == JOptionPane.YES_OPTION);
		} else if (type.equalsIgnoreCase("Entrée")) {
			int froideOption = JOptionPane.showConfirmDialog(null, "L'entrée est-elle froide ?");
			flag = (froideOption == JOptionPane.YES_OPTION);
		} else if (type.equalsIgnoreCase("Plat")) {
			int vegeOption = JOptionPane.showConfirmDialog(null, "Le plat est-il végétarien ?");
			flag = (vegeOption == JOptionPane.YES_OPTION);
		}

		return MenuItemFactory.createMenuItem(type, nom, prix, description, flag, ingredients);
	}

	/**
	 * Permet à l'utilisateur de sélectionner les articles d'une commande.
	 */
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

	/**
	 * Recherche un article de menu par son nom.
	 * 
	 * @param nom
	 * @return
	 */
	private MenuItem trouverMenuItemParNom(String nom) {
		for (MenuItem item : stockage.getMenus()) {
			if (item.getNom().equalsIgnoreCase(nom)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Récupère la liste des tables disponibles (non occupées).
	 */
	private String getTablesDisponibles() {
		StringBuilder sb = new StringBuilder();
		for (Table table : tables) {
			if (!table.isOccupee())
				sb.append(table).append("\n");
		}
		return sb.length() > 0 ? sb.toString() : "Aucune table disponible";
	}

	/**
	 * Récupère la liste des tables actuellement occupées.
	 */
	private String getTablesOccupees() {
		StringBuilder sb = new StringBuilder();
		for (Table table : tables) {
			if (table.isOccupee()) {
				Client client = trouverClientParTable(table);
				String nomClient = (client != null) ? client.getNom() : "Inconnu";
				sb.append("Table ").append(table.getNumero()).append(" (Capacité: ").append(table.getCapacite())
						.append(", Réservée par: ").append(nomClient).append(")\n");
			}
		}
		return sb.length() > 0 ? sb.toString() : "Aucune table occupée";
	}

	/**
	 * Trouve une table par son numéro.
	 * 
	 * @param numero
	 * @return
	 */
	private Table trouverTableParNumero(int numero) {
		for (Table table : tables) {
			if (table.getNumero() == numero)
				return table;
		}
		return null;
	}

	/**
	 * Trouve un client par son nom.
	 * 
	 * @param nom
	 * @return
	 */
	private Client trouverClientParNom(String nom) {
		for (Client client : clients) {
			if (client.getNom().equalsIgnoreCase(nom))
				return client;
		}
		return null;
	}

	/**
	 * Trouve le client associé à une table donnée.
	 * 
	 * @param table
	 * @return
	 */
	private Client trouverClientParTable(Table table) {
		for (Reservation r : stockage.getReservations()) {
			if (r.getTable().getNumero() == table.getNumero()) {
				return r.getClient();
			}
		}
		return null;
	}

	/**
	 * Choisit la méthode de paiement pour la commande.
	 */
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

}
