package fr.pantheonsorbonne.miage.engine;

import org.junit.jupiter.api.Test;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.engine.net.PouilleuxHost;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import fr.pantheonsorbonne.miage.Facade;

public class PouilleuxGameEngineTest {

    PouilleuxGameEngine game = new PouilleuxHost(new Deck(), Facade.getFacade(), null, null);

    @Test
    void testGetNextPlayerIndex() throws Exception {
        Method method = PouilleuxGameEngine.class.getDeclaredMethod("getNextPlayerIndex", int.class, int.class,
                boolean.class);
        method.setAccessible(true);

        int nextIndex = (int) method.invoke(game, 4, 0, false);
        assertEquals(1, nextIndex);

        nextIndex = (int) method.invoke(game, 4, 0, true);
        assertEquals(3, nextIndex);
    }

    @Test
    void testHandleSkipTurn() throws Exception {
        Method method = PouilleuxGameEngine.class.getDeclaredMethod("handleSkipTurn", List.class, int.class,
                boolean.class);
        method.setAccessible(true);

        List<String> players = Arrays.asList("Nicolas", "Elio", "Arthur", "Eva");

        int nextIndex = (int) method.invoke(game, players, 0, false);
        assertEquals(1, nextIndex);

        nextIndex = (int) method.invoke(game, players, 0, true);
        assertEquals(-1, nextIndex);
    }
}
