package fr.pantheonsorbonne.miage.engine.net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
import fr.pantheonsorbonne.miage.PlayerFacade;

public class PouilleuxHost extends PouilleuxGameEngine{

    private static final int PLAYER_COUNT = 2;

    private final HostFacade hostFacade;
    private final Set<String> players;
    private final Game pouilleux;
    
    
    public PouilleuxHost(Deck deck,HostFacade hostFacade,
    Set<String> players, fr.pantheonsorbonne.miage.model.Game pouilleux) {
        
        super(deck, 2);
        this.hostFacade = hostFacade;
        this.players = players;
        this.pouilleux = pouilleux;
    }

    public static void main(String[] args) {
        System.out.println("aaa");
        //create the host facade
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        //set the name of the player
        hostFacade.createNewPlayer("Host");

        //create a new game of war
        fr.pantheonsorbonne.miage.model.Game pouilleux = hostFacade.createNewGame("POUILLEUX");

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
    
    @Override
    protected void giveCardsToPlayer(String playerName) {
        List<Card> hand = Deck.getRandomCards(nbPlayer);
        hostFacade.sendGameCommandToPlayer(pouilleux, playerName, new GameCommand("cardsForYou", Card.cardsToString(hand)));
    }
    protected void getHandString(String playerName, String hand) {
        hostFacade.sendGameCommandToPlayer(pouilleux, playerName, new GameCommand("ShowHand", hand));
    }

    @Override
    protected boolean isWinner(String currentPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isWinner'");
    }

    @Override
    protected void WhoIsLooser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'WhoIsLooser'");
    }

    @Override
    protected String getHandString(String player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHandString'");
    }

    @Override
    protected void pickOneCard(String currentPlayer, String nextPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pickOneCard'");
    }

    @Override
    protected PairType discardPairs(String player, boolean imposeColor, String colorCurrent) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'discardPairs'");
    }

    @Override
    protected String getLastDiscardColor(String player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLastDiscardColor'");
    }

    @Override
    protected void switchTwoCards(String currentPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'switchTwoCards'");
    }

    @Override
    protected void volerCarte(String currentPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'volerCarte'");
    }

    @Override
    protected void handlePaireDeDix(String currentPlayer, String nextPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePaireDeDix'");
    }

    @Override
    protected void handlePaireDAs(String currentPlayer, String color) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePaireDAs'");
    }

    @Override
    protected void handlePaireDeDame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePaireDeDame'");
    }

    @Override
    protected void handlePaireDeRoi(String currentPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePaireDeRoi'");
    }

    @Override
    protected void handlePaireDeValet(String currentPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePaireDeValet'");
    }


}