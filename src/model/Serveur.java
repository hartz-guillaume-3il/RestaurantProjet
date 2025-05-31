package model;

import java.util.List;

public class Serveur extends Employe {
	private List<Table> tablesAssignees;

	public Serveur(String nom, int id, List<Table> tablesAssignees) {
		super(nom, id, "Serveur");
		this.tablesAssignees = tablesAssignees;
	}

	@Override
	public void afficherInfos() {
		System.out.println("Serveur : " + nom + " (ID: " + id + ")");
		if (tablesAssignees == null || tablesAssignees.isEmpty()) {
			System.out.println("Aucune table assignée.");
		} else {
			System.out.println("Tables assignées :");
			for (Table table : tablesAssignees) {
				System.out.println(" - Table " + table.getNumero() + " (" + table.getCapacite() + " places)");
			}
		}
	}

}
