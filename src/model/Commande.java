package model;

import java.util.ArrayList;
import java.util.List;

import observer.Contexte;
import observer.Observer;
import state.EnPreparation;
import state.EtatCommande;
import state.Livree;
import state.NouvelleCommande;
import state.Payee;
import state.Prete;

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
		notifyObservers();
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
		notifyObservers();
	}

	public void setEtatParNom(String nomEtat) {
		switch (nomEtat.toLowerCase()) {
		case "nouvelle", "nouvellecommande" -> this.etat = new NouvelleCommande(this);
		case "en préparation", "en preparation" -> this.etat = new EnPreparation(this);
		case "prête", "prete" -> this.etat = new Prete(this);
		case "livrée", "livree" -> this.etat = new Livree(this);
		case "payée", "payee" -> this.etat = new Payee(this);
		default -> this.etat = new NouvelleCommande(this);
		}
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
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
}
