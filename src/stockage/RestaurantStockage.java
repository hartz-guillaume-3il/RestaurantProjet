package stockage;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantStockage {
	private List<MenuItem> menus;
	private List<Ingredient> ingredients; 
	private final String fichierSauvegardeMenus = "menus.txt";
	private final String fichierSauvegardeIngredients = "ingredients.txt";

	public RestaurantStockage() {
		this.menus = new ArrayList<>();
		this.ingredients = new ArrayList<>();
		chargerMenus();
		chargerIngredients(); 
	}

	public void ajouterMenu(MenuItem menu) {
		this.menus.add(menu);
		sauvegarderMenus();
	}

	public void supprimerMenu(MenuItem menu) {
		this.menus.remove(menu);
		sauvegarderMenus();
	}

	public List<MenuItem> getMenus() {
		return menus;
	}

	public void afficherMenus() {
		for (MenuItem menu : menus) {
			System.out.println(menu);
		}
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
		if (!fichier.exists()) {
			System.err.println("Fichier menus.txt introuvable !");
			return;
		}
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

					MenuItem item = null;
					switch (type) {
					case "Plat" -> item = new Plat(nom, prix, description, attribut, new ArrayList<>());
					case "Entrée" -> item = new Entree(nom, prix, description, attribut, new ArrayList<>());
					case "Dessert" -> item = new Dessert(nom, prix, description, attribut, new ArrayList<>());
					case "Boisson" -> item = new Boisson(nom, prix, description, attribut, new ArrayList<>());
					default -> System.err.println("Type inconnu : " + type);
					}

					if (item != null) {
						menus.add(item);
					}
				}
			}
		} catch (IOException | NumberFormatException e) {
			System.err.println("Erreur lors du chargement des menus : " + e.getMessage());
		}
	}

	public void ajouterIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
		sauvegarderIngredients();
	}

	public void supprimerIngredient(Ingredient ingredient) {
		this.ingredients.remove(ingredient);
		sauvegarderIngredients();
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void afficherIngredients() {
		for (Ingredient ingredient : ingredients) {
			System.out.println(ingredient);
		}
	}

	private void sauvegarderIngredients() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierSauvegardeIngredients))) {
			for (Ingredient ingredient : ingredients) {
				writer.write(ingredient.getNom() + ";" + ingredient.getQuantiteStock());
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la sauvegarde des ingrédients : " + e.getMessage());
		}
	}

	private void chargerIngredients() {
		File fichier = new File(fichierSauvegardeIngredients);
		if (!fichier.exists()) {
			System.err.println("Fichier ingredients.txt introuvable !");
			return;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			while ((ligne = reader.readLine()) != null) {
				String[] parties = ligne.split(";");
				if (parties.length == 2) {
					String nom = parties[0].trim();
					int quantite = Integer.parseInt(parties[1].trim());
					Ingredient ingredient = new Ingredient(nom, quantite);
					ingredients.add(ingredient);
				}
			}
		} catch (IOException | NumberFormatException e) {
			System.err.println("Erreur lors du chargement des ingrédients : " + e.getMessage());
		}
	}
}
