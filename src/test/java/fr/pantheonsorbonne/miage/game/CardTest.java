package fr.pantheonsorbonne.miage.game;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {

    @Test
    void cardsToString() {
        {
            Card card = new Card(Value.AS, Symbol.CARREAU);
            assertEquals("AS de Carreau (Rouge)", card.toString());
        }
        {
            Card card = new Card(Value.HUIT, Symbol.PIQUE);
            assertEquals("HUIT de Pique (Noir)", card.toString());
        }
    }
    
    @Test
    void getColor() {
        assertEquals(Symbol.PIQUE.getColor(), "Noir");
        assertEquals(Symbol.COEUR.getColor(), "Rouge");
    }
}