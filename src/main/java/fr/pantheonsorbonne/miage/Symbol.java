package fr.pantheonsorbonne.miage;

import java.util.List;

public class Symbol {
    private final String name; // Nom du symbole (Coeur, Carreau, etc.)
    private final String color; // Couleur associée (Rouge ou Noir)

    public Symbol(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public static final Symbol COEUR = new Symbol("Coeur", "Rouge");
    public static final Symbol CARREAU = new Symbol("Carreau", "Rouge");
    public static final Symbol PIQUE = new Symbol("Pique", "Noir");
    public static final Symbol TREFLE = new Symbol("Trèfle", "Noir");

    public static final List<Symbol> ALL = List.of(COEUR, CARREAU, PIQUE, TREFLE);

}
