package gameplay;

import java.util.ArrayList;

import entities.Card;
import entities.Player;
import utility.DeckBuilder;

public class Game {

    private Card hiddenCard;
    private ArrayList<Card> deck;

    // starts the round by having the player draw 2 card, if the player draws 2 aces it will be a sum of 12 as our ace is either 11 or 1 and it should not exceed 21
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
            if(card instanceof Ace){
            p.setAceCount(p.getAceCount() + 1);
            }
            p.getHand().add(card);

        return card;
    }

    /*Starts the game by building a new deck and resetting the player and dealer hand sum and acecount to 0.
    //The dealer and player draws their hand card.
    */
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
    
    // Change the ace points from 11 to 1 based on condition of hand sum for player
    public int recountPlayerSum(Player p) {
        while (p.getSum() > 21 && p.getAceCount() > 0) {
            p.setSum(p.getSum() - 10);
            p.setAceCount(p.getAceCount() - 1);
        }

        System.out.println("recountPlayerSum() method ran successfully");
        return p.getSum();
    }
    
    /* To make our dealer smarter as if the total sum of 2 cards is below 16 the chances of losing 
    is higher hence we get our dealer to draw to make it more difficult for the player to win. */
    public void drawDealerCards(Player d) {
        while (d.getSum() < 16 && d.getHand().size()<=5) {
            drawCard(d);
            recountPlayerSum(d);
        }
    }
    //The path to our hiddencard image
    public String getHiddenCardPath() {
        String imagePath = "images/cards/" + hiddenCard.toString() + ".gif";
        return imagePath;
    }
    //The conditons on who wins,loses or tie.
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
    //returns the deck
    public ArrayList<Card> getDeck() {
        return deck;
    }

}
