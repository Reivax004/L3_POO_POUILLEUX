package fr.pantheonsorbonne.miage.engine.net;

import java.util.*;
import fr.pantheonsorbonne.miage.enums.Value;
import fr.pantheonsorbonne.miage.game.Card;
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
                case "cardForYou":
                    handleCardForYou(command);
                    break;

                case "GiveACard":
                    handleGiveACard(command);
                    break;

                case "DiscardAPair":
                    handleDiscardAPair(command);
                    break;
                case "isWinner":
                    handleWinner(command);
                    break;
                case "WhoIsLooser":
                    handleLooser(command);
                    break;
            }
        }
    }

    private static void handleCardsForYou(GameCommand command) {
        for (Card card : Card.stringToCards(command.body())) {
            hand.add(card);
        }
    }

    private static void handleCardForYou(GameCommand command) {
        System.out.println("Le joueur a pioché " + Card.valueOf(command.body()) + "\n");
        hand.add(Card.valueOf(command.body()));
    }

    private static void handleGiveACard(GameCommand command) {
        System.out.println("\nMain du Joueur (" + hand.size() + " cartes) : ");
        System.out.println(Card.cardsToString(hand));

        if (hand.size() > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(hand.size());
            Card card = hand.get(randomIndex);
            playerFacade.sendGameCommandToAll(pouilleux, new GameCommand(card.toString()));
            hand.remove(card);
        } else {
            System.out.println("Le joueur n'a plus de cartes à donner.");
        }
    }

    private static void handleDiscardAPair(GameCommand command) {
        List<Card> cardsToRemove = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            Card card1 = hand.get(i);
            for (int j = i + 1; j < hand.size(); j++) {
                Card card2 = hand.get(j);
                if (card1.getValue().equals(card2.getValue())
                        && card1.getSymbol().getColor().equals(card2.getSymbol().getColor())
                        && card2.getSymbol().getColor().equals(command.body())) {

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
                    } else if (card1.getValue().ordinal() > 4 && cardsToRemove.get(0).getValue().ordinal() > 4) {
                        cardsToRemove.add(card1);
                        cardsToRemove.add(card2);
                    }
                } else if (card1.getValue().equals(card2.getValue())
                        && card1.getSymbol().getColor().equals(card2.getSymbol().getColor())
                        && command.body().equals("null")) {
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
                    } else if (card1.getValue().ordinal() > 4 && cardsToRemove.get(0).getValue().ordinal() > 4) {
                        cardsToRemove.add(card1);
                        cardsToRemove.add(card2);
                    }
                }
            }
        }
        playerFacade.sendGameCommandToAll(pouilleux, new GameCommand(playerId, Card.cardsToString(cardsToRemove)));
        List<Card> toRemove = new ArrayList<>();
        if (cardsToRemove.size() == 0) {
            System.out.println("Le joueur n'a pas de paire ");
        } else {
            System.out.println("Le joueur défausse : ");
            System.out.println(cardsToRemove);
        }
        for (Card card : hand) {
            if (cardsToRemove.contains(card)) {
                toRemove.add(card);
            }
        }
        hand.removeAll(toRemove);
    }

    private static void handleWinner(GameCommand command) {
        if (hand.size() == 0) {
            playerFacade.sendGameCommandToAll(pouilleux, new GameCommand(playerId, "true"));
            System.out.println("Je n'ai plus de cartes, j'ai gagné !");
        } else {
            playerFacade.sendGameCommandToAll(pouilleux, new GameCommand(playerId, "false"));
        }
    }

    private static void handleLooser(GameCommand command) {
        for (Card card : hand) {
            if (card.getValue().equals(Value.VALET) && card.getSymbol().getName().equals("Pique")) {
                System.out.println("J'ai le valet de pique, j'ai perdu !");
                return;
            }
        }
    }
}