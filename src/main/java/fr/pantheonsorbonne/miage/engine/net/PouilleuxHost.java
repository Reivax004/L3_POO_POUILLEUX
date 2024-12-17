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
        List<Card> hand = new ArrayList<>(Deck.getRandomCards(nbPlayer));
        System.out.println(Card.cardsToString(hand));
        hostFacade.sendGameCommandToPlayer(pouilleux, playerName, new GameCommand("cardsForYou", Card.cardsToString(hand)));
    }
    @Override
    protected PairType discardPairs(String player, boolean imposeColor, String colorCurrent) {
        hostFacade.sendGameCommandToPlayer(pouilleux, player, new GameCommand("EvaluateAPair"));
        GameCommand discPair = hostFacade.receiveGameCommand(pouilleux);
        PairType pairType = PairType.AUCUNE_PAIRE;
        List<Card> listEvaluate = new ArrayList(Card.stringToCards(discPair.body()));
    
            if(imposeColor){

                if(listEvaluate.get(0).getSymbol().getColor().equals(colorCurrent)){
                    
                    if (listEvaluate.get(0).getValue().ordinal() > 4) {
                        pairType = PairType.PAIRE_NORMALE;
                    } else if (listEvaluate.get(0).getValue() == Value.DIX) {
                        pairType = PairType.PAIRE_DE_DIX;
                    } else if (listEvaluate.get(0).getValue() == Value.VALET) {
                        pairType = PairType.PAIRE_DE_VALET;
                    } else if (listEvaluate.get(0).getValue() == Value.DAME) {
                        pairType = PairType.PAIRE_DE_DAME;
                    } else if (listEvaluate.get(0).getValue() == Value.ROI) {
                        pairType = PairType.PAIRE_DE_ROI;
                    } else if (listEvaluate.get(0).getValue() == Value.AS) {
                        pairType = PairType.PAIRE_D_AS;
                        colorCurrent = listEvaluate.get(0).getSymbol().getColor();
                    }
                }
            }
            else{
                if (listEvaluate.get(0).getValue().ordinal() > 4) {
                    pairType = PairType.PAIRE_NORMALE;
                } else if (listEvaluate.get(0).getValue() == Value.DIX) {
                    pairType = PairType.PAIRE_DE_DIX;
                } else if (listEvaluate.get(0).getValue() == Value.VALET) {
                    pairType = PairType.PAIRE_DE_VALET; 
                } else if (listEvaluate.get(0).getValue() == Value.DAME) {
                    pairType = PairType.PAIRE_DE_DAME;
                } else if (listEvaluate.get(0).getValue() == Value.ROI) {
                    pairType = PairType.PAIRE_DE_ROI;
                } else if (listEvaluate.get(0).getValue() == Value.AS) {
                    pairType = PairType.PAIRE_D_AS;
                    colorCurrent = listEvaluate.get(0).getSymbol().getColor();
                }
            } 
        return pairType;
    }

    @Override
    protected void pickOneCard(String currentPlayer, String nextPlayer) {
        hostFacade.sendGameCommandToPlayer(pouilleux, nextPlayer, new GameCommand("GiveACard"));
        System.out.println(currentPlayer);
        GameCommand expectedCard = hostFacade.receiveGameCommand(pouilleux);
        System.out.println("Le joueur a pioché "+expectedCard);
        hostFacade.sendGameCommandToPlayer(pouilleux, currentPlayer, new GameCommand("cardsForYou", expectedCard.body().toString()));
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
    protected String getLastDiscardColor(String player) {
        if (discardedPairs.size() >= 2) {
            Card lastDiscardedCard = discardedPairs.get(discardedPairs.size() - 1);
            return lastDiscardedCard.getSymbol().getColor();
        }
        return null;
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