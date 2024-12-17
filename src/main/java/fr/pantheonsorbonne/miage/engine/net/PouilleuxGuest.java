package fr.pantheonsorbonne.miage.engine.net;

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
    static List<Card> hand = new ArrayList<>();
    static List<Card> discardedPairs = new ArrayList<>();
    static final PlayerFacade playerFacade = Facade.getFacade();
    static Game pouilleux;
    
        public static void main(String... args) {
    
            playerFacade.waitReady();
            playerFacade.createNewPlayer(playerId);
            pouilleux = playerFacade.autoJoinGame("POUILLEUX");
    
            while (true) {
                GameCommand command = playerFacade.receiveGameCommand(pouilleux);
                
                switch (command.name()) {
                    
                    case "cardsForYou":
                        handleCardsForYou(command);
                        break;
                    
                    case "GiveACard":
                        handleGiveACard(command);
                        break;

                    case "EvaluateAPair":
                        handleEvaluateAPair(command);
                        break;

                    case "DiscardAPair":
                        handleDiscardAPair(command);
                        break;
                }
            }
        }
    private static void handleCardsForYou(GameCommand command) {
        for (Card card : Card.stringToCards(command.body())) {
            hand.add(card);
        }
        System.out.println(Card.cardsToString(hand));
    }
    private static void handleGiveACard(GameCommand command){
        //System.out.println("Le joueur a en main "+hand.size()+" carte(s)\n");
        Random random = new Random();
        int randomIndex = random.nextInt(hand.size());
        Card card = hand.get(randomIndex);
        hand.remove(card);
        System.out.println("Le joueur a en main "+hand.size()+" carte(s)\n");
        playerFacade.sendGameCommandToAll(pouilleux, new GameCommand("card", card.toString()));
    }

    private static void handleEvaluateAPair(GameCommand command){
        List<Card> cardsToRemove = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            Card card1 = hand.get(i);
            for (int j = i + 1; j < hand.size(); j++) {
                Card card2 = hand.get(j);
                if (card1.getValue().equals(card2.getValue()) && card1.getSymbol().getColor().equals(card2.getSymbol().getColor())) {          
                    if (cardsToRemove.size() == 0) {
                    
                        if (card1.getValue().ordinal() > 4) {
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        } else if (card1.getValue() == Value.DIX) {
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        } else if (card1.getValue() == Value.VALET) {
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        } else if (card1.getValue() == Value.DAME) {
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        } else if (card1.getValue() == Value.ROI) {
                            cardsToRemove.add(card1);
                            cardsToRemove.add(card2);
                        } else if (card1.getValue() == Value.AS) {
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
        playerFacade.sendGameCommandToAll(pouilleux, new GameCommand("card", Card.cardsToString(cardsToRemove)));
        List<Card> toRemove = new ArrayList<>();
        for (Card card : hand) {
            if (cardsToRemove.contains(card)) {
                toRemove.add(card);
            }
        }
        hand.removeAll(toRemove);
    }

    private static void handleDiscardAPair(GameCommand command) {
        System.out.println("Le joueur défausse : "+"\n");
        System.out.println(command.body());
        for (Card card : Card.stringToCards(command.body())) {
            hand.remove(card);
        }
    }
}