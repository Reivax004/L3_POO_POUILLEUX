package fr.pantheonsorbonne.miage.engine.net;

import java.util.*;
import fr.pantheonsorbonne.miage.engine.PouilleuxGameEngine;
import fr.pantheonsorbonne.miage.enums.PairType;
import fr.pantheonsorbonne.miage.enums.Value;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class PouilleuxHost extends PouilleuxGameEngine {

    private static final int PLAYER_COUNT = 3;

    private final HostFacade hostFacade;
    private final Set<String> players;
    private final Game pouilleux;

    public PouilleuxHost(Deck deck, HostFacade hostFacade, Set<String> players, Game pouilleux) {
        super(deck, 3);
        this.hostFacade = hostFacade;
        this.players = players;
        this.pouilleux = pouilleux;
    }

    public static void main(String[] args) {
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        hostFacade.createNewPlayer("Host");

        Game pouilleux = hostFacade.createNewGame("POUILLEUX");

        hostFacade.waitForExtraPlayerCount(PLAYER_COUNT);

        PouilleuxGameEngine host = new PouilleuxHost(new Deck(), hostFacade, pouilleux.getPlayers(), pouilleux);
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
        hostFacade.sendGameCommandToPlayer(pouilleux, playerName,
                new GameCommand("cardsForYou", Card.cardsToString(hand)));
    }

    @Override
    protected PairType discardPairs(String player, boolean imposeColor, String colorCurrent) {
        hostFacade.sendGameCommandToPlayer(pouilleux, player, new GameCommand("DiscardAPair", colorCurrent));
        GameCommand discPair = hostFacade.receiveGameCommand(pouilleux);
        PairType pairType = PairType.AUCUNE_PAIRE;
        List<Card> listPair = new ArrayList<Card>(Card.stringToCards(discPair.body()));

        if (imposeColor) {

            if (listPair.size() != 0) {

                if (listPair.get(0).getSymbol().getColor().equals(colorCurrent)) {

                    if (listPair.get(0).getValue().ordinal() > 4) {
                        pairType = PairType.PAIRE_NORMALE;
                    } else if (listPair.get(0).getValue() == Value.DIX) {
                        pairType = PairType.PAIRE_DE_DIX;
                    } else if (listPair.get(0).getValue() == Value.VALET) {
                        pairType = PairType.PAIRE_DE_VALET;
                    } else if (listPair.get(0).getValue() == Value.DAME) {
                        pairType = PairType.PAIRE_DE_DAME;
                    } else if (listPair.get(0).getValue() == Value.ROI) {
                        pairType = PairType.PAIRE_DE_ROI;
                    } else if (listPair.get(0).getValue() == Value.AS) {
                        pairType = PairType.PAIRE_D_AS;
                        colorCurrent = listPair.get(0).getSymbol().getColor();
                    }
                }
            }
        } else {
            if (listPair.size() != 0) {
                if (listPair.get(0).getValue().ordinal() > 4) {
                    pairType = PairType.PAIRE_NORMALE;
                } else if (listPair.get(0).getValue() == Value.DIX) {
                    pairType = PairType.PAIRE_DE_DIX;
                } else if (listPair.get(0).getValue() == Value.VALET) {
                    pairType = PairType.PAIRE_DE_VALET;
                } else if (listPair.get(0).getValue() == Value.DAME) {
                    pairType = PairType.PAIRE_DE_DAME;
                } else if (listPair.get(0).getValue() == Value.ROI) {
                    pairType = PairType.PAIRE_DE_ROI;
                } else if (listPair.get(0).getValue() == Value.AS) {
                    pairType = PairType.PAIRE_D_AS;
                    colorCurrent = listPair.get(0).getSymbol().getColor();
                }
            }
        }
        if (pairType == PairType.PAIRE_D_AS) {
            discardedPairs.addAll(listPair);
        }
        return pairType;
    }

    @Override
    protected void pickOneCard(String currentPlayer, String nextPlayer) {
        hostFacade.sendGameCommandToPlayer(pouilleux, nextPlayer, new GameCommand("GiveACard"));
        GameCommand expectedCard = hostFacade.receiveGameCommand(pouilleux);
        hostFacade.sendGameCommandToPlayer(pouilleux, currentPlayer,
                new GameCommand("cardForYou", expectedCard.name()));
    }

    @Override
    protected boolean isWinner(String currentPlayer) {
        hostFacade.sendGameCommandToPlayer(pouilleux, currentPlayer, new GameCommand("isWinner"));
        GameCommand winner = hostFacade.receiveGameCommand(pouilleux);
        if (Boolean.parseBoolean(winner.body()) == true) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void WhoIsLooser() {
        hostFacade.sendGameCommandToAll(pouilleux, new GameCommand("WhoIsLooser"));
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
        List<String> playerList = new ArrayList<>(players);
        Random rand = new Random();
        int indexPlayer1 = rand.nextInt(playerList.size());
        int indexPlayer2 = rand.nextInt(playerList.size() - 1);
        if (indexPlayer1 == indexPlayer2) {
            indexPlayer2++;
        }
        hostFacade.sendGameCommandToPlayer(pouilleux, playerList.get(indexPlayer1), new GameCommand("GiveACard"));
        GameCommand expectedCard1 = hostFacade.receiveGameCommand(pouilleux);

        hostFacade.sendGameCommandToPlayer(pouilleux, playerList.get(indexPlayer2), new GameCommand("GiveACard"));
        GameCommand expectedCard2 = hostFacade.receiveGameCommand(pouilleux);

        hostFacade.sendGameCommandToPlayer(pouilleux, playerList.get(indexPlayer2),
                new GameCommand("cardForYou", expectedCard1.name()));
        hostFacade.sendGameCommandToPlayer(pouilleux, playerList.get(indexPlayer1),
                new GameCommand("cardForYou", expectedCard2.name()));
    }

    @Override
    protected void stealCard(String currentPlayer) {
        List<String> playerList = new ArrayList<>(players);
        playerList.remove(currentPlayer);
        Random rand = new Random();
        int randomIndex = rand.nextInt(playerList.size());
        hostFacade.sendGameCommandToPlayer(pouilleux, playerList.get(randomIndex), new GameCommand("GiveACard"));
        GameCommand expectedCard = hostFacade.receiveGameCommand(pouilleux);
        hostFacade.sendGameCommandToPlayer(pouilleux, currentPlayer,
                new GameCommand("cardForYou", expectedCard.name()));
    }

    @Override
    protected void handlePaireDeDix(String currentPlayer, String nextPlayer) {
        System.out.println("Paire de 10! Le joueur suivant, " + nextPlayer + ", saute son tour.");
    }

    @Override
    protected void handlePaireDeDame() {
        System.out.println("Paire de dame! Sens de jeu inversé.");
    }

    @Override
    protected void handlePaireDAs(String currentPlayer, String color) {
        System.out.println(currentPlayer + " impose une couleur: " + color + ".");
    }

    @Override
    protected void handlePaireDeRoi(String currentPlayer) {
        System.out.println(currentPlayer + " vole une carte à un autre joueur.");
        stealCard(currentPlayer);
    }

    @Override
    protected void handlePaireDeValet(String currentPlayer) {
        System.out.println(currentPlayer + " a fait échanger deux cartes entre deux joueurs.");
        switchTwoCards(currentPlayer);
    }

}