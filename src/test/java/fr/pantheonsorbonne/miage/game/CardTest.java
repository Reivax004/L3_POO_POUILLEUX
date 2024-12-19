package fr.pantheonsorbonne.miage.game;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;

class CardTest {

    @Test
    void getColor() {
        assertEquals(Symbol.PIQUE.getColor(), "Noir");
        assertEquals(Symbol.COEUR.getColor(), "Rouge");
    }

    @Test
    void getSymbol() {
        Card card = new Card(Value.AS, Symbol.CARREAU);
        assertEquals(Symbol.CARREAU, card.getSymbol());
    }

    @Test
    void testCardEquality() {
        Card card1 = new Card(Value.AS, Symbol.COEUR);
        Card card2 = new Card(Value.AS, Symbol.COEUR);
        Card card3 = new Card(Value.ROI, Symbol.PIQUE);

        assertEquals(card1, card2);
        assertNotEquals(card1, card3);
    }

    @Test
    void testToString() {
        Card card = new Card(Value.DAME, Symbol.PIQUE);
        String expected = "DAME de Pique (Noir)";
        assertEquals(expected, card.toString());
    }

    @Test
    void testCardsToString() {
        List<Card> cards = Arrays.asList(new Card(Value.AS, Symbol.CARREAU), new Card(Value.ROI, Symbol.PIQUE));
        String expected = "\nAS de Carreau (Rouge)\nROI de Pique (Noir)\n";
        assertEquals(expected, Card.cardsToString(cards));
    }

    @Test
    void testValueOfCard() {
        Card card = new Card(Value.AS, Symbol.COEUR);
        String expected = "AS de Coeur (Rouge)";
        assertEquals(expected, Card.valueOfCard(card));
    }

    @Test
    void testStringToCards() {
        String input = "\nAS de Carreau (Rouge)\nROI de Pique (Noir)\n";
        List<Card> expectedCards = Arrays.asList(new Card(Value.AS, Symbol.CARREAU), new Card(Value.ROI, Symbol.PIQUE));

        List<Card> result = Card.stringToCards(input);
        assertEquals(expectedCards.toString().trim(), result.toString().trim());
    }

    @Test
    void testValueOf() {
        String input = "AS de Carreau (Rouge)";
        Card expectedCard = new Card(Value.AS, Symbol.CARREAU);

        Card result = Card.valueOf(input);
        assertEquals(expectedCard.toString().trim(), result.toString().trim());
    }

    @Test
    void testCommandToStringCard() {
        String command = "name=AS de Carreau (Rouge), body=Some other info";
        String expected = "AS de Carreau (Rouge)";
        assertEquals(expected, Card.commandToStringCard(command));
    }
}