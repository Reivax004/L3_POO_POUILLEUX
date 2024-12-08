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

        for (;;) {

            for (int i = 0; i < players.size(); i++) {
                Player currentPlayer = players.get(i);
                Player nextPlayer = players.get((i + 1) % players.size());

                System.out.println("------------------------- Au tour de " + currentPlayer.getName() + " -------------------------");
                System.out.println(currentPlayer.getName() + " a " + currentPlayer.getHand().size() + " cartes.");

                currentPlayer.pickOneCard(nextPlayer.getHand());

                String pairType = currentPlayer.discardPairs();

                // Gère les effets des paires spéciales
                if (pairType.equals("Paire de 10")) {
                    System.out.println(currentPlayer.getName() + " a défaussé une paire de 10, le joueur suivant " + nextPlayer.getName() + " saute son tour.");
                    i++; // Le joueur suivant saute son tour
                } else if (pairType.equals("Paire d'As")) {
                    System.out.println(currentPlayer.getName() + " a défaussé une paire d'As, imposant une couleur de paire."); // Rouge ou Noir
                    // TODO : Implémenter l'imposition de couleur
                } else if (pairType.equals("Paire de Dame")) {
                    System.out.println(currentPlayer.getName() + " a défaussé une paire de Dame, le sens du jeu est inversé.");
                    // TODO : Inverser le sens du jeu
                } else if (pairType.equals("Paire de Roi")) {
                    System.out.println(currentPlayer.getName() + " a défaussé une paire de Roi, échange de cartes avec un autre joueur.");
                    currentPlayer.echange2Cartes(currentPlayer, players);
                } else if (pairType.equals("Paire de Valet")) {
                    System.out.println(currentPlayer.getName() + " a défaussé une paire de Valet, vol d'une carte.");
                    currentPlayer.volerCarte(currentPlayer, players);
                }

                System.out.println(currentPlayer.getName() + " a maintenant " + currentPlayer.getHand().size() + " cartes.");

                // Vérifier si le joueur actif a gagné
                if (currentPlayer.getHand().isEmpty()) {
                    System.out.println("\n" + currentPlayer.getName() + " n'a plus de cartes et a gagné !");

                    for (Player pouilleux : players) {
                        if (pouilleux.getHand().contains(new Card(Value.VALET, Symbol.PIQUE))) {
                            System.out
                                    .println(pouilleux.getName() + " est le pouilleux, car il a le Valet de Pique !\n");
                        }
                    }

                    return; // Fin de la partie, on sort de la boucle infinie
                }

            }

            /*             
            // Affichage des cartes pour chaque joueur
            for (Player player : players) {
                System.out.println("Cartes de " + player.getName() + " :");
                System.out.println(player.getHandString());
                System.out.println("Le joueur " + player.getName() + " a " + player.getHand().size() + " cartes.");
                System.out.println("-------------------------------------------------");
            }
            */

        }
    }
}
