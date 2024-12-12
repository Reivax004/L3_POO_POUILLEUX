package fr.pantheonsorbonne.miage;

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

}
