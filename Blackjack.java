import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackJack {

    private ArrayList<Card> deck;
    private Random random = new Random(); // shuffle deck

    // dealer
    private Card hiddenCard;
    // ArrayList<Card> dealerHand;
    // int dealerSum;
    // int dealerAceCount;

    // player
    Player p = new Player();
    Player d = new Player(); // added
    // ArrayList<Card> playerHand;
    // int playerSum;
    // int playerAceCount;

    // window
    int boardWidth = 680;
    int boardHeight = boardWidth;

    int cardWidth = 110; // ratio should 1/1.4
    int cardHeight = 154;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                // draw hidden card
                Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/b.gif")).getImage();
                if (!stayButton.isEnabled()) {
                    hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
                }
                g.drawImage(hiddenCardImg, 20, 100, cardWidth, cardHeight, null);

                // draw dealer's hand
                for (int i = 0; i < d.getHand().size(); i++) {
                    Card card = d.getHand().get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5) * i, 100, cardWidth, cardHeight, null);
                }

                // draw player's hand
                for (int i = 0; i < p.getHand().size(); i++) {
                    // System.out.println("Player draws a card");
                    // System.err.println("Player's Sum is " + p.getSum());
                    Card card = p.getHand().get(i);
                    System.out.println(card);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 20 + (cardWidth + 5) * i, 275, cardWidth, cardHeight, null);
                    System.out.println("Print Player Card: " + i);
                    System.out.println("Player's Current Score: " + p.getSum());
                    
                }
                String playerHandCount = "You: " + p.getSum();
                String dealerHandCount = "Dealer: " + d.getSum();

                if (!stayButton.isEnabled()) {
                    // dealerSum = reduceDealerAce();
                    d.setSum(reduceDealerAce());
                    p.setSum(reducePlayerAce());
                    System.out.println("STAY: ");
                    System.out.println("Dealer sum = " + d.getSum());
                    System.out.println("Player sum = " + p.getSum());

                    String message = "";
                
                    if (p.getSum() > 21) {
                        message = "BUST!";
                    } else if (d.getSum() > 21) {
                        message = "You Win!";
                    }
                    // both you and dealer <= 21
                    else if (p.getSum() == d.getSum()) {
                        message = "Tie!";

                    } else if (p.getSum() > d.getSum()) {
                        message = "WIN!";
                    } else if (p.getSum() < d.getSum()) {
                        message = "Dealer Win!";
                    }

                    g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
                    g.setColor(Color.WHITE);
                    g.drawString(message, 275, 550);

                    g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
                    g.setColor(Color.WHITE);
                    g.drawString(dealerHandCount, 20, 75);

                }

                g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
                g.setColor(Color.WHITE);
                g.drawString(playerHandCount, 20, 475);
    

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton NewRoundButton = new JButton("New Round Button");

    BlackJack() {
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);


        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        buttonPanel.add(NewRoundButton);
        NewRoundButton.setVisible(false);
        buttonPanel.setVisible(true);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Card card = deck.remove(deck.size() - 1);
                p.setSum(p.getSum() + card.getValue());
                // playerSum += card.getValue();
                // playerAceCount += card.isAce() ? 1 : 0;
                p.setAceCount(p.getAceCount() + (card.isAce() ? 1 : 0));
                p.getHand().add(card);
                if (reducePlayerAce() >= 21) { // A + 2 + J --> 1 + 2 + J
                    hitButton.setVisible(false);
                    stayButton.setVisible(false);
                    hitButton.setEnabled(false);
                    stayButton.setEnabled(false);
                    NewRoundButton.setVisible(true);

                }
                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setVisible(false);
                stayButton.setVisible(false);
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                NewRoundButton.setVisible(true);
                drawDealerCards();
                gamePanel.repaint();
            }
        });

        NewRoundButton.addActionListener(new ActionListener() { 
            @Override 
            public void actionPerformed(ActionEvent e){ 
                restartGame(); 
            }   
});
    }


    public void restartGame(){ 
        startGame(); 
 
        NewRoundButton.setVisible(false); 

        hitButton.setVisible(true);
        stayButton.setVisible(true);
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        gamePanel.repaint(); 
    }

    public void startGame() {
        // deck
        buildDeck();
        shuffleDeck();

        d.setFreshDeck();
        d.setSum(0);
        d.setAceCount(0);

        p.setFreshDeck();
        p.setSum(0);
        p.setAceCount(0);

        hiddenCard = deck.remove(deck.size() - 1); // remove card at last index
        d.setSum(d.getSum() + hiddenCard.getValue());
        d.setAceCount(d.getAceCount() + (hiddenCard.isAce() ? 1 : 0));
        // dealerSum += hiddenCard.getValue();
        // dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size() - 1);
        d.setSum(d.getSum() + card.getValue());
        d.setAceCount(d.getAceCount() + (card.isAce() ? 1 : 0));
        d.getHand().add(card);
        // dealerSum += card.getValue();
        // dealerAceCount += card.isAce() ? 1 : 0;
        // dealerHand.add(card);

        System.out.println("DEALER:");
        System.out.println(hiddenCard);
        System.out.println(d.getHand());
        System.out.println(d.getSum());
        System.out.println(d.getAceCount());

        // player
        // Player p = new Player(new ArrayList<Card>());
        p.setSum(0);
        p.setAceCount(0);
        // playerHand = new ArrayList<Card>();
        // playerSum = 0;
        // playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);
            p.setSum(p.getSum() + card.getValue());
            // playerSum += card.getValue();
            p.setAceCount(p.getAceCount() + (card.isAce() ? 1 : 0));
            // playerAceCount += card.isAce() ? 1 : 0;
            p.getHand().add(card);
            System.out.println(card + " added to player's hand");
            System.out.println("Player's hand size is: " + p.getHand().size());
        }

        System.out.println("PLAYER: ");
        System.out.println(p.getHand());
        System.out.println(p.getSum());
        System.out.println(p.getAceCount());
    }

    public void buildDeck() {
        deck = new ArrayList<Card>();
        String[] values = { "a", "2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k" };
        String[] types = { "c", "d", "h", "s" };

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }

        System.out.println("BUILD DECK:");
        System.out.println(deck);
    }

    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }

        System.out.println("AFTER SHUFFLE");
        System.out.println(deck);
    }

    public int reducePlayerAce() {
        while (p.getSum() > 21 && p.getAceCount() > 0) {
            p.setSum(p.getSum() - 10);
            p.setAceCount(p.getAceCount() - 1);
        }
        System.out.println("ReducePlayerAce() method ran successfully");
        return p.getSum();
    }

    public int reduceDealerAce() {
        while (d.getSum() > 21 && d.getAceCount() > 0) {
            d.setSum(d.getSum() - 10);
            d.setAceCount(d.getAceCount() - 1);
            // dealerSum -= 10;
            // dealerAceCount -= 1;
        }
        return d.getSum();
    }
    public void drawDealerCards(){
    while (d.getSum() < 17) {
        Card card = deck.remove(deck.size() - 1);
        d.setSum(d.getSum() + card.getValue());
        // dealerSum += card.getValue();
        // dealerAceCount += card.isAce() ? 1 : 0;
        d.setAceCount(d.getAceCount() + (card.isAce() ? 1 : 0));
        reduceDealerAce();
        d.getHand().add(card);
    }
}
}
