package gameplay;

import java.util.ArrayList;

import entities.Card;
import entities.Player;
import utility.DeckBuilder;

public class Game {

    private Card hiddenCard;
    private ArrayList<Card> deck;

    public void startOfRoundCards(Player p) {
        drawCard(p);
        drawCard(p);
        if (p.getAceCount() == 2 && p.getHand().size() == 2) {
            p.setSum(12);
        }
    }

    // draws 1 card
    public Card drawCard(Player p){
            Card card = deck.remove(deck.size() - 1);
            p.setSum(p.getSum() + card.getValue());
            p.setAceCount(p.getAceCount() + (card.isAce() ? 1 : 0));
            p.getHand().add(card);

        return card;
    }

    public void startGame(Player p, Player d) {
        // deck
        deck = DeckBuilder.buildDeck();
        d.setFreshHand();
        p.setFreshHand();

        // Dealer's first card is hidden.
        hiddenCard = drawCard(d);
        // Dealer's 2nd card
        drawCard(d);
        startOfRoundCards(p);

        /* FOR DEBUGGING */
        // System.out.println("DEALER:");
        // System.out.println("Hidden Card: " + hiddenCard);
        // System.out.println("Hand: " + d.getHand() + " Sum: " + d.getSum() + " Ace count: " + d.getAceCount());

        
        // System.out.println("PLAYER: ");
        // System.out.println("Player's hand size is: " + p.getHand().size());
        // System.out.println("hand: " + p.getHand() + " Sum: " + p.getSum() + " Ace count:" + p.getAceCount());

    }
    

    public int recountPlayerSum(Player p) {
        while (p.getSum() > 21 && p.getAceCount() > 0) {
            p.setSum(p.getSum() - 10);
            p.setAceCount(p.getAceCount() - 1);
        }

        System.out.println("recountPlayerSum() method ran successfully");
        return p.getSum();
    }

    public void drawDealerCards(Player d) {
        while (d.getSum() < 16 && d.getHand().size()<5) {
            drawCard(d);
            recountPlayerSum(d);
        }
    }

    public String getHiddenCardPath() {
        String imagePath = "images/cards/" + hiddenCard.toString() + ".gif";
        return imagePath;
    }

    public String determineWinner(Player p, Player d, int betAmount) {
        if (p.getSum() > 21 && d.getSum() > 21) {
            p.setMoney(p.getMoney() + betAmount);
            return "Tie!";
        } else if (p.getSum() > 21) {
            return "BUST! Dealer Win!";
        } else if (d.getSum() > 21 || p.getHand().size() == 5) {
            p.setMoney(p.getMoney() + betAmount * 2);
            return "You Win!";
        } else if (p.getSum() == d.getSum()) {
            p.setMoney(p.getMoney() + betAmount);
            return "Tie!";
        } else if (p.getSum() > d.getSum()) {
            p.setMoney(p.getMoney() + betAmount * 2);
            return "You Win!";
        }
        // By default, the dealer wins if none of the if statements above trigger.

        return "Dealer Win!";
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

}