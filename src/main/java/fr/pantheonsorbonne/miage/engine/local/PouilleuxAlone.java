package fr.pantheonsorbonne.miage.engine.local;

import java.util.*;

import fr.pantheonsorbonne.miage.engine.PouilleuxGameEngine;
import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Deck;
import fr.pantheonsorbonne.miage.game.Symbol;
import fr.pantheonsorbonne.miage.enums.PairType;
import fr.pantheonsorbonne.miage.enums.Value;


public class PouilleuxAlone extends PouilleuxGameEngine {

    private final List<String> playerNames;
    //private final Map<String, Queue<Card>> playerCards = new HashMap<>();

    public PouilleuxAlone(Deck deck, int nbPlayer, List<String> playerNames) {
        super(deck, nbPlayer);
        this.playerNames = playerNames;
        //this.playerCards = playerCards;
    }

    public static void main(String... args) {

        Deck deck = new Deck();

        PouilleuxAlone game = new PouilleuxAlone(deck, 4, Arrays.asList("Nicolas", "Elio", "Arthur", "Eva"));
        game.play();
    }

    @Override
    protected List<String> getInitialPlayers() {
        return this.playerNames;
    }
    @Override
    protected void giveCardsToPlayer(String player){
        Queue<Card> hand = new LinkedList<>(Deck.getRandomCards(nbPlayer));
        playerCards.put(player, hand);

    }
    @Override
    protected String getHandString(String player){
        Queue<Card> hand = playerCards.get(player);
        if (hand.isEmpty()) {
            return "Aucune carte";
        }
        StringBuilder builder = new StringBuilder();
        for (Card c : hand) {
            builder.append("\t - ").append(c).append("\n");
        }
        return builder.toString().trim();
    }
    @Override
    protected boolean isWinner(String currentPlayer){
        if (playerCards.get(currentPlayer).isEmpty()) {
            return true;
        }
        return false;
    }
    @Override
    protected void WhoIsLooser(){
        for (String pouilleux : getInitialPlayers()) {
            if (playerCards.get(pouilleux).contains(new Card(Value.VALET, Symbol.PIQUE))) {
                System.out.println(pouilleux + " est le pouilleux, car il a le Valet de Pique !\n");
            }
        }
    }
    @Override
    protected void pickOneCard(String currentPlayer, String nextPlayer) {
        Queue<Card> currentHand = playerCards.get(currentPlayer);
        Queue<Card> nextHand = playerCards.get(nextPlayer);
    
        if (currentHand.isEmpty()) {
            System.out.println(currentPlayer + " n'a plus de cartes.");
            return;
        }
    
        if (nextHand.isEmpty()) {
            System.out.println(nextPlayer + " n'a plus de cartes.");
            return;
        }
        List<Card> nextHandList = new ArrayList<>(nextHand);
        Random random = new Random();
        int randomIndex = random.nextInt(nextHandList.size());
    
        Card card = nextHandList.remove(randomIndex);
    
        nextHand.clear();
        nextHand.addAll(nextHandList);
    
        currentHand.add(card);
    
        System.out.println(currentPlayer + " a pris une carte aléatoire " + card + " au joueur suivant.");
    }
    @Override
    protected PairType discardPairs(String player, boolean imposeColor, String colorCurrent) {
        Queue<Card> hand = playerCards.get(player);
        List<Card> cardsToRemove = new ArrayList<>();
        PairType pairType = PairType.AUCUNE_PAIRE;
        List<Card> handList = new ArrayList<>(hand);
        for (int i = 0; i < handList.size(); i++) {
            Card card1 = handList.get(i);
            for (int j = i + 1; j < handList.size(); j++) {
                Card card2 = handList.get(j);
                if (card1.getValue().equals(card2.getValue()) && card1.getSymbol().getColor().equals(card2.getSymbol().getColor())) {
                    System.out.println(card1.getValue()+" "+card1.getSymbol().getColor());
                    if(imposeColor){
                        
                        if(card1.getSymbol().getColor().equals(colorCurrent)){
                            
                            if (cardsToRemove.size() == 0) {
                                
                                if (card1.getValue().ordinal() > 4) {
                                    pairType = PairType.PAIRE_NORMALE;
                                    cardsToRemove.add(card1);
                                    cardsToRemove.add(card2);
                                } else if (card1.getValue() == Value.DIX) {
                                    pairType = PairType.PAIRE_DE_DIX;
                                    cardsToRemove.add(card1);
                                    cardsToRemove.add(card2);
                                } else if (card1.getValue() == Value.VALET) {
                                    pairType = PairType.PAIRE_DE_VALET;
                                    cardsToRemove.add(card1);
                                    cardsToRemove.add(card2);
                                } else if (card1.getValue() == Value.DAME) {
                                    pairType = PairType.PAIRE_DE_DAME;
                                    cardsToRemove.add(card1);
                                    cardsToRemove.add(card2);
                                } else if (card1.getValue() == Value.ROI) {
                                    pairType = PairType.PAIRE_DE_ROI;
                                    cardsToRemove.add(card1);
                                    cardsToRemove.add(card2);
                                } else if (card1.getValue() == Value.AS) {
                                    pairType = PairType.PAIRE_D_AS;
                                    colorCurrent = card1.getSymbol().getColor();
                                    cardsToRemove.add(card1);
                                    cardsToRemove.add(card2);
                                }
                            } 
                            else if (card1.getValue().ordinal() > 4 && cardsToRemove.get(0).getValue().ordinal() > 4) {
                                cardsToRemove.add(card1);
                                cardsToRemove.add(card2);
                            }
                        }
                        else{
                            //System.out.println(card1.getValue()+" "+card1.getSymbol().getColor());
                            continue;
                        }
   
                    }
                    else{
                        if (cardsToRemove.size() == 0) {
                            if (card1.getValue().ordinal() > 4) {
                                pairType = PairType.PAIRE_NORMALE;
                                cardsToRemove.add(card1);
                                cardsToRemove.add(card2);
                            } else if (card1.getValue() == Value.DIX) {
                                pairType = PairType.PAIRE_DE_DIX;
                                cardsToRemove.add(card1);
                                cardsToRemove.add(card2);
                            } else if (card1.getValue() == Value.VALET) {
                                pairType = PairType.PAIRE_DE_VALET;
                                cardsToRemove.add(card1);
                                cardsToRemove.add(card2);
                            } else if (card1.getValue() == Value.DAME) {
                                pairType = PairType.PAIRE_DE_DAME;
                                cardsToRemove.add(card1);
                                cardsToRemove.add(card2);
                            } else if (card1.getValue() == Value.ROI) {
                                pairType = PairType.PAIRE_DE_ROI;
                                cardsToRemove.add(card1);
                                cardsToRemove.add(card2);
                            } else if (card1.getValue() == Value.AS) {
                                pairType = PairType.PAIRE_D_AS;
                                colorCurrent = card1.getSymbol().getColor();
                                cardsToRemove.add(card1);
                                cardsToRemove.add(card2);
                            }
                        } 
                        else if (card1.getValue().ordinal() > 4 && cardsToRemove.get(0).getValue().ordinal() > 4) {
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        }
                    }
                
                }
            
            }
        
        }
        if (pairType == PairType.PAIRE_D_AS) {
            discardedPairs.addAll(cardsToRemove);
        }
        
        System.out.println(colorCurrent);
        if (cardsToRemove.size() == 0) {
            System.out.println("Le joueur n'a pas de paire.");
        } 
        else {
            System.out.println("Le joueur défausse : " + cardsToRemove);
            hand.removeAll(cardsToRemove);
        }

        return pairType;
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
        Random random = new Random();
    
        List<String> otherPlayers = new ArrayList<>(playerCards.keySet());
        otherPlayers.remove(currentPlayer);
        if (otherPlayers.size() < 2) {
            System.out.println("Pas assez de joueurs pour un échange.");
            return;
        }
    
        String player1 = otherPlayers.get(random.nextInt(otherPlayers.size()));
        String player2;
        do {
            player2 = otherPlayers.get(random.nextInt(otherPlayers.size()));
        } while (player1.equals(player2));
    
        Queue<Card> hand1 = playerCards.get(player1);
        Queue<Card> hand2 = playerCards.get(player2);
    
        if (hand1.isEmpty() || hand2.isEmpty()) {
            System.out.println("L'échange n'est pas possible car l'un des joueurs n'a pas de cartes.");
            return;
        }
        Card card1 = ((LinkedList<Card>) hand1).removeFirst();
        Card card2 = ((LinkedList<Card>) hand2).removeFirst();
    
        hand1.add(card2);
        hand2.add(card1);
    
        System.out.println("Échange effectué entre " + player1 + " et " + player2 + ":");
        System.out.println(player1 + " reçoit " + card2);
        System.out.println(player2 + " reçoit " + card1);
    }
    @Override
    protected void volerCarte(String currentPlayer) {
        Random random = new Random();
    
        List<String> otherPlayers = new ArrayList<>(playerCards.keySet());
        otherPlayers.remove(currentPlayer);
        if (otherPlayers.isEmpty()) {
            System.out.println("Pas de joueur à voler.");
            return;
        }
    
        String targetPlayer = otherPlayers.get(random.nextInt(otherPlayers.size()));
    
        Queue<Card> targetHand = playerCards.get(targetPlayer);
        Queue<Card> currentHand = playerCards.get(currentPlayer);
    
        if (targetHand.isEmpty()) {
            System.out.println(targetPlayer + " n'a pas de cartes à voler.");
            return;
        }
    
        Card stolenCard = ((LinkedList<Card>) targetHand).removeFirst();
        currentHand.add(stolenCard);
    
        System.out.println(currentPlayer + " a volé la carte " + stolenCard + " à " + targetPlayer + ".");
    }
    protected void handlePaireDeDix(String currentPlayer, String nextPlayer, int i, boolean reverse){
        System.out.println(currentPlayer+ " a défaussé une paire de 10, le joueur suivant, " + nextPlayer + ", saute son tour.");
        if (reverse) {
            i--;
        } else {
            i++;
        }
    }
    protected void handlePaireDAs(String currentPlayer, boolean imposeColor, String colorCurrent, int nbToursCouleur){
        System.out.println(currentPlayer+ " a défaussé une paire d'As, imposant une couleur de paire.");
        imposeColor = true;
        colorCurrent = getLastDiscardColor(currentPlayer);
        nbToursCouleur = 1;
    }

    protected void handlePaireDeDame(String currentPlayer, boolean reverse){
        System.out.println(currentPlayer+ " a défaussé une paire de Dame, le sens du jeu est inversé.");
        reverse = !reverse;
    }

    protected void handlePaireDeRoi(String currentPlayer){
        System.out.println(currentPlayer+ " a défaussé une paire de Roi, le joueur échange 2 cartes entre 2 joueurs.");
        switchTwoCards(currentPlayer);
    }

    protected void handlePaireDeValet(String currentPlayer){
        System.out.println(currentPlayer+ " a défaussé une paire de Valet, vol d'une carte.");
        volerCarte(currentPlayer);
    }
}   

