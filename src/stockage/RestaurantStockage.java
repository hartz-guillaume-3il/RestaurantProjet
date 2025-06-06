package stockage;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantStockage {
	private List<MenuItem> menus;
	private List<Ingredient> ingredients;
	private List<Reservation> reservations;
	private List<Commande> commandes;
	private final String fichierSauvegardeMenus = "menus.txt";
	private final String fichierSauvegardeIngredients = "ingredients.txt";
	private static final String fichierSauvegardeCommandes = "commandes.txt";
	private static final String fichierSauvegardeReservations = "reservations.txt";
	private static final String fichierPersonnel = "personnel.txt";

	public RestaurantStockage() {
		this.menus = new ArrayList<>();
		this.ingredients = new ArrayList<>();
		this.commandes = new ArrayList<>();
		this.reservations = new ArrayList<>();

		chargerMenus();
		chargerIngredients();
		chargerCommandes();
		chargerReservations();
	}

	public void ajouterMenu(MenuItem menu) {
		if (!menus.contains(menu)) {
			menus.add(menu);
			sauvegarderMenus();
		}
	}

	public void supprimerMenu(MenuItem menu) {
		menus.remove(menu);
		sauvegarderMenus();
	}

	public List<MenuItem> getMenus() {
		return menus;
	}

	private void sauvegarderMenus() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierSauvegardeMenus))) {
			for (MenuItem menu : menus) {
				StringBuilder sb = new StringBuilder();
				sb.append(menu.getType()).append(";").append(menu.getNom()).append(";").append(menu.getPrix())
						.append(";").append(menu.getDescription()).append(";");

				if (menu instanceof Boisson boisson) {
					sb.append(boisson.isAlcoolisee());
				} else if (menu instanceof Entree entree) {
					sb.append(entree.isFroide());
				} else if (menu instanceof Plat plat) {
					sb.append(plat.isVegetarien());
				} else if (menu instanceof Dessert dessert) {
					sb.append(dessert.isSucre());
				} else {
					sb.append("false");
				}

				writer.write(sb.toString());
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la sauvegarde des menus : " + e.getMessage());
		}
	}

	private void chargerMenus() {
		File fichier = new File(fichierSauvegardeMenus);
		if (!fichier.exists())
			return;

		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			while ((ligne = reader.readLine()) != null) {
				String[] parties = ligne.split(";");
				if (parties.length >= 5) {
					String type = parties[0].trim();
					String nom = parties[1].trim();
					double prix = Double.parseDouble(parties[2].trim());
					String description = parties[3].trim();
					boolean attribut = Boolean.parseBoolean(parties[4].trim());

					MenuItem item = switch (type) {
					case "Plat" -> new Plat(nom, prix, description, attribut, new ArrayList<>());
					case "Entrée" -> new Entree(nom, prix, description, attribut, new ArrayList<>());
					case "Dessert" -> new Dessert(nom, prix, description, attribut, new ArrayList<>());
					case "Boisson" -> new Boisson(nom, prix, description, attribut, new ArrayList<>());
					default -> null;
					};

					if (item != null)
						menus.add(item);
				}
			}
		} catch (IOException | NumberFormatException e) {
			System.err.println("Erreur lors du chargement des menus : " + e.getMessage());
		}
	}

	public void ajouterIngredient(Ingredient ingredient) {
		if (!ingredients.contains(ingredient)) {
			ingredients.add(ingredient);
			sauvegarderIngredients();
		}
	}

	public void supprimerIngredient(Ingredient ingredient) {
		ingredients.remove(ingredient);
		sauvegarderIngredients();
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void sauvegarderIngredients() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierSauvegardeIngredients))) {
			for (Ingredient ingredient : ingredients) {
				writer.write(ingredient.getNom() + ";" + ingredient.getQuantiteStock() + ";" + ingredient.getUnite());
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la sauvegarde des ingrédients : " + e.getMessage());
		}
	}

	private void chargerIngredients() {
		File fichier = new File(fichierSauvegardeIngredients);
		if (!fichier.exists())
			return;

		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			while ((ligne = reader.readLine()) != null) {
				String[] parts = ligne.split(";");
				if (parts.length >= 2) {
					String nom = parts[0].trim();
					int quantite = Integer.parseInt(parts[1].trim());
					String unite = parts.length >= 3 ? parts[2].trim() : "";
					ingredients.add(new Ingredient(nom, quantite, unite));
				}
			}
		} catch (IOException e) {
			System.err.println("Erreur lors du chargement des ingrédients : " + e.getMessage());
		}
	}

	public void sauvegarderCommandes() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierSauvegardeCommandes))) {
			for (Commande commande : commandes) {
				StringBuilder ligne = new StringBuilder();

				String clientNom = (commande.getClient() != null) ? commande.getClient().getNom() : "inconnu";
				String numeroTable = (commande.getTable() != null) ? String.valueOf(commande.getTable().getNumero())
						: "0";
				String montant = String.valueOf(commande.getMontantTotal());
				String etat = commande.getEtat() != null ? commande.getEtat().getClass().getSimpleName()
						: "NouvelleCommande";

				ligne.append(commande.getId()).append(";").append(clientNom).append(";").append(numeroTable).append(";")
						.append(montant).append(";").append(etat).append(";");

				List<MenuItem> items = commande.getItems();
				for (int i = 0; i < items.size(); i++) {
					ligne.append(items.get(i).getNom());
					if (i < items.size() - 1) {
						ligne.append(",");
					}
				}

				writer.write(ligne.toString());
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la sauvegarde des commandes : " + e.getMessage());
		}
	}

	public void chargerCommandes() {
		File fichier = new File(fichierSauvegardeCommandes);
		commandes.clear();

		if (!fichier.exists())
			return;

		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			while ((ligne = reader.readLine()) != null) {
				String[] parts = ligne.split(";");
				if (parts.length == 4) {
					String nomClient = parts[0].trim();
					String telClient = parts[1].trim();
					String etat = parts[2].trim();
					String[] nomsItems = parts[3].split(",");

					Client client = new Client(nomClient, telClient);
					Commande commande = new Commande(client, null);

					for (String nom : nomsItems) {
						MenuItem item = trouverMenuItemParNom(nom);
						if (item != null)
							commande.ajouterItem(item);
					}

					commande.setEtatParNom(etat);
					commandes.add(commande);
				}
			}
		} catch (IOException e) {
			System.err.println("Erreur lors du chargement des commandes : " + e.getMessage());
		}
	}

	private MenuItem trouverMenuItemParNom(String nom) {
		for (MenuItem item : menus) {
			if (item.getNom().equalsIgnoreCase(nom)) {
				return item;
			}
		}
		return null;
	}

	public void sauvegarderReservations() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierSauvegardeReservations))) {
			for (Reservation reservation : reservations) {
				String nomClient = reservation.getNomClient() != null ? reservation.getNomClient() : "inconnu";
				String dateHeureStr = (reservation.getDateHeure() != null)
						? String.valueOf(reservation.getDateHeure().getTime())
						: "0";
				String telephoneStr = String.valueOf(reservation.getTelephone());
				String nbPersonnesStr = String.valueOf(reservation.getNombrePersonnes());
				String tableStr = (reservation.getTable() != null) ? String.valueOf(reservation.getTable().getNumero())
						: "0";

				writer.write(
						nomClient + ";" + dateHeureStr + ";" + telephoneStr + ";" + nbPersonnesStr + ";" + tableStr);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la sauvegarde des réservations : " + e.getMessage());
		}
	}

	public void chargerReservations() {
		File fichier = new File(fichierSauvegardeReservations);
		reservations.clear();

		if (!fichier.exists())
			return;

		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			while ((ligne = reader.readLine()) != null) {
				String[] parts = ligne.split(";");
				if (parts.length == 3) {
					String nom = parts[0].trim();
					String tel = parts[1].trim();
					int numeroTable = Integer.parseInt(parts[2].trim());

					Client client = new Client(nom, tel);
					Table table = new Table(numeroTable, 4);
					reservations.add(new Reservation(client, table));
				}
			}
		} catch (IOException e) {
			System.err.println("Erreur lors du chargement des réservations : " + e.getMessage());
		}
	}

	public void sauvegarderPersonnel(List<Employe> personnel) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierPersonnel))) {
			for (Employe e : personnel) {
				writer.write(e.getClass().getSimpleName() + ";" + e.getNom());
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la sauvegarde du personnel : " + e.getMessage());
		}
	}

	public List<Employe> chargerPersonnel() {
		List<Employe> personnel = new ArrayList<>();
		File fichier = new File(fichierPersonnel);
		if (!fichier.exists())
			return personnel;

		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			while ((ligne = reader.readLine()) != null) {
				String[] parties = ligne.split(";");
				if (parties.length == 2) {
					String type = parties[0].trim();
					String nom = parties[1].trim();

					Employe e = switch (type.toLowerCase()) {
					case "serveur" -> new Serveur(nom, 0, null);
					case "chef" -> new Chef(nom, 0, null);
					case "manager" -> new Manager(nom, 0, null);
					default -> null;
					};

					if (e != null)
						personnel.add(e);
				}
			}
		} catch (IOException e) {
			System.err.println("Erreur lors du chargement du personnel : " + e.getMessage());
		}

		return personnel;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}

	public void setMenus(List<MenuItem> menus) {
		this.menus = menus;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

}
