package fr.pantheonsorbonne.miage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    // Attributs du joueur
    final String name; // Le nom du joueur
    private List<Card> hand; // La main du joueur (tableau de cartes)
    private Random random = new Random();

    // Constructeur
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<Card>(); // Par exemple, une main de 5 cartes
    }

    // Le joueur reçoit cette main de cartes
    public void setHand(List<Card> deck) {
        if (deck.size() != 0) {
            this.hand = deck; // Attribuer les cartes passées en paramètre à la main du joueur
        } else {
            System.out.println("La taille de la main ne correspond pas.");
        }
    }

    // Méthode pour récupérer la main du joueur
    public List<Card> getHand() {
        return this.hand;
    }

    // Méthode pour récupérer le nom du joueur
    public String getName() {
        return this.name;
    }

    // méthode pour afficher la main du joueur en chaîne de caractères
    public String getHandString() {
        if (hand.isEmpty()) {
            return "Aucune carte";
        }
        StringBuilder builder = new StringBuilder();
        for (Card c : this.hand) {
            if (c != null) {
                builder.append("\t - " + c.toString());
            } else {
                builder.append("\t - ");
            }
            builder.append("\n ");
        }
        return builder.toString().substring(0, builder.length() - 2);
    }

    // méthode pour ajouter des cartes à la main du joueur
    public void addCard(List<Card> cards) {
        System.out.println(this.name + " Adding cards in hand: hand before: \n" + this.getHandString());
        int myHandIndex = 0;
        for (int i = 0; i < cards.size(); i++) {
            for (; myHandIndex < this.hand.size(); myHandIndex++) {
                if (this.hand.get(myHandIndex) == null) {
                    this.hand.set(myHandIndex, cards.get(i));
                    break;
                }
            }

        }
        System.out.println(this.name + " Adding cards in hand: hand after: \n" + this.getHandString());
    }

    public String discardPairs() {
        List<Card> cardsToRemove = new ArrayList<>();
        boolean foundTenPair = false;
        boolean foundValetPair = false;
        boolean foundDamePair = false;
        boolean foundRoiPair = false;
        boolean foundAsPair = false;

        // Vérifie chaque carte pour voir si c'est une paire avec une autre
        for (int i = 0; i < hand.size(); i++) {
            Card card1 = hand.get(i);
            for (int j = i + 1; j < hand.size(); j++) {
                Card card2 = hand.get(j);
                // Vérifie si les 2 cartes ont la même valeur et la même couleur
                if (card1.getValue().equals(card2.getValue()) &&
                        card1.getSymbol().getColor().equals(card2.getSymbol().getColor())) {

                    if (cardsToRemove.size() == 0) {

                        if (card1.getValue().ordinal() > 4) { // De 9 à Deux
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        } else if (card1.getValue().ordinal() == 4) { // 10
                            foundTenPair = true;
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        } else if (card1.getValue().ordinal() == 3) { // Valet
                            foundValetPair = true;
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        } else if (card1.getValue().ordinal() == 2) { // Dame
                            foundDamePair = true;
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        } else if (card1.getValue().ordinal() == 1) { // Roi
                            foundRoiPair = true;
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        } else if (card1.getValue().ordinal() == 0) { // As
                            foundAsPair = true;
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        }
                    }

                    else if (card1.getValue().ordinal() > 4 && cardsToRemove.get(0).getValue().ordinal() > 4) {
                        cardsToRemove.add(card1);
                        cardsToRemove.add(card2);
                    }

                }
            }
        }

        if (cardsToRemove.size() == 0) {
            System.out.println("Le joueur n'a pas de paire.");
            // String colorMessage = hasImposedColor() ? " avec la couleur imposée " + getImposedColor() : "";
            // System.out.println(this.name + " n'a pas de paire" + colorMessage + ".");
        } else {
            System.out.println("Le joueur défausse : " + cardsToRemove);
            hand.removeAll(cardsToRemove);
        }

        if (foundAsPair) {
            return "Paire d'As";
        } else if (foundRoiPair) {
            return "Paire de Roi";
        } else if (foundDamePair) {
            return "Paire de Dame";
        } else if (foundValetPair) {
            return "Paire de Valet";
        } else if (foundTenPair) {
            return "Paire de 10";
        } else {
            return "Paire normale";
        }

    }

    public void pickOneCard(List<Card> handProchainJoueur) {
        if (this.getHand().isEmpty()) {
            System.out.println(this.getName() + " n'a plus de cartes.");
            return;
        }

        if (handProchainJoueur.isEmpty()) {
            System.out.println("Le joueur suivant n'a plus de cartes.");
            return;
        }
        
        int randomIndex = random.nextInt(handProchainJoueur.size());
        Card card = handProchainJoueur.get(randomIndex);
        handProchainJoueur.remove(card);
        this.getHand().add(card);
        System.out.println(this.getName() + " a pris la carte " + card + " au joueur suivant.");
    }

    public void echange2Cartes(Player joueurActuel, List<Player> joueurs) {
        if (joueurActuel.getHand().isEmpty()) {
            System.out.println(joueurActuel.getName() + " n'a plus de cartes à échanger.");
            return;
        }

        Player joueurCible1 = joueurs.get(random.nextInt(joueurs.size()));
        Player joueurCible2 = joueurs.get(random.nextInt(joueurs.size()));

        if (joueurActuel.getHand().size() < 2) {
            System.out.println(joueurActuel.getName() + " n'a pas assez de cartes pour échanger.");
            return; 
        }

        int indexCarteActuelle1 = random.nextInt(joueurActuel.getHand().size());
        int indexCarteActuelle2 = random.nextInt(joueurActuel.getHand().size());
        while (indexCarteActuelle1 == indexCarteActuelle2) {
            indexCarteActuelle2 = random.nextInt(joueurActuel.getHand().size());
        } // Vérifie que les cartes à échanger ne sont pas les mêmes

        int indexCarteCible1 = random.nextInt(joueurCible1.getHand().size());
        int indexCarteCible2 = random.nextInt(joueurCible2.getHand().size());
        while (indexCarteCible1 == indexCarteCible2) {
            indexCarteCible2 = random.nextInt(joueurCible2.getHand().size());
        } // Vérifie que les cartes cibles ne sont pas les mêmes au cas où les joueurs cibles sont le même

        Card carteAEchanger1 = joueurActuel.getHand().get(indexCarteActuelle1);
        Card carteAEchanger2 = joueurActuel.getHand().get(indexCarteActuelle2);
        Card carteCible1 = joueurCible1.getHand().get(indexCarteCible1);
        Card carteCible2 = joueurCible2.getHand().get(indexCarteCible2);

        joueurActuel.getHand().set(indexCarteActuelle1, carteCible1);
        joueurActuel.getHand().set(indexCarteActuelle2, carteCible2);
        joueurCible1.getHand().set(indexCarteCible1, carteAEchanger1);
        joueurCible2.getHand().set(indexCarteCible2, carteAEchanger2);

        System.out.println(joueurActuel.getName() + " échange les cartes " + carteAEchanger1 + " et " + carteAEchanger2
                + "\n avec les cartes " + carteCible1 + " de " + joueurCible1.getName() + " et " + carteCible2 + " de "
                + joueurCible2.getName());
    }

    public void volerCarte(Player joueurActuel, List<Player> joueurs) {
        Player joueurCible = joueurs.get(random.nextInt(joueurs.size()));
        Card carteVolee = joueurCible.getHand().get(random.nextInt(joueurCible.getHand().size()));
        joueurActuel.getHand().add(carteVolee);
        joueurCible.getHand().remove(carteVolee);
        System.out.println(joueurActuel.getName() + " vole la carte " + carteVolee + " à " + joueurCible.getName());
    }
       
}