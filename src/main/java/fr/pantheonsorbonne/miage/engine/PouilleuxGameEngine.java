package fr.pantheonsorbonne.miage.engine;

import java.util.*;
import fr.pantheonsorbonne.miage.enums.PairType;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;

public abstract class PouilleuxGameEngine {

    protected Map<String, Queue<Card>> playerCards = new HashMap<>();
    protected List<Card> discardedPairs = new ArrayList<>();
    public final int nbPlayer;
    protected final Deck deck;

    public PouilleuxGameEngine(Deck deck, int nbPlayer) {
        this.deck = deck;
        this.nbPlayer = nbPlayer;
    }

    public void play() {
        initGame();
        System.out.println("Début de la partie !\n");
        playTurn();
        System.out.println("\nFin de la partie !");
    }

    protected abstract List<String> getInitialPlayers();

    protected abstract void giveCardsToPlayer(String player);

    protected void initGame() {
        for (String player : getInitialPlayers()) {
            giveCardsToPlayer(player);
        }
    }

    protected void playTurn() {

        boolean reverse = false;
        int nbToursCouleur = 0;
        boolean imposeColor = false;
        String colorCurrent = null;

        List<String> players = new ArrayList<>(getInitialPlayers());

        int currentIndex = 0;
        for (;;) {
            String currentPlayer = players.get(currentIndex);
            String nextPlayer = players.get((currentIndex + 1) % players.size());

            System.out.println("--- Au tour de " + currentPlayer + " ---");

            pickOneCard(currentPlayer, nextPlayer);

            if (isWinner(currentPlayer) == true || isWinner(nextPlayer) == true) {
                WhoIsLooser();
                return;
            }

            PairType pairType;
            if (imposeColor && nbToursCouleur % players.size() != 0) {
                pairType = discardPairs(currentPlayer, imposeColor, colorCurrent);
                nbToursCouleur++;
            } else {
                imposeColor = false;
                colorCurrent = null;
                pairType = discardPairs(currentPlayer, imposeColor, colorCurrent);
                nbToursCouleur = 1;
            }

            switch (pairType) {
                case PAIRE_DE_DIX:
                    handlePaireDeDix(currentPlayer, nextPlayer);
                    currentIndex = handleSkipTurn(players, currentIndex, reverse);
                    break;
                case PAIRE_D_AS:
                    imposeColor = true;
                    colorCurrent = getLastDiscardColor(currentPlayer);
                    nbToursCouleur = 1;
                    handlePaireDAs(currentPlayer, colorCurrent);
                    break;
                case PAIRE_DE_DAME:
                    reverse = !reverse;
                    handlePaireDeDame();
                    break;
                case PAIRE_DE_ROI:
                    handlePaireDeRoi(currentPlayer);
                    break;
                case PAIRE_DE_VALET:
                    handlePaireDeValet(currentPlayer);
                    break;
                default:
                    break;
            }
            currentIndex = getNextPlayerIndex(players.size(), currentIndex, reverse);
        }
    }

    private int handleSkipTurn(List<String> players, int currentIndex, boolean reverse) {
        if (reverse) {
            currentIndex--;
        } else {
            currentIndex++;
        }
        return currentIndex;
    }

    private int getNextPlayerIndex(int totalPlayers, int currentIndex, boolean reverse) {
        if (reverse) {
            return (currentIndex - 1 + totalPlayers) % totalPlayers;
        } else {
            return (currentIndex + 1) % totalPlayers;
        }
    }

    protected void turnDirection(boolean reverse, int index) {
        if (reverse) {
            index = (index - 2 + getInitialPlayers().size()) % getInitialPlayers().size();
        }
    }

    protected abstract boolean isWinner(String currentPlayer);

    abstract protected void WhoIsLooser();

    abstract protected void pickOneCard(String currentPlayer, String nextPlayer);

    abstract protected PairType discardPairs(String player, boolean imposeColor, String colorCurrent);

    abstract protected String getLastDiscardColor(String player);

    abstract protected void switchTwoCards(String currentPlayer);

    abstract protected void stealCard(String currentPlayer);

    protected abstract void handlePaireDeDix(String currentPlayer, String nextPlayer);

    protected abstract void handlePaireDAs(String currentPlayer, String color);

    protected abstract void handlePaireDeDame();

    protected abstract void handlePaireDeRoi(String currentPlayer);

    protected abstract void handlePaireDeValet(String currentPlayer);

}