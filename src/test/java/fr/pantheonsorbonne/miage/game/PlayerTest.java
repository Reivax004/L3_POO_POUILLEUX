package fr.pantheonsorbonne.miage.game;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.enums.PairType;
import fr.pantheonsorbonne.miage.enums.Value;

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
            boolean imposeColor = false;
            String colorCurrent = null;
            main.add(AsCarreau);
            main.add(NeufCarreau);
            main.add(AsCoeur);
            //Player p1 = new Player("Nicolas");
            //p1.setHand(main);
            //assertEquals(p1.discardPairs(imposeColor, colorCurrent), PairType.PAIRE_D_AS);
        }
    }
}
