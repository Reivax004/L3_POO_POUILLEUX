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
        System.out.println("Cartes initiales des joueurs :");
        for (String player : getInitialPlayers()) {
            giveCardsToPlayer(player);
            System.out.println(player + " a :");
            System.out.println(getHandString(player));
            System.out.println("-------------------------------------------------");
        }

        System.out.println("Début du jeu !\n");
    }
    
    protected void playTurn(){

        boolean reverse = false;
        int nbToursCouleur = 0;
        boolean imposeColor = false;
        String colorCurrent = null;
        
        for (;;) {
            for (int i = 0; i < getInitialPlayers().size(); i++) {
                String currentPlayer = getInitialPlayers().get(i);
                String nextPlayer = getInitialPlayers().get((i + 1) % getInitialPlayers().size());

                System.out.println("------------------------- Au tour de " + currentPlayer + " -------------------------");
                System.out.println(currentPlayer + " a " + playerCards.get(currentPlayer).size() + " cartes.");

                pickOneCard(currentPlayer, nextPlayer);

                PairType pairType;
                if (imposeColor && nbToursCouleur % getInitialPlayers().size() != 0) {
                    pairType = discardPairs(currentPlayer, imposeColor, colorCurrent);
                    nbToursCouleur++;
                } else {
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
                        handlePaireDeDix(currentPlayer, nextPlayer,i,reverse);
                        break;
                    case PAIRE_D_AS:
                        handlePaireDAs(currentPlayer,imposeColor,colorCurrent,nbToursCouleur);
                        break;
                    case PAIRE_DE_DAME:
                        handlePaireDeDame(currentPlayer, reverse);
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
                System.out.println(currentPlayer + " a maintenant " + playerCards.get(currentPlayer).size() + " cartes.");

            }
        }
    }
    protected void turnDirection(boolean reverse, int i){
        if (reverse) {
            i = (i - 2 + getInitialPlayers().size()) % getInitialPlayers().size();
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
    
    protected abstract void handlePaireDeDix(String currentPlayer, String nextPlayer, int i, boolean reverse);

    protected abstract void handlePaireDAs(String currentPlayer, boolean imposeColor, String colorCurrent, int nbToursCouleur );

    protected abstract void handlePaireDeDame(String currentPlayer, boolean reverse);

    protected abstract void handlePaireDeRoi(String currentPlayer);

    protected abstract void handlePaireDeValet(String currentPlayer);

}