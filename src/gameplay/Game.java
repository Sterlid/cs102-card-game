package gameplay;

import java.util.ArrayList;

import entities.Card;
import entities.Player;
import utility.DeckBuilder;

public class Game {

    private Card hiddenCard;

    public void startOfRoundCards(Player p, ArrayList<Card> deck) {
        drawCard(p,deck);
        drawCard(p,deck);
        if (p.getAceCount() == 2 && p.getHand().size() == 2) {
            p.setSum(12);
        }
    }

    // draws 1 card
    public void drawCard(Player p, ArrayList<Card> deck){
            Card card = deck.remove(deck.size() - 1);
            p.setSum(p.getSum() + card.getValue());
            p.setAceCount(p.getAceCount() + (card.isAce() ? 1 : 0));
            p.getHand().add(card);
    }

    public void startGame(Player p, Player d, ArrayList<Card> deck) {
        // deck
        deck = DeckBuilder.buildDeck();
        d.setFreshHand();
        p.setFreshHand();
        // Boolean to not redraw the start screen
        // firstRound = false;

        hiddenCard = deck.remove(deck.size() - 1); // remove card at top of deck
        d.getHand().add(hiddenCard);
        d.setSum(d.getSum() + hiddenCard.getValue());
        d.setAceCount(d.getAceCount() + (hiddenCard.isAce() ? 1 : 0));

        // Dealer's normal cards
        // startOfRoundCards(d, deck);
        drawCard(d, deck);

        System.out.println("DEALER:");
        System.out.println(hiddenCard);
        System.out.println("Hand: " + d.getHand() + " Sum: " + d.getSum() + " Ace count: " + d.getAceCount());

        // p.setSum(0);
        // p.setAceCount(0);

        startOfRoundCards(p, deck);
        System.out.println("Player's hand size is: " + p.getHand().size());

        System.out.println("PLAYER: ");
        System.out.println("hand: " + p.getHand() + " Sum: " + p.getSum() + " Ace count:" + p.getAceCount());

    }

    public int recountPlayerSum(Player p) {
        while (p.getSum() > 21 && p.getAceCount() > 0) {
            p.setSum(p.getSum() - 10);
            p.setAceCount(p.getAceCount() - 1);
        }

        System.out.println("recountPlayerSum() method ran successfully");
        return p.getSum();
    }

    public void drawDealerCards(Player d, ArrayList<Card> deck) {
        while (d.getSum() < 17) {
            drawCard(d,deck);
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

}
