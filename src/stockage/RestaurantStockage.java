package stockage;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantStockage {
	private List<MenuItem> menus;
	private final String fichierSauvegarde = "menus.txt";

	public RestaurantStockage() {
		this.menus = new ArrayList<>();
		chargerMenus();
	}

	public void ajouterMenu(MenuItem menu) {
		this.menus.add(menu);
		sauvegarderMenus();
	}

	public List<MenuItem> getMenus() {
		return menus;
	}

	public void supprimerMenu(MenuItem menu) {
		this.menus.remove(menu);
		sauvegarderMenus();
	}

	public void afficherMenus() {
		for (MenuItem menu : menus) {
			System.out.println(menu);
		}
	}

	private void sauvegarderMenus() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierSauvegarde))) {
			for (MenuItem menu : menus) {
				writer.write(menu.getType() + ";" + menu.getNom() + ";" + menu.getPrix() + ";" + menu.getDescription());
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la sauvegarde des menus : " + e.getMessage());
		}
	}

	private void chargerMenus() {
		File fichier = new File(fichierSauvegarde);
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
					MenuItem menuEntree = new Entree(nom, prix, description, false, new ArrayList<>());
					MenuItem menuPlat = new Plat(nom, prix, description, false, new ArrayList<>());
					MenuItem menuDessert = new Dessert(nom, prix, description, false, new ArrayList<>());
					MenuItem menuBoisson = new Boisson(nom, prix, description, false);
					switch (type) {
					case "Plat" -> menus.add(menuPlat);
					case "Entrée" -> menus.add(menuEntree);
					case "Dessert" -> menus.add(menuDessert);
					case "Boisson" -> menus.add(menuBoisson);
					default -> System.err.println("Type inconnu : " + type);
					}
				}
			}
		} catch (IOException | NumberFormatException e) {
			System.err.println("Erreur lors du chargement des menus : " + e.getMessage());
		}
	}

}
