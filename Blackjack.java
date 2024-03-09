import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Blackjack {
    // Private class of cards inside the deck
    private class Card {
        String number;
        String suits;

        Card(String number, String suits) {
            this.number = number;
            this.suits = suits;
        }

        // Overriding toString, makes the deck print the card in the format of
        // number|suits
        public String toString() {
            return number + "" + suits;
        }

        // Method to get the value of current card
        public int getValue() {
            if ("atjqk".contains(number)) {
                if (number == "a") {
                    return 11;
                }
                return 10;
            }
            return Integer.parseInt(number);
        }

        // Method to know if the compared card is an ace
        public boolean isAce() {
            return number == "a";
        }
        //Method to get the current image's path
        public String getImagePath(){
            return "./cards/" + toString() + ".gif";
        }
    }

    // The deck

    ArrayList<Card> deck;
    Random random = new Random();

    // Dealer's hand
    Card hiddenCard;
    ArrayList<Card> dealerCards;
    int dealerHandSum;
    int dealerAceCount;


    //Player's hand
    ArrayList<Card> playerCards;
    int playerHandSum;
    int playerAceCount;


    //GUI
    int boardWidth = 600;
    int boardHeight = boardWidth;
    
    //Card size
    int cardWidth = 110;        //1:1.4 ratio
    int cardHeight = 154;

    
    JFrame frame = new JFrame("Black Jack"); 
    JPanel gamePanel = new JPanel(){
        //Overriding paintComponent to put the card assets into the JFrame
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            try{
            Image hiddenCardImage = new ImageIcon(getClass().getResource("./cards/b.gif")).getImage();
            if(!stayButton.isEnabled()){
                hiddenCardImage = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
            }
            g.drawImage(hiddenCardImage, 20, 20, cardWidth, cardHeight, null);

            //Dealer's other cards
            for(int i = 0; i < dealerCards.size(); i++){
                Card card = dealerCards.get(i);
                Image cardImage = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImage, cardWidth + 25 + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);

            }
            //Player's cards
            for(int i = 0; i < playerCards.size(); i++){
                Card card = playerCards.get(i);
                Image cardImage = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImage, 20 + (cardWidth + 5) * i, 320, cardWidth, cardHeight, null);

            }
            String message = "";
            if(!stayButton.isEnabled()){
                dealerHandSum = reduceDealerAce();
                playerHandSum = reducePlayerAce();
                if(playerHandSum > 21){
                    message = "You Lose!";
                }
                else if(dealerHandSum > 21){
                    message = "You Win!";
                }
                else if(playerHandSum == dealerHandSum){
                    message = "Tie!";
                }
                else if(playerHandSum > dealerHandSum){
                    message = "You Win!" ;
                }
                else if(playerHandSum < dealerHandSum){
                    message = "You Lose!" ;
                }
            }
            g.setFont(new Font("Helvetica", Font.PLAIN, 40));
            g.setColor(Color.WHITE);
            g.drawString(message, 220, 250);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    //The buttons in the JFrame
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");

    // Blackjack method to start the game
    Blackjack() {
        beginGame();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 105, 77));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e){
            Card card = deck.remove(deck.size() - 1);
            playerHandSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerCards.add(card);
            if(reducePlayerAce() > 21){
                hitButton.setEnabled(false);
            }
            gamePanel.repaint();
          }  
        });
        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while(dealerHandSum < 17){
                    Card card = deck.remove(deck.size() - 1);
                    dealerHandSum += card.getValue();
                    dealerAceCount += card.isAce() ? 1 : 0;
                    dealerCards.add(card);
                }
                gamePanel.repaint();
            }
        });
        gamePanel.repaint();
        }

    // Method that starts the game
    public void beginGame() {
        buildDeck();
        shuffleDeck();

        dealerCards = new ArrayList<>();
        dealerHandSum = 0;
        dealerAceCount = 0;
        // Removes card at the top of the deck, this is the hidden card of the dealer
        hiddenCard = deck.remove(deck.size() - 1);
        dealerHandSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        // The other cards of the dealer
        Card card = deck.remove(deck.size() - 1);
        dealerHandSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerCards.add(card);
        System.out.println(hiddenCard);
        System.out.println(card);
        System.out.println(dealerHandSum);
        System.out.println(dealerAceCount);

        playerCards = new ArrayList<>();
        playerHandSum = 0;
        playerAceCount = 0;

        card = deck.remove(deck.size() - 1);
        playerHandSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerCards.add(card);
        System.out.println(card);

        card = deck.remove(deck.size() - 1);
        playerHandSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerCards.add(card);
        System.out.println(card);
        System.out.println(playerHandSum);
        System.out.println(playerAceCount);
    }

    // Creates a deck of cards
    public void buildDeck() {
        deck = new ArrayList<>();
        String[] number = { "a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k" };
        String[] suits = { "d", "c", "h", "s" };

        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < number.length; j++) {
                Card card = new Card(number[j], suits[i]);
                deck.add(card);
            }
        }
    }

    // Shuffles deck using random, picks a number between 0 and 51
    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currentCard = deck.get(i);
            Card ranCard = deck.get(j);
            deck.set(i, ranCard);
            deck.set(j, currentCard);
        }

    }

    public int reducePlayerAce(){
        while(playerHandSum > 21 && playerAceCount > 0){
            playerHandSum -= 10;
            playerAceCount -= 1;
        }
        return playerHandSum;
    }
    public int reduceDealerAce(){
        while(dealerHandSum > 21 && dealerAceCount > 0){
            dealerHandSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerHandSum;
    }
}
