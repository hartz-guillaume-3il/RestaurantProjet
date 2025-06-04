package observer;

import model.Commande;

//Interface de base pour tous les observateurs ( cuisine ..)
public interface Observer {
	void update(Commande commande);
}
