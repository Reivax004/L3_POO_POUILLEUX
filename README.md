# L3_POO_REGLES

## Règles
Le jeu se joue de 2 à 8 joueurs et comporte un maître du jeu.

---

### Mise en place
- Le jeu utilise un jeu de 51 cartes (le Valet de trèfle est retiré).
- Le maître du jeu distribue équitablement les cartes aux joueurs.  
  Exemple : avec 2 joueurs, J1 reçoit 25 cartes et J2 en reçoit 26.

---

### Déroulé de la partie
1. **Distribution des cartes**  
   Le maître du jeu distribue toutes les cartes.
   Chaque joueur commence par retirer les paires (même valeur et même couleur).
   Les cartes défaussées sont mises de côté.

2. **Tour de jeu**  
   - Le joueur actif pioche une carte dans le jeu du joueur suivant.  
   - Il vérifie s’il peut former une ou plusieurs paires et les défausse immédiatement.  
   - Le joueur peut poser une **paire spéciale** pour déclencher un effet *(voir paires spéciales)*.  
   - Les joueurs peuvent poser plusieurs paires de **cartes normales** (2 à 9) en un seul tour.  
   - Pour les **paires spéciales**, une seule peut être jouée par tour.  

3. **Contrôle de fin de partie**  
   - La partie se termine lorsqu’un joueur n’a plus de cartes en main, il est déclaré **vainqueur**.  
   - Le joueur ayant le Valet de pique est déclaré **grand perdant**.

4. **Passage au joueur suivant**  
   Le sens de jeu peut changer si une Reine est posée *(voir paires spéciales)*.
   Sinon, le prochain joueur joue à son tour.

---

### Paires spéciales et effets
Lorsqu’un joueur pose une **paire spéciale**, il déclenche un effet unique.
Seule **une paire spéciale** peut être posée par tour.  

- **10 (Dix)** : Le joueur suivant saute son tour.  
- **Reine** : Change le sens du jeu.  
- **Roi** : Permet d’échanger deux cartes entre deux joueurs de son choix.  
- **Valet** : Le joueur peut choisir une carte dans la main d’un autre joueur de son choix.  
- **As** : Le joueur impose une couleur de paire que les autres doivent poser lors de leurs tours.
  Cet effet dure jusqu’à ce que le tour revienne au joueur qui a posé l'As.  

---

### Fin de la partie
- La partie s’arrête lorsqu’un joueur n’a plus de cartes en main :  
  - Ce joueur est déclaré **vainqueur**.  
  - Si le jeu comporte plus de deux joueurs, le joueur ayant le Valet de pique dans sa main est désigné comme le **grand perdant**.


# Détail des classes principales


# Protocole réseau
> Le protocole réseau définit les séquences des commandes échangées entre les différentes parties prenantes.
> Il doit contenir, pour chaque commande, l'expéditeur, le destinataire, le nom de la commande et le contenu du corps de la commande.
![protocole jack](POUILLEUX.png)

# Diagramme des classes
