package model;

import java.util.List;

public class Manager extends Employe {
	private List<Table> tablesAssignees;

	public Manager(String nom, int id, List<Table> tablesAssignees) {
		super(nom, id, "Manager");
		this.tablesAssignees = tablesAssignees;
	}

	@Override
	public void afficherInfos() {
		System.out.println("Manager : " + nom + " (ID: " + id + ")");
		if (tablesAssignees == null || tablesAssignees.isEmpty()) {
			System.out.println("Aucune table gérée.");
		} else {
			System.out.println("Tables gérées :");
			for (Table table : tablesAssignees) {
				System.out.println(" - Table " + table.getNumero() + " (" + table.getCapacite() + " places)");
			}
		}
	}

}
