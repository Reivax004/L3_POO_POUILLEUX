/*package fr.pantheonsorbonne.miage.engine.net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


import fr.pantheonsorbonne.miage.engine.PouilleuxGameEngine;
import fr.pantheonsorbonne.miage.enums.PairType;
import fr.pantheonsorbonne.miage.enums.Value;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Symbol;
import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class PouilleuxHost extends PouilleuxGameEngine{

    private static final int PLAYER_COUNT = 4;

    private final HostFacade hostFacade;
    private final Set<String> players;
    private final Game pouilleux;
    

    public PouilleuxHost(Deck deck,HostFacade hostFacade,
    Set<String> players, fr.pantheonsorbonne.miage.model.Game pouilleux) {
        
        super(deck, 4);
        this.hostFacade = hostFacade;
        this.players = players;
        this.pouilleux = pouilleux;
    }

    public static void main(String[] args) {
        //create the host facade
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        //set the name of the player
        hostFacade.createNewPlayer("Host");

        //create a new game of war
        fr.pantheonsorbonne.miage.model.Game pouilleux = hostFacade.createNewGame("Pouilleux");

        //wait for enough players to join
        hostFacade.waitForExtraPlayerCount(PLAYER_COUNT);

        Deck deck = new Deck();

        PouilleuxGameEngine host = new PouilleuxHost(deck, hostFacade,pouilleux.getPlayers(), pouilleux);
        host.play();
        System.exit(0);
    }
    @Override
    protected List<String> getInitialPlayers() {
        return new ArrayList<>(this.pouilleux.getPlayers());
    }
    /*
    @Override
    protected void giveCardsToPlayer(String playerName, String hand) {
        hostFacade.sendGameCommandToPlayer(war, playerName, new GameCommand("cardsForYou", hand));
    }
    protected void getHandString(String playerName, String hand) {
        hostFacade.sendGameCommandToPlayer(pouilleux, playerName, new GameCommand("ShowHand", hand));
    }


}*/