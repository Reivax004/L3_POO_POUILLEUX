package fr.pantheonsorbonne.miage.game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        return card.getValue() + " de " + card.getSymbol().getName() + " (" + card.getSymbol().getColor() + ")";
    }

    public static List<Card> stringToCards(String str) {
        return Arrays.stream(str.split("\n"))
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .map(Card::valueOf)
                .collect(Collectors.toList());
    }

    public static Card valueOf(String str) {
        str = str.trim();

        int spaceIndex = str.indexOf(' ');
        int deIndex = str.indexOf(" de ");
        int parenIndex = str.indexOf('(');

        String valuePart = str.substring(0, spaceIndex);
        String symbolNamePart = str.substring(deIndex + 4, parenIndex - 1);
        String colorPart = str.substring(parenIndex + 1, str.length() - 1);

        Symbol symbol = stringToSymbol(symbolNamePart, colorPart);
        Value value = Value.valueOf(valuePart);

        return new Card(value, symbol);
    }

    private static Symbol stringToSymbol(String name, String color) {
        return new Symbol(name, color);
    }

    public static String commandToStringCard(String command) {

        int startIndex = command.indexOf("name=") + 5;
        int endIndex = command.indexOf(", body=");

        if (startIndex != -1 && endIndex != -1) {
            return command.substring(startIndex, endIndex).trim();
        }

        return "";
    }

}
