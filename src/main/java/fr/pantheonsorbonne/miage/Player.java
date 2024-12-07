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


    public int discardPairs() {
        List<Card> cardsToRemove = new ArrayList<>();
        boolean foundTenPair = false;
        // Vérifie chaque carte pour voir si c'est une paire avec une autre
        for (int i = 0; i < hand.size(); i++) {
            Card card1 = hand.get(i);
            for (int j = i + 1; j < hand.size(); j++) {
                Card card2 = hand.get(j);
                // Vérifie si les 2 cartes ont la même valeur et la même couleur
                if (card1.getValue().equals(card2.getValue()) &&
                    card1.getSymbol().getColor().equals(card2.getSymbol().getColor())) {
  
                    if(cardsToRemove.size() == 0){

                        if (card1.getValue().ordinal() > 4) {
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        }
                        else if (card1.getValue().ordinal() == 4) {
                            foundTenPair = true;
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        }
                        else if (card1.getValue().ordinal() == 3) {
                            foundTenPair = true;
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        }
                        else if (card1.getValue().ordinal() == 2) {
                            
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        }
                        else if (card1.getValue().ordinal() == 1) {
                            
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        }
                        else if (card1.getValue().ordinal() == 0) {
                            
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        }
                    }
                       
                    else if (card1.getValue().ordinal() > 4 && cardsToRemove.get(0).getValue().ordinal() > 4){
                        cardsToRemove.add(card1);
                        cardsToRemove.add(card2);
                    }

                }
            }
        }
        System.out.println("le joueur défausse : "+cardsToRemove);
        hand.removeAll(cardsToRemove);
        return foundTenPair ? 4 : 0;
    }

    public void pickOneCard(List<Card> cards) {
        int indexRandomCard = random.nextInt(cards.size());
        Card cardPick = cards.get(indexRandomCard);
        cards.remove(indexRandomCard);
        this.hand.add(cardPick); // placement de la carte tiré à la fin du jeu
    }
}