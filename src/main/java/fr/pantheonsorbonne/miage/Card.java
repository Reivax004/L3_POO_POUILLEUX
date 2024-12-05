package fr.pantheonsorbonne.miage;


public class Card {

    final Value value; // une carte ne change pas de valeur
    private final Color color; // une carte ne change pas de couleur

    // le constructeur
    public Card(final Value value, final Color color) {
        // constructeur qui initialise les attributs finaux
        this.value = value;
        this.color = color;
    }

    // méthode pour accéder à la valeur d'une carte
    public Value getValue() {
        return this.value;
    }

    // méthode pour accéder à la couleur d'une carte
    public Color getColor() {
        return this.color;
    }

    // méthode pour représenter une carte en chaîne de caractères
    public String toString() {
        return this.value.name() + " of " + this.color.name();
    }
}