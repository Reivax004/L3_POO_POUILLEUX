package fr.pantheonsorbonne.miage.game;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    public void testNbCardsInit() {
        assertEquals(51, Deck.getCards().size());
    }

    @Test
    public void testGetRandomCards() {
        List<Card> j1 = Deck.getRandomCards(4);
        List<Card> j2 = Deck.getRandomCards(4);
        List<Card> j3 = Deck.getRandomCards(4);
        List<Card> j4 = Deck.getRandomCards(4);

        assertEquals(12, j1.size());
        assertEquals(13, j2.size());
        assertEquals(13, j3.size());
        assertEquals(13, j4.size());

        assertEquals(0, Deck.getRemainingCardCount());
    }
}