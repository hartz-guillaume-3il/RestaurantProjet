package model;

import java.util.List;
import state.EtatCommande;

public class Commande {
    private int id;
    private Table table;
    private List<MenuItem> items;
    private EtatCommande etat; 
    private double montantTotal;

    public void ajouterItem(MenuItem item) {}
    public void calculerMontant() {}
}

