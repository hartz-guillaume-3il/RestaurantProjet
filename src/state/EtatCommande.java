package state;

import model.Commande;

public abstract class EtatCommande {
    protected Commande commande;

    public EtatCommande(Commande commande) {
        this.commande = commande;
    }

    public abstract void avancerEtat();
    public abstract String getNomEtat();
}
