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
        if (joueurActuel.getHand().isEmpty()) {
            System.out.println(joueurActuel.getName() + " n'a plus de cartes à échanger.");
            return;
        }

        Player joueurCible1 = joueurs.get(random.nextInt(joueurs.size()));
        Player joueurCible2 = joueurs.get(random.nextInt(joueurs.size()));

        if (joueurActuel.getHand().size() < 2) {
            System.out.println(joueurActuel.getName() + " n'a pas assez de cartes pour échanger.");
            return;
        }

        int indexCarteActuelle1 = random.nextInt(joueurActuel.getHand().size());
        int indexCarteActuelle2 = random.nextInt(joueurActuel.getHand().size());
        while (indexCarteActuelle1 == indexCarteActuelle2) {
            indexCarteActuelle2 = random.nextInt(joueurActuel.getHand().size());
        }

        int indexCarteCible1 = random.nextInt(joueurCible1.getHand().size());
        int indexCarteCible2 = random.nextInt(joueurCible2.getHand().size());
        while (indexCarteCible1 == indexCarteCible2) {
            indexCarteCible2 = random.nextInt(joueurCible2.getHand().size());
        }

        Card carteAEchanger1 = joueurActuel.getHand().get(indexCarteActuelle1);
        Card carteAEchanger2 = joueurActuel.getHand().get(indexCarteActuelle2);
        Card carteCible1 = joueurCible1.getHand().get(indexCarteCible1);
        Card carteCible2 = joueurCible2.getHand().get(indexCarteCible2);

        joueurActuel.getHand().set(indexCarteActuelle1, carteCible1);
        joueurActuel.getHand().set(indexCarteActuelle2, carteCible2);
        joueurCible1.getHand().set(indexCarteCible1, carteAEchanger1);
        joueurCible2.getHand().set(indexCarteCible2, carteAEchanger2);

        System.out.println(joueurActuel.getName() + " échange les cartes " + carteAEchanger1 + " et " + carteAEchanger2
                + "\n avec les cartes " + carteCible1 + " de " + joueurCible1.getName() + " et " + carteCible2 + " de "
                + joueurCible2.getName());
    }

    public void volerCarte(Player joueurActuel, List<Player> joueurs) {
        Player joueurCible = joueurs.get(random.nextInt(joueurs.size()));
        Card carteVolee = joueurCible.getHand().get(random.nextInt(joueurCible.getHand().size()));
        joueurActuel.getHand().add(carteVolee);
        joueurCible.getHand().remove(carteVolee);
        System.out.println(joueurActuel.getName() + " vole la carte " + carteVolee + " à " + joueurCible.getName());
    }

}