package fr.pantheonsorbonne.miage.game;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.Symbol;
import fr.pantheonsorbonne.miage.Value;
import fr.pantheonsorbonne.miage.Card;
import fr.pantheonsorbonne.miage.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlayerTest {
    @Test
    void Paire() {
        {
            Card AsCarreau  = new Card(Value.AS, Symbol.CARREAU);
            Card NeufCarreau = new Card(Value.NEUF, Symbol.CARREAU);
            Card AsCoeur  = new Card(Value.AS, Symbol.COEUR);
            List<Card> main = new ArrayList<>();
            main.add(AsCarreau);
            main.add(NeufCarreau);
            main.add(AsCoeur);
            Player p1 = new Player("Nicolas");
            p1.setHand(main);
            assertEquals(p1.discardPairs(),"Paire d'As");
        }
    }
}
