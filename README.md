
# 🍽️ RestaurantProjet - Gestion de restaurant en Java

**RestaurantProjet** est un projet Java qui illustre l’utilisation de quatre **design patterns** classiques : **Strategy**, **State**, **Decorator**, et **Façade**. Le projet met en œuvre ces patterns pour gérer de manière flexible et propre les fonctionnalités d’un restaurant (commandes, états, options, paiements, etc.).

---

## 📦 Fonctionnalités principales

- 🔄 **Gestion des commandes** et de leurs différents états (en préparation, servie, terminée...).
- ➕ **Ajout dynamique d’options** à un plat (par exemple, sauces, suppléments...).
- 💳 **Choix de stratégies de paiement** interchangeables (carte bancaire, PayPal...).
- 🛠️ **Interface simplifiée** grâce au pattern **Façade**.

---

## 🏗️ Technologies utilisées

- **Langage** : Java  
- **Paradigme** : Programmation orientée objet, patterns de conception  
- **Structure du projet** :
  ```
  RestaurantProjet/
  ├── src/
  │   ├── decorator/
  │   ├── facade/
  │   ├── factory/
  │   ├── Main/
  │   ├── model/
  │   ├── observer/
  │   ├── reporting/
  │   ├── state/
  │   ├── stockage/
  │   ├── strategy/
  │   └── ui.console/
  ├── diagramme.png
  ├── menus.txt
  ├── ingredients.txt
  ├── commandes.txt
  ├── personnels.txt
  ├── reservations.txt
  ├── diagramme.png
  ├── README.md
  └── LICENSE
  ```

---

## 🏃‍♂️ Lancer le projet

1️⃣ **Importer** le projet dans votre IDE Java préféré (Eclipse, IntelliJ, NetBeans…).  
2️⃣ **Compiler** les classes.  
3️⃣ **Exécuter** la classe `ConsoleUI.java` pour découvrir les fonctionnalités.

---

## 🖼️ Diagramme UML

Le diagramme des patterns est disponible dans le fichier `diagramme.png` pour visualiser les interactions et l’architecture globale.

---

## 👥 Auteurs

- **Hartz Guillaume**
- **Rahou Hind**

---

## 📜 Licence

Consultez le fichier [`LICENSE`](LICENSE) pour plus d’informations.

---

## 🚀 Pourquoi ce projet ?

Le projet **RestaurantProjet** a été conçu pour démontrer l’articulation et l’utilité de plusieurs patterns de conception (**Strategy**, **State**, **Decorator**, **Façade**) dans un scénario concret de gestion de restaurant. Il offre une base solide pour explorer les concepts de modularité, de flexibilité et de lisibilité dans le développement Java.

---

🎉 N’hésitez pas à cloner le dépôt, l’exécuter et expérimenter avec les différentes stratégies et états pour voir toute la puissance de ces patterns en action ! 🍝
