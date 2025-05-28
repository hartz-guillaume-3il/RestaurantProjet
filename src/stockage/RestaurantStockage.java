package stockage;

import model.Boisson;
import model.Dessert;
import model.Entree;
import model.MenuItem;
import model.Plat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantStockage {

	private List<MenuItem> menus;
	private final String fichier = "menus.txt";

	public RestaurantStockage() {
		this.menus = new ArrayList<>();
		chargerMenus();
	}

	public void ajouterMenu(MenuItem menu) {
		menus.add(menu);
		sauvegarderMenus();
	}

	public void supprimerMenu(MenuItem menu) {
		menus.remove(menu);
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
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
			for (MenuItem menu : menus) {
				writer.write(menu.toString());
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erreur de sauvegarde : " + e.getMessage());
		}
	}

	private void chargerMenus() {
		File f = new File(fichier);
		if (!f.exists()) {
			return;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			while ((ligne = reader.readLine()) != null) {
				MenuItem menuEntree = new Entree(ligne);
				MenuItem menuPlat = new Plat(ligne);
				MenuItem menuDessert = new Dessert(ligne);
				MenuItem menuBoisson = new Boisson(ligne);
				menus.add(menuEntree);
				menus.add(menuPlat);
				menus.add(menuDessert);
				menus.add(menuBoisson);
			}
		} catch (IOException e) {
			System.err.println("Erreur de chargement : " + e.getMessage());
		}
	}
}
