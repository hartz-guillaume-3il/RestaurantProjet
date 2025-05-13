package state;

import model.Commande;

public class Livree extends EtatCommande {

    public Livree(Commande commande) {
        super(commande);
    }

    @Override
    public void avancerEtat() {
        commande.setEtat(new Payee(commande));
        System.out.println("Commande #" + commande.getId() + " a été payée.");
    }

    @Override
    public String getNomEtat() {
        return "Livrée";
    }
}
