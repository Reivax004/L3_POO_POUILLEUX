package fr.pantheonsorbonne.miage;

import java.util.Arrays;
import java.util.List;

import fr.pantheonsorbonne.miage.enums.PairType;
import fr.pantheonsorbonne.miage.enums.Value;

public class Pouilleux {

    public static void main(String... args) {
        Player p1 = new Player("Nicolas");
        Player p2 = new Player("Elio");
        Player p3 = new Player("Arthur");
        Player p4 = new Player("Eva");

        List<Player> players = Arrays.asList(p1, p2, p3, p4);

        play(players);
    }

    public static void play(List<Player> players) {
        for (Player player : players) {
            List<Card> hand = Deck.getRandomCards(players.size());
            player.setHand(hand);
            System.out.println(player.getName() + " a reçu " + player.getHand().size() + " cartes.");
        }

        System.out.println("\nCartes initiales des joueurs :");
        for (Player player : players) {
            System.out.println(player.getName() + " a reçu :");
            System.out.println(player.getHandString());
            System.out.println("-------------------------------------------------");
        }

        System.out.println("Début du jeu !\n");

        boolean reverse = false;

        for (;;) {
            for (int i = 0; i < players.size(); i++) {
                Player currentPlayer = players.get(i);
                Player nextPlayer = players.get((i + 1) % players.size());

                System.out.println("------------------------- Au tour de " + currentPlayer.getName() + " -------------------------");
                System.out.println(currentPlayer.getName() + " a " + currentPlayer.getHand().size() + " cartes.");

                currentPlayer.pickOneCard(nextPlayer.getHand());

                PairType pairType = currentPlayer.discardPairs();

                if (pairType.equals(PairType.PAIRE_DE_DIX)) {
                    System.out.println(currentPlayer.getName() + " a défaussé une paire de 10, le joueur suivant, " + nextPlayer.getName() + ", saute son tour.");
                    if (reverse) {
                        i--;
                    } else {
                        i++;
                    }
                } else if (pairType.equals(PairType.PAIRE_D_AS)) {
                    System.out.println(currentPlayer.getName() + " a défaussé une paire d'As, imposant une couleur de paire.");
                    // currentPlayer.imposeColor();
                } else if (pairType.equals(PairType.PAIRE_DE_DAME)) {
                    System.out.println(currentPlayer.getName() + " a défaussé une paire de Dame, le sens du jeu est inversé.");
                    reverse = !reverse;
                } else if (pairType.equals(PairType.PAIRE_DE_ROI)) {
                    System.out.println(currentPlayer.getName() + " a défaussé une paire de Roi, le joueur échange 2 cartes entre 2 joueurs.");
                    currentPlayer.echange2Cartes(currentPlayer, players);
                } else if (pairType.equals(PairType.PAIRE_DE_VALET)) {
                    System.out.println(currentPlayer.getName() + " a défaussé une paire de Valet, vol d'une carte.");
                    currentPlayer.volerCarte(currentPlayer, players);
                }

                System.out.println(currentPlayer.getName() + " a maintenant " + currentPlayer.getHand().size() + " cartes.");

                if (currentPlayer.getHand().isEmpty()) {
                    System.out.println("\n" + currentPlayer.getName() + " n'a plus de cartes et a gagné !");

                    for (Player pouilleux : players) {
                        if (pouilleux.getHand().contains(new Card(Value.VALET, Symbol.PIQUE))) {
                            System.out.println(pouilleux.getName() + " est le pouilleux, car il a le Valet de Pique !\n");
                        }
                    }

                    return;
                }
        
                if (reverse) {
                    i = (i - 2 + players.size()) % players.size();
                }
            }
        }
    }
}