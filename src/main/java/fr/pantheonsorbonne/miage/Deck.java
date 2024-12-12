package fr.pantheonsorbonne.miage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.pantheonsorbonne.miage.enums.Value;

public class Deck {

    private final static List<Card> cards;
    private static int remainingCardCount;
    private static int index = 0;

    public static List<Card> getCards() {
        return cards;
    }

    public static int getRemainingCardCount() {
        return remainingCardCount;
    }

    static {
        cards = new ArrayList<Card>();
        for (Value value : Value.values()) {
            for (Symbol symbol : Symbol.ALL) {
                if (value == Value.VALET && symbol == Symbol.TREFLE) {
                    continue;
                }
                cards.add(new Card(value, symbol));
            }
        }
        remainingCardCount = cards.size();

        Random rand = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int randIndex = rand.nextInt(cards.size());
            Card c = cards.get(randIndex);
            cards.set(randIndex, cards.get(i));
            cards.set(i, c);
        }
    }   

    static public List<Card> getRandomCards(int nbPlayer) {
        int handSize;

        handSize  = remainingCardCount / (nbPlayer - index);
        index ++;

        if (remainingCardCount >= handSize) {
            List<Card> hand = new ArrayList<Card>();
            for( int i = remainingCardCount-1; i >= remainingCardCount - handSize ; i--){
                hand.add(cards.get(i));
            }
            remainingCardCount -= handSize;
            return hand;
        }
        List<Card> vide = new ArrayList<Card>();
        return  vide;
    }
}