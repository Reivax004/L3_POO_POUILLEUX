package fr.pantheonsorbonne.miage;

public class Card {
    private final Value value; // Valeur de la carte (2, 3, As, Roi, etc...)
    private final Symbol symbol; // Symbole (Coeur, Carreau, etc...)

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
    public String toString() {
        return value.name() + " de " + symbol.getName() + " (" + symbol.getColor() + ")";
    }
    
}
