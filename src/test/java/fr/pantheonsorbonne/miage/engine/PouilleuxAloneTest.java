package fr.pantheonsorbonne.miage.engine;

import org.junit.jupiter.api.Test;
import fr.pantheonsorbonne.miage.engine.local.PouilleuxAlone;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.enums.PairType;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class PouilleuxAloneTest {

    Deck deck = new Deck();
    PouilleuxAlone game = new PouilleuxAlone(deck, 4, Arrays.asList("Nicolas", "Elio", "Arthur", "Eva"));

    @Test
    void testGetInitialPlayers() throws Exception {
        Method method = PouilleuxAlone.class.getDeclaredMethod("getInitialPlayers");
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<String> players = (List<String>) method.invoke(game);
        assertEquals(4, players.size());
        assertTrue(players.contains("Nicolas"));
        assertTrue(players.contains("Elio"));
        assertTrue(players.contains("Arthur"));
        assertTrue(players.contains("Eva"));
    }

    @Test
    void testGiveCardsToPlayer() throws Exception {
        Method method = PouilleuxAlone.class.getDeclaredMethod("giveCardsToPlayer", String.class);
        method.setAccessible(true);
        method.invoke(game, "Nicolas");
        Queue<Card> hand = game.playerCards.get("Nicolas");
        assertNotNull(hand);
        assertFalse(hand.isEmpty());
    }

    @Test
    void testIsWinner() throws Exception {
        Method giveCardsMethod = PouilleuxAlone.class.getDeclaredMethod("giveCardsToPlayer", String.class);
        giveCardsMethod.setAccessible(true);
        giveCardsMethod.invoke(game, "Nicolas");

        Method isWinnerMethod = PouilleuxAlone.class.getDeclaredMethod("isWinner", String.class);
        isWinnerMethod.setAccessible(true);
        assertFalse((boolean) isWinnerMethod.invoke(game, "Nicolas"));

        game.playerCards.get("Nicolas").clear();
        assertTrue((boolean) isWinnerMethod.invoke(game, "Nicolas"));
    }

    /* 
    @Test
    void testWhoIsLooser() throws Exception {
        Method giveCardsMethod = PouilleuxAlone.class.getDeclaredMethod("giveCardsToPlayer", String.class);
        giveCardsMethod.setAccessible(true);
        giveCardsMethod.invoke(game, "Nicolas");
    
        game.playerCards.get("Nicolas").add(new Card(Value.VALET, Symbol.PIQUE));
    
        Method whoIsLooserMethod = PouilleuxAlone.class.getDeclaredMethod("WhoIsLooser");
        whoIsLooserMethod.setAccessible(true);
        whoIsLooserMethod.invoke(game);
    }
     */

    @Test
    void testPickOneCard() throws Exception {
        Method giveCardsMethod = PouilleuxAlone.class.getDeclaredMethod("giveCardsToPlayer", String.class);
        giveCardsMethod.setAccessible(true);
        giveCardsMethod.invoke(game, "Nicolas");
        giveCardsMethod.invoke(game, "Elio");

        Method pickOneCardMethod = PouilleuxAlone.class.getDeclaredMethod("pickOneCard", String.class, String.class);
        pickOneCardMethod.setAccessible(true);
        pickOneCardMethod.invoke(game, "Nicolas", "Elio");
    }

    @Test
    void testDiscardPairs() throws Exception {
        Method giveCardsMethod = PouilleuxAlone.class.getDeclaredMethod("giveCardsToPlayer", String.class);
        giveCardsMethod.setAccessible(true);
        giveCardsMethod.invoke(game, "Nicolas");

        Method discardPairsMethod = PouilleuxAlone.class.getDeclaredMethod("discardPairs", String.class, boolean.class,
                String.class);
        discardPairsMethod.setAccessible(true);
        PairType pairType = (PairType) discardPairsMethod.invoke(game, "Nicolas", false, null);
        assertNotNull(pairType);
    }

    @Test
    void testSwitchTwoCards() throws Exception {
        Method giveCardsMethod = PouilleuxAlone.class.getDeclaredMethod("giveCardsToPlayer", String.class);
        giveCardsMethod.setAccessible(true);
        giveCardsMethod.invoke(game, "Nicolas");
        giveCardsMethod.invoke(game, "Elio");

        Method switchTwoCardsMethod = PouilleuxAlone.class.getDeclaredMethod("switchTwoCards", String.class);
        switchTwoCardsMethod.setAccessible(true);
        switchTwoCardsMethod.invoke(game, "Nicolas");
    }

    @Test
    void testStealCard() throws Exception {
        Method giveCardsMethod = PouilleuxAlone.class.getDeclaredMethod("giveCardsToPlayer", String.class);
        giveCardsMethod.setAccessible(true);
        giveCardsMethod.invoke(game, "Nicolas");
        giveCardsMethod.invoke(game, "Elio");

        Method stealCardMethod = PouilleuxAlone.class.getDeclaredMethod("stealCard", String.class);
        stealCardMethod.setAccessible(true);
        stealCardMethod.invoke(game, "Nicolas");
    }

    @Test
    void testHandlePaireDeDix() throws Exception {
        Method handlePaireDeDixMethod = PouilleuxAlone.class.getDeclaredMethod("handlePaireDeDix", String.class,
                String.class);
        handlePaireDeDixMethod.setAccessible(true);
        handlePaireDeDixMethod.invoke(game, "Nicolas", "Elio");
    }

    @Test
    void testHandlePaireDeDame() throws Exception {
        Method handlePaireDeDameMethod = PouilleuxAlone.class.getDeclaredMethod("handlePaireDeDame");
        handlePaireDeDameMethod.setAccessible(true);
        handlePaireDeDameMethod.invoke(game);
    }

    @Test
    void testHandlePaireDAs() throws Exception {
        Method handlePaireDAsMethod = PouilleuxAlone.class.getDeclaredMethod("handlePaireDAs", String.class,
                String.class);
        handlePaireDAsMethod.setAccessible(true);
        handlePaireDAsMethod.invoke(game, "Nicolas", "PIQUE");
    }

    @Test
    void testHandlePaireDeRoi() throws Exception {
        Method giveCardsMethod = PouilleuxAlone.class.getDeclaredMethod("giveCardsToPlayer", String.class);
        giveCardsMethod.setAccessible(true);
        giveCardsMethod.invoke(game, "Nicolas");
        giveCardsMethod.invoke(game, "Elio");

        Method handlePaireDeRoiMethod = PouilleuxAlone.class.getDeclaredMethod("handlePaireDeRoi", String.class);
        handlePaireDeRoiMethod.setAccessible(true);
        handlePaireDeRoiMethod.invoke(game, "Nicolas");
    }

    @Test
    void testHandlePaireDeValet() throws Exception {
        Method giveCardsMethod = PouilleuxAlone.class.getDeclaredMethod("giveCardsToPlayer", String.class);
        giveCardsMethod.setAccessible(true);
        giveCardsMethod.invoke(game, "Nicolas");
        giveCardsMethod.invoke(game, "Elio");

        Method handlePaireDeValetMethod = PouilleuxAlone.class.getDeclaredMethod("handlePaireDeValet", String.class);
        handlePaireDeValetMethod.setAccessible(true);
        handlePaireDeValetMethod.invoke(game, "Nicolas");
    }
}