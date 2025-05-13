package model;

import java.util.ArrayList;
import java.util.List;
import state.EtatCommande;
import state.NouvelleCommande;

public class Commande {
	private static int compteurId = 1;
	private int id;
	private List<MenuItem> items;
	private Table table;
	private EtatCommande etat;
	private Client client;
	private double montantTotal;

	public Commande(Client client, Table table) {
		this.id = compteurId++;
		this.client = client;
		this.table = table;
		this.items = new ArrayList<>();
		this.etat = new NouvelleCommande(this);
	}

	public void ajouterItem(MenuItem item) {
		items.add(item);
		calculerMontant();
	}

	public void calculerMontant() {
		montantTotal = 0;
		for (MenuItem item : items) {
			montantTotal += item.getPrix();
		}
	}

	public int getId() {
		return id;
	}

	public List<MenuItem> getItems() {
		return items;
	}

	public Table getTable() {
		return table;
	}

	public Client getClient() {
		return client;
	}

	public double getMontantTotal() {
		return montantTotal;
	}

	public EtatCommande getEtat() {
		return etat;
	}

	public void setEtat(EtatCommande etat) {
		this.etat = etat;
	}
	
	public void avancerEtat() {
	    etat.avancerEtat();
	}

	@Override
	public String toString() {
		return "Commande #" + id + " pour " + client.getNom() + ", Table " + table.getNumero() + ", Montant : "
				+ montantTotal + "€";
	}
}
