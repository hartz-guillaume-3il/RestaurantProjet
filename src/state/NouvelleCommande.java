package state;

import model.Commande;

public class NouvelleCommande extends EtatCommande {

    public NouvelleCommande(Commande commande) {
        super(commande);
    }

    @Override
    public void avancerEtat() {
        commande.setEtat(new EnPreparation(commande));
        System.out.println("Commande #" + commande.getId() + " passe en préparation.");
    }

    @Override
    public String getNomEtat() {
        return "Nouvelle";
    }
}
