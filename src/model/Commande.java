package model;

import java.util.ArrayList;
import java.util.List;

import observer.Contexte;
import observer.Observer;
import state.EtatCommande;
import state.NouvelleCommande;

public class Commande implements Contexte {
	private static int compteurId = 1;
	private int id;
	private List<MenuItem> items;
	private Table table;
	private EtatCommande etat;
	private Client client;
	private double montantTotal;
	private List<Observer> observers = new ArrayList<>();

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

	public double calculerMontant() {
		montantTotal = 0;
		for (MenuItem item : items) {
			montantTotal += item.getPrix();
		}
		return montantTotal;
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

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		observers.add(observer);

	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		observers.remove(observer);

	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for (Observer observer : observers) {
			observer.update(this);
		}

	}
}
