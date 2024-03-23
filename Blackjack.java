import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Blackjack {

    ArrayList<Card> deck = DeckBuilder.buildDeck();

    // dealer's hiddenCard
    private Card hiddenCard;

    // Instantiate player & dealer
    Player p = new Player();
    Player d = new Player();

    // window
    int boardWidth;
    int boardHeight;

    int cardWidth; // ratio should 1/1.4
    int cardHeight;
    // boolean firstRound = true;

    // UI Buttons
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton NewRoundButton = new JButton("New Round Button");
    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel;

    {   //Logic of start game, for later use 
    // JPanel startGamePanel = new JPanel(){
    // @Override
    // public void paintComponent(Graphics g){
    // super.paintComponent(g);
    // Graphics2D g2d = (Graphics2D) g;
    // Font fnt0 = new Font("Times New Roman", Font.BOLD, 50);
    // Font fnt1 = new Font("Times New Roman", Font.BOLD, 25);
    // g.setFont(fnt0);
    // g.setColor(Color.WHITE);
    // g.drawString("Blackjack", 230, 150);

    // g.setFont(fnt0);
    // g.setColor(Color.WHITE);

    // g.drawString("Start", 280, 510 );

    // Rectangle startButton = new Rectangle(260, 460, 150, 75);
    // g2d.draw(startButton);
    // }
    // };

     // if (firstRound) {
        // startGamePanel.setLayout(new BorderLayout());
        // startGamePanel.setBackground(new Color(53, 101, 77)); // Set the background
        // color to green
        // frame.add(startGamePanel);
        // }
     }

    public String determineWinner() {
        if (p.getSum() > 21) {
            return "BUST! Dealer Win!";
        } else if (d.getSum() > 21) {
            return "You Win!";
        }
        // both you and dealer <= 21
        else if (p.getSum() == d.getSum()) {
            return "Tie!";

        } else if (p.getSum() > d.getSum()) {
            return "You Win!";
        } else if (p.getSum() < d.getSum()) {
            return "Dealer Win!";
        }
        return "";
    }

    public void createMainPanel() {
        gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                try {

                    // draw dealer's hand
                    for (int i = 0; i < d.getHand().size(); i++) {
                        Card card = d.getHand().get(i);
                        Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                        g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5) * i, 185, cardWidth, cardHeight, null);
                    }

                    // draw player's hand
                    for (int i = 0; i < p.getHand().size(); i++) {
                        // System.out.println("Player draws a card");
                        // System.err.println("Player's Sum is " + p.getSum());
                        Card card = p.getHand().get(i);
                        System.out.println(card);
                        Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                        g.drawImage(cardImg, 20 + (cardWidth + 5) * i, 350, cardWidth, cardHeight, null);
                        System.out.println("Print Player Card: " + i);
                        System.out.println("Player's Current Score: " + p.getSum());

                    }
                    String playerHandCount = "You: " + p.getSum();
                    String dealerHandCount = "Dealer: " + d.getSum();

                    // draw hidden card
                    Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/b.gif")).getImage();

                    if (!stayButton.isEnabled()) {
                        hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
                    }
                    g.drawImage(hiddenCardImg, 20, 185, cardWidth, cardHeight, null);
                    if(!hitButton.isEnabled()){
                        d.setSum(recountPlayerSum(d));
                        p.setSum(recountPlayerSum(p));
                        System.out.println("Dealer sum = " + d.getSum());
                        System.out.println("Player sum = " + p.getSum());

                        String message = "";

                        message = determineWinner();

                        g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
                        g.setColor(Color.WHITE);
                        g.drawString(message, 310, 550);

                        g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
                        g.setColor(Color.WHITE);
                        g.drawString(dealerHandCount, 20, 155);
                    }
                    

                    g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
                    g.setColor(Color.WHITE);
                    g.drawString(playerHandCount, 20, 550);
                        
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void initializeButtons() {
        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);

        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);

        buttonPanel.add(NewRoundButton);
        NewRoundButton.setVisible(false);

        buttonPanel.setVisible(true);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        gamePanel.setVisible(true);
    }

    public void initializeFrame() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initializeGamePanel() {
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

    }

    public void createListeners() {
        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Card card = deck.remove(deck.size() - 1);
                p.setSum(p.getSum() + card.getValue());
                p.setAceCount(p.getAceCount() + (card.isAce() ? 1 : 0));
                p.getHand().add(card);
                if (recountPlayerSum(p) >= 21) { // A + 2 + J --> 1 + 2 + J
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
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
    }

    Blackjack() {
        boardWidth = 680;
        boardHeight = boardWidth;
    
        cardWidth = 100; // ratio should 1/1.4
        cardHeight = 140;
        startGame();
        createMainPanel();
        initializeFrame();
        initializeGamePanel();
        initializeButtons();
        createListeners();
    }

    public void restartGame() {

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
        deck = DeckBuilder.buildDeck();

        // Boolean to not redraw the start screen
        // firstRound = false;
        d.setFreshHand();
        p.setFreshHand();
        p.setSum(0);
        d.setSum(0);

        hiddenCard = deck.remove(deck.size() - 1); // remove card at last index
        d.setSum(d.getSum() + hiddenCard.getValue());
        d.setAceCount(d.getAceCount() + (hiddenCard.isAce() ? 1 : 0));

        Card card = deck.remove(deck.size() - 1);
        d.setSum(d.getSum() + card.getValue());
        d.setAceCount(d.getAceCount() + (card.isAce() ? 1 : 0));
        d.getHand().add(card);

        System.out.println("DEALER:");
        System.out.println(hiddenCard);
        System.out.println("Hand: " + d.getHand() + " Sum: " + d.getSum() + " Ace count: " + d.getAceCount());

        // p.setSum(0);
        // p.setAceCount(0);

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);
            p.setSum(p.getSum() + card.getValue());
            p.setAceCount(p.getAceCount() + (card.isAce() ? 1 : 0));
            p.getHand().add(card);
            System.out.println(card + " added to player's hand");
            System.out.println("Player's hand size is: " + p.getHand().size());
        }

        System.out.println("PLAYER: ");
        System.out.println("hand: " + p.getHand() + " Sum: " + p.getSum() + " Ace count:" + p.getAceCount());

    }

    // Functions to help the game

    public int recountPlayerSum(Player p) {
        while (p.getSum() > 21 && p.getAceCount() > 0) {
            p.setSum(p.getSum() - 10);
            p.setAceCount(p.getAceCount() - 1);
        }
        System.out.println("recountPlayerSum() method ran successfully");
        return p.getSum();
    }


    public void drawDealerCards() {
        while (d.getSum() < 17) {
            Card card = deck.remove(deck.size() - 1);
            d.setSum(d.getSum() + card.getValue());
            d.setAceCount(d.getAceCount() + (card.isAce() ? 1 : 0));
            recountPlayerSum(d);
            d.getHand().add(card);
        }
    }
}
