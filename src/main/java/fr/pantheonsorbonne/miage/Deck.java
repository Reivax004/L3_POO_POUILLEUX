package fr.pantheonsorbonne.miage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    // les attributs
    private final static List<Card> cards; // attribut représentant le jeu de cartes (deck)
    private static int remainingCardCount; // nombre de cartes restantes dans le deck
    private static int index = 0;

    // le constructeur
    static {
        // initialisation du jeu de cartes
        cards = new ArrayList<Card>();
        // itère sur chacune des valeurs possibles des cartes
        for (Value value : Value.values()) {
            // itère sur chacune des couleurs possibles des cartes
            for (Symbol symbol : Symbol.ALL) {
                if (value == Value.VALET && symbol == Symbol.TREFLE) {
                    continue; // exclure le valet de Trèfle pour que le Pouilleux soit le Valet de Pique
                }
                cards.add(new Card(value, symbol));
            }
        }
        remainingCardCount = cards.size();

        // Mélange les cartes de manière aléatoire
        Random rand = new Random(); // créer un générateur de nombres aléatoires
        for (int i = 0; i < cards.size(); i++) {
            int randIndex = rand.nextInt(cards.size()); // génère un index aléatoire compris entre 0 et 31
            Card c = cards.get(randIndex); // sauvegarde temporairement la carte située à l'index aléatoire
            cards.set(randIndex, cards.get(i)); // échange la carte à l'index aléatoire avec celle à l'index `i`
            cards.set(i, c); // place la carte précédemment à l'index aléatoire à la position `i`
        }
        //System.out.println(cards);
    }   

    // méthode pour tirer un certain nombre de cartes aléatoirement
    static public List<Card> getRandomCards(int nbPlayer) {
        // Les cartes sont bien distribuées si le nombre de joueurs est un diviseur de 51 = {1,3,17,51}
        // Je ne sais pas comment faire quand ce n'est pas le cas
        int handSize;

        handSize  = remainingCardCount / (nbPlayer - index);
        index ++;
        //System.out.println("taille deck = "+cards.size());

        // nombre de cartes aléatoires que l'on souhaite tirer du jeu
        if (remainingCardCount >= handSize) {
            // vérifie s'il y a assez de cartes dans le jeu avant d'en tirer le nombre demandé
            List<Card> hand = new ArrayList<Card>();
            for( int i = remainingCardCount-1; i >= remainingCardCount - handSize ; i--){
                hand.add(cards.get(i));
            }
            //System.out.println("Main du joueur en cours "+sizeMain);
            remainingCardCount -= handSize;
            //System.out.println("nb carte qu'il reste dans le deck "+remainingCardCount); // met à jour le nombre de cartes restantes
            return hand; // retourne les cartes tirées
        }
        List<Card> vide = new ArrayList<Card>();
        return  vide; // s'il n'y a pas assez de cartes, renvoie un tableau vide
    }
}