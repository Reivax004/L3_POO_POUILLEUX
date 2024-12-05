package fr.pantheonsorbonne.miage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Player {
    // Attributs du joueur
    final String name; // Le nom du joueur
    private List<Card> cards; // La main du joueur (tableau de cartes)
    private Random random = new Random();
    // Constructeur
    public Player(String name){ 
        this.name = name;
        this.cards = new ArrayList<Card>(); // Par exemple, une main de 5 cartes
    }

    // Le joueur reçoit cette main de cartes
    public void setHand(List<Card> deck) {
        if (deck.size() != 0) {
            this.cards = deck; // Attribuer les cartes passées en paramètre à la main du joueur
        } else {
            System.out.println("La taille de la main ne correspond pas.");
        }
    }

    // Méthode pour récupérer la main du joueur
    public List<Card> getCards() {
        return this.cards;
    }

    // méthode pour afficher la main du joueur en chaîne de caractères
    public String getHandString() {
        StringBuilder builder = new StringBuilder();
        for (Card c : this.cards) {
            if(c!=null){
                builder.append("\t - "+c.toString());
            }
            else{
                builder.append("\t - ");
            }
            builder.append("\n ");
        }
        ;
        return builder.toString().substring(0, builder.length() - 2);
    }


    // méthode pour ajouter des cartes à la main du joueur
    public void addCard(List<Card> cards) {
        System.out.println(this.name + " Adding cards in hand: hand before: \n" + this.getHandString());
        int myHandIndex = 0;
        for (int i = 0; i < cards.size(); i++) {
            for (; myHandIndex < this.cards.size(); myHandIndex++) {
                if (this.cards.get(myHandIndex) == null) {
                    this.cards.set(myHandIndex, cards.get(i));
                    break;
                }
            }

        }
         System.out.println(this.name + " Adding cards in hand: hand after: \n" + this.getHandString());
    }

    public void pickOneCard(List<Card> cards){
        
        int indexRandomCard = random.nextInt(cards.size());
    
        Card cardPick = cards.get(indexRandomCard);

        cards.remove(indexRandomCard);

        this.cards.add(cardPick); // placement de la carte tiré à la fin du jeu
    
    }
}