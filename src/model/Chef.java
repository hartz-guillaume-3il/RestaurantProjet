package model;

import java.util.List;

public class Chef extends Employe {
	private List<Table> tablesAssignees;

	public Chef(String nom, int id, List<Table> tablesAssignees) {
		super(nom, id, "Chef");
		this.tablesAssignees = tablesAssignees;
	}

	@Override
	public void afficherInfos() {
	    System.out.println("Chef : " + nom + " (ID: " + id + ")");
	    if (tablesAssignees == null || tablesAssignees.isEmpty()) {
	        System.out.println("Aucune table supervisée.");
	    } else {
	        System.out.println("Tables supervisées :");
	        for (Table table : tablesAssignees) {
	            System.out.println(" - Table " + table.getNumero() + " (" + table.getCapacite() + " places)");
	        }
	    }
	}

}