package fr.pantheonsorbonne.miage;

import java.util.Arrays;
import java.util.List;

public class Pouilleux {

    public static void main(String... args) {
        Player p1 = new Player("Nicolas");
        Player p2 = new Player("Elio");
        Player p3 = new Player("Arthur");
        Player p4 = new Player("Eva");

        // Crée une liste de joueurs
        List<Player> players = Arrays.asList(p1, p2, p3, p4);

        play(players);
    }

    public static void play(List<Player> players) {
        // Distribution des cartes à chaque joueur
        for (Player player : players) {
            List<Card> hand = Deck.getRandomCards(players.size());
            player.setHand(hand);
            System.out.println(player.getName() + " a reçu " + player.getHand().size() + " cartes.");
        }

        // Afficher les cartes initiales de chaque joueur
        System.out.println("\nCartes initiales des joueurs :");
        for (Player player : players) {
            System.out.println(player.getName() + " a reçu :");
            System.out.println(player.getHandString());
            System.out.println("-------------------------------------------------");
        }

        System.out.println("Début du jeu !\n");
        int tour = 1;

        // Jouer en boucle jusqu'à ce qu'un joueur n'ait plus de cartes
        for (;;) {
            System.out.println("Tour " + tour++ + " :");

            // Tirage dans le jeu de chacun
            for (int i = 0; i < players.size(); i++) {
                Player currentPlayer = players.get(i);
                // Le joueur suivant dans la liste, avec modulo %
                Player nextPlayer = players.get((i + 1) % players.size());
                currentPlayer.pickOneCard(nextPlayer.getHand());
                // Défausser les paires après chaque tour
                if(currentPlayer.discardPairs() == 4){ //Si une paire de 10 est defaussé on passe au prochain
                    i++;
                }
                
            }

            // Défausser les paires après chaque tour
            /*
            for (Player player : players) {
                player.discardPairs();
            }*/

            // Affichage des cartes pour chaque joueur
            for (Player player : players) {
                System.out.println("Cartes de " + player.getName() + " :");
                System.out.println(player.getHandString());
                System.out.println("Le joueur " + player.getName() + " a " + player.getHand().size() + " cartes.");
                System.out.println("-------------------------------------------------");
            }

            // Vérifier si un joueur n'a plus de cartes après avoir affiché ses cartes
            for (Player player : players) {
                if (player.getHand().isEmpty()) {
                    System.out.println("\n" + player.getName() + " n'a plus de cartes et a gagné !");

                    // On cherche le second joueur qui sera le pouilleux (celui qui a le Valet de Pique)
                    for (Player pouilleux : players) {
                        if (pouilleux.getHand().contains(new Card(Value.VALET, Symbol.PIQUE))) {
                            System.out.println(pouilleux.getName() + " est le pouilleux, car il a le Valet de Pique !\n");
                        }
                    }

                    return; // Fin de la partie, on sort de la boucle infinie
                }
            }
        }
    }
}
//if(player.discardPairs() == 4)