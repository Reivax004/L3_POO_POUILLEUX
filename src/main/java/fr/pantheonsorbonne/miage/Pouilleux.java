package fr.pantheonsorbonne.miage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pouilleux {
    public static void main(String... args) {

        Player p1 = new Player("Nicolas");
        Player p2 = new Player("Elio");
        
        Player p3 = new Player("Arthur");
        //Player p4 = new Player("Eva");


        //System.out.println(Deck.getRandomCards(2));
        List<Card> mainOne = Deck.getRandomCards(3);
        p1.setHand(mainOne);

        List<Card> mainTwo = Deck.getRandomCards(3);
        p2.setHand(mainTwo);
        
        List<Card> mainThree = Deck.getRandomCards(3);
        p3.setHand(mainThree);
        
        //List<Card> mainFour = Deck.getRandomCards(4);
        //p4.setHand(mainFour);

        // Tirage dans le jeu de chacun

        p1.pickOneCard(mainTwo);
        
        p2.pickOneCard(mainThree);
        
        p3.pickOneCard(mainOne);

        
        System.out.println("Carte du Joueur 1 : ");

        System.out.println(p1.getHandString());

        System.out.println("______________________________"+"\n");

        System.out.println("Carte du Joueur 2 : ");

        System.out.println(p2.getHandString());

        System.out.println("______________________________"+"\n");
        
        System.out.println("Carte du Joueur 3 : ");

        System.out.println(p3.getHandString());

        System.out.println("______________________________"+"\n");

        //System.out.println("Carte du Joueur 4 : ");

        //System.out.println(p4.getHandString());

        





    }
}
