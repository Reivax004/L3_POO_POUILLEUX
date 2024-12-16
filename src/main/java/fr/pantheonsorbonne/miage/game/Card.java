package fr.pantheonsorbonne.miage.game;

import java.util.List;

import fr.pantheonsorbonne.miage.enums.Value;

public class Card {
    private final Value value;
    private final Symbol symbol;

    public Card(Value value, Symbol symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public Value getValue() {
        return value;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object object) {
        Card card = (Card) object;
        return value == card.value && symbol == card.symbol;
    }

    @Override
    public String toString() {
        return value.name() + " de " + symbol.getName() + " (" + symbol.getColor() + ")";
    }
    public static String cardsToString(List<Card> cards) {
    String carte = "\n";
    for (Card card : cards) {
        carte += Card.valueOfCard(card) + "\n";
    }
    return carte;
    }
    public static String valueOfCard(Card card) {
        return card.getValue()+ " " +card.getSymbol().getColor();
    }

}
