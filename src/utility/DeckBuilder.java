package utility;
import java.util.*;
import java.util.Random;

import entities.*;

// Builds a new deck
public class DeckBuilder {
    public static ArrayList<Card> buildDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        String[] values = { "a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k" };
        String[] types = { "c", "d", "h", "s" };

         for (String value : values) {
            for (String type : types) {
                deck.add(new Card(value, type));
            }
        }

        System.out.println("BUILD DECK:");
        System.out.println(deck);

        return shuffle(deck);
    }
    
    // Swap around 2 cards at a time randomly to shuffle the deck
    public static ArrayList<Card> shuffle(ArrayList<Card> deck) {
        Random random = new Random(); // shuffle deck
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }

        System.out.println("AFTER SHUFFLE");
        System.out.println(deck);

        return deck;
    }
}
