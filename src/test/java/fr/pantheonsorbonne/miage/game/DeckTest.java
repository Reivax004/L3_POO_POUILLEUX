package fr.pantheonsorbonne.miage.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {

    @Test
    public void testNbCardsInit() {
        assertEquals(51, Deck.getCards().size());
    }

    @Test
    public void testGetRandomCards() {
        List<Card> j1 = Deck.getRandomCards(4);
        assertEquals(12, j1.size());

        List<Card> j2 = Deck.getRandomCards(4);
        assertEquals(13, j2.size());

        List<Card> j3 = Deck.getRandomCards(4);
        assertEquals(13, j3.size());

        List<Card> j4 = Deck.getRandomCards(4);
        assertEquals(13, j4.size());

        assertEquals(0, Deck.getRemainingCardCount());
    }


}