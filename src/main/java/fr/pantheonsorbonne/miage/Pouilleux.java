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

        // Distribution des cartes à chaque joueur
        for (Player player : players) {
            List<Card> hand = Deck.getRandomCards(players.size());
            player.setHand(hand);
        }

        // Tirage dans le jeu de chacun
        for (int i = 0; i < players.size(); i++) {
            Player currentPlayer = players.get(i);
            // Le joueur suivant dans la liste, avec rotation (modulo %)
            Player nextPlayer = players.get((i + 1) % players.size());
            currentPlayer.pickOneCard(nextPlayer.getHand());
        }

        // Affichage des cartes pour chaque joueur
        for (Player player : players) {
            System.out.println("Carte de " + player.getName() + " :");
            System.out.println(player.getHandString());     
            
        /// Fonction à utiliser dans un "play", pas vraiment utile ici car y'a que 1 tour
            // player.discardPairs();
            // System.out.println("Carte de " + player.getName() + " après avoir défaussé les paires :");
            // System.out.println(player.getHandString());
            // System.out.println("Il reste " + player.getHand().size() + " cartes dans la main de " + player.getName());
            // System.out.println("______________________________\n");
        }
    }
}
