package fr.pantheonsorbonne.miage.engine;

import org.junit.jupiter.api.Test;
import fr.pantheonsorbonne.miage.enums.Value;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Symbol;
import fr.pantheonsorbonne.miage.engine.net.PouilleuxHost;
import static org.junit.jupiter.api.Assertions.*;
import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;


public class PouilleuxGuestTest {

    @Test
    void testDiscardPairs() {
        Card neufCarreau = new Card(Value.NEUF, Symbol.CARREAU);
        Card neufCoeur = new Card(Value.NEUF, Symbol.COEUR);
        Card cinqCoeur = new Card(Value.CINQ, Symbol.COEUR);

        HostFacade hostFacade = Facade.getFacade();
        fr.pantheonsorbonne.miage.model.Game pouilleux = hostFacade.createNewGame("pouilleux");
        
        PouilleuxHost engine = new PouilleuxHost(new Deck(), hostFacade, pouilleux.getPlayers(), pouilleux);
        engine.discardedPairs.add(neufCarreau);
        engine.discardedPairs.add(neufCoeur);
        engine.discardedPairs.add(cinqCoeur);
        
        assertEquals(3, engine.discardedPairs.size());
    }
}
