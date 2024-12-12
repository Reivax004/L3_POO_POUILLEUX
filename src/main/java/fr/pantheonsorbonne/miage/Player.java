package fr.pantheonsorbonne.miage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import fr.pantheonsorbonne.miage.enums.Value;
import fr.pantheonsorbonne.miage.enums.PairType;

public class Player {
    final String name;
    private List<Card> hand;
    private Random random = new Random();

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<Card>();
    }

    public void setHand(List<Card> deck) {
        this.hand = deck;
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public String getName() {
        return this.name;
    }

    public String getHandString() {
        if (hand.isEmpty()) {
            return "Aucune carte";
        }
        StringBuilder builder = new StringBuilder();
        for (Card c : this.hand) {
            if (c != null) {
                builder.append("\t - " + c.toString());
            } else {
                builder.append("\t - ");
            }
            builder.append("\n ");
        }
        return builder.toString().substring(0, builder.length() - 2);
    }


    public PairType discardPairs() {
        List<Card> cardsToRemove = new ArrayList<>();
        PairType pairType = PairType.AUCUNE_PAIRE;

        for (int i = 0; i < hand.size(); i++) {
            Card card1 = hand.get(i);
            for (int j = i + 1; j < hand.size(); j++) {
                Card card2 = hand.get(j);
                if (card1.getValue().equals(card2.getValue()) && card1.getSymbol().getColor().equals(card2.getSymbol().getColor())) {

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

        if (cardsToRemove.size() == 0) {
            System.out.println("Le joueur n'a pas de paire.");
        } else {
            System.out.println("Le joueur défausse : " + cardsToRemove);
            hand.removeAll(cardsToRemove);
        }

        return pairType;
    }

    public void pickOneCard(List<Card> handProchainJoueur) {
        if (this.getHand().isEmpty()) {
            System.out.println(this.getName() + " n'a plus de cartes.");
            return;
        }

        if (handProchainJoueur.isEmpty()) {
            System.out.println("Le joueur suivant n'a plus de cartes.");
            return;
        }

        int randomIndex = random.nextInt(handProchainJoueur.size());
        Card card = handProchainJoueur.get(randomIndex);
        handProchainJoueur.remove(card);
        this.getHand().add(card);
        System.out.println(this.getName() + " a pris la carte " + card + " au joueur suivant.");
    }

    public void echange2Cartes(Player joueurActuel, List<Player> joueurs) {
        Player joueurCible1 = joueurs.get(random.nextInt(joueurs.size()));
        Player joueurCible2 = joueurs.get(random.nextInt(joueurs.size()));

        int indexCarteDuJ1 = random.nextInt(joueurCible1.getHand().size());
        int indexCarteDuJ2 = random.nextInt(joueurCible2.getHand().size());
        Card carteAEchanger1 = joueurCible1.getHand().get(indexCarteDuJ1);
        Card carteAEchanger2 = joueurCible2.getHand().get(indexCarteDuJ2);
        
        joueurCible1.getHand().set(indexCarteDuJ1, carteAEchanger2);
        joueurCible2.getHand().set(indexCarteDuJ2, carteAEchanger1);

        System.out.println(joueurActuel.getName() + " échange la carte " + carteAEchanger1 + " avec " + joueurCible1.getName() +
                            " et la carte " + carteAEchanger2 + " avec " + joueurCible2.getName());
    }

    public void volerCarte(Player joueurActuel, List<Player> joueurs) {
        Player joueurCible = joueurs.get(random.nextInt(joueurs.size()));
        Card carteVolee = joueurCible.getHand().get(random.nextInt(joueurCible.getHand().size()));
        joueurActuel.getHand().add(carteVolee);
        joueurCible.getHand().remove(carteVolee);
        System.out.println(joueurActuel.getName() + " vole la carte " + carteVolee + " à " + joueurCible.getName());
    }

}