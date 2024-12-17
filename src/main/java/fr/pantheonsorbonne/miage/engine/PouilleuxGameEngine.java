package fr.pantheonsorbonne.miage.engine;

import java.util.*;
import fr.pantheonsorbonne.miage.enums.PairType;
import fr.pantheonsorbonne.miage.enums.Value;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Symbol;

public abstract class PouilleuxGameEngine {

    //protected final List<String> playerNames;
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
        playTurn();
    }

    protected abstract List<String> getInitialPlayers();

    protected abstract void giveCardsToPlayer(String player);

    protected void initGame(){
        //System.out.println("Cartes initiales des joueurs :");
        for (String player : getInitialPlayers()) {
            //System.out.println(player + " a :");
            giveCardsToPlayer(player);
            //System.out.println("-------------------------------------------------");
        }

        //System.out.println("Début du jeu !\n");
    }
    
    protected void playTurn(){

        boolean reverse = false;
        int nbToursCouleur = 0;
        boolean imposeColor = false;
        String colorCurrent = null;

        List<String> players = new ArrayList<>(getInitialPlayers());

        int currentIndex = 0;
        for (;;) {
                String currentPlayer = players.get(currentIndex);
                String nextPlayer = players.get((currentIndex + 1) % players.size());

                System.out.println("------------------------- Au tour de " + currentPlayer + " -------------------------");

                pickOneCard(currentPlayer, nextPlayer);
                
                PairType pairType;
                if (imposeColor && nbToursCouleur % players.size() != 0) {
                    pairType = discardPairs(currentPlayer, imposeColor, colorCurrent);
                    nbToursCouleur++;
                } 
                else {
                    imposeColor = false;
                    colorCurrent = null;
                    pairType = discardPairs(currentPlayer, imposeColor, colorCurrent);
                    nbToursCouleur = 1;
                }
                
                if(isWinner(currentPlayer)){
                    System.out.println("\n" + currentPlayer + " n'a plus de cartes et a gagné !");
                    WhoIsLooser();
                    return;
                }
                switch (pairType) {
                    case PAIRE_DE_DIX:
                        //handlePaireDeDix(currentPlayer, nextPlayer);
                        currentIndex = handleSkipTurn(players, currentIndex, reverse);
                        break;
                    case PAIRE_D_AS:
                        imposeColor = true;
                        colorCurrent = getLastDiscardColor(currentPlayer);
                        nbToursCouleur = 1;
                        //handlePaireDAs(currentPlayer, colorCurrent);
                        break;
                    case PAIRE_DE_DAME:
                        reverse = !reverse;
                        //handlePaireDeDame();
                        break;
                    case PAIRE_DE_ROI:
                        //handlePaireDeRoi(currentPlayer);
                        break;
                    case PAIRE_DE_VALET:
                        //handlePaireDeValet(currentPlayer);
                        break;
                    default:
                        break;
                }
                currentIndex = getNextPlayerIndex(players.size(), currentIndex, reverse);
        }
    }

    private int handleSkipTurn(List<String> players, int currentIndex, boolean reverse) {
        return getNextPlayerIndex(players.size(), getNextPlayerIndex(players.size(), currentIndex, reverse), reverse);
    }

    private int getNextPlayerIndex(int totalPlayers, int currentIndex, boolean reverse) {
        return reverse ? (currentIndex - 1 + totalPlayers) % totalPlayers : (currentIndex + 1) % totalPlayers;
    }

    protected void turnDirection(boolean reverse, int index){
        if (reverse) {
            index = (index - 2 + getInitialPlayers().size()) % getInitialPlayers().size();
        }
    }

    protected abstract boolean isWinner(String currentPlayer);

    abstract protected void WhoIsLooser();

    abstract protected String getHandString(String player);

    abstract protected void pickOneCard(String currentPlayer, String nextPlayer);

    abstract protected PairType discardPairs(String player, boolean imposeColor, String colorCurrent);

    abstract protected String getLastDiscardColor(String player);

    abstract protected void switchTwoCards(String currentPlayer);

    abstract protected void volerCarte(String currentPlayer);  
    
    protected abstract void handlePaireDeDix(String currentPlayer, String nextPlayer);

    protected abstract void handlePaireDAs(String currentPlayer, String color);

    protected abstract void handlePaireDeDame();

    protected abstract void handlePaireDeRoi(String currentPlayer);

    protected abstract void handlePaireDeValet(String currentPlayer);

}