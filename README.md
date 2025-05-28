
RestaurantProjet - Gestion de restaurant avec Java

Ce projet illustre l'utilisation de quatre design patterns classiques dans le contexte de la gestion d'un restaurant :
- Strategy : permet de changer dynamiquement les stratégies de traitement (exemple : choix du mode de paiement).
- State : gère les états d'un objet (exemple : statut d'une commande).
- Decorator : ajoute des fonctionnalités supplémentaires aux objets (exemple : options d'une commande).
- Façade : fournit une interface simplifiée pour interagir avec un système complexe (exemple : interactions entre les différentes classes du restaurant).

Technologies utilisées :
- Langage : Java
- Paradigmes : Programmation orientée objet, patterns de conception

Fonctionnalités :
- Gestion des commandes selon leur état.
- Ajout dynamique de fonctionnalités (comme des extras à un plat).
- Choix de stratégies de paiement interchangeables.
- Interface simplifiée pour le client via le pattern Façade.

Structure du projet :
RestaurantProjet/
- src/
  - strategy/
  - state/
  - decorator/
  - facade/
  - Application.java
- diagramme.png
- README.md

Exécution :
1. Importer le projet dans votre IDE Java préféré (Eclipse, IntelliJ, NetBeans...).
2. Compiler les classes.
3. Exécuter la classe Application.java pour tester les fonctionnalités.

Auteur(e)s :
- Hartz Guillaume
- Rahou Hind

Licence :
Consultez le fichier LICENSE pour plus d'informations.
