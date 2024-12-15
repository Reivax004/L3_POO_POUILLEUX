/*package fr.pantheonsorbonne.miage.engine.net;

import java.util.*;
import java.util.stream.Collectors;


import fr.pantheonsorbonne.miage.enums.PairType;
import fr.pantheonsorbonne.miage.enums.Value;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Symbol;
import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;


public class PouilleuxGuest {

    static final String playerId = "Player-" + new Random().nextInt();
    static final Deque<Card> hand = new LinkedList<>();
    static final PlayerFacade playerFacade = Facade.getFacade();
    static Game pouilleux;


    public static void main(String... args) {

        playerFacade.waitReady();
        playerFacade.createNewPlayer(playerId);
        pouilleux = playerFacade.autoJoinGame("POUILLEUX");
        /*
        while (true) {
            GameCommand command = playerFacade.receiveGameCommand(pouilleux);
        
            if ("cardsForYou".equals(command.name())) {
                handleCardsForYou(command);
            } else if ("playACard".equals(command.name())) {
                System.out.println("I have " + hand.stream().map(Card::toFancyString).collect(Collectors.joining(" ")));
                handlePlayACard(command);
            } else if ("gameOver".equals(command.name())) {
                handleGameOverCommand(command);
            } else {
                System.out.println("Unknown command: " + command.name());
            }
        }
    }

    
}*/