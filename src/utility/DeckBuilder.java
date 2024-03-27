package utility;
import java.util.*;

import entities.*;

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

        Collections.shuffle(deck);
        return deck;
    }

}
