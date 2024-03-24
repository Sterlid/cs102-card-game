package utility;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import entities.Card;
import entities.Player;

public class Blackjack {

    ArrayList<Card> deck = DeckBuilder.buildDeck();

    // dealer's hiddenCard
    private Card hiddenCard;

    // Instantiate player & dealer
    Player p = new Player();
    Player d = new Player();

    boolean beforeBet;
    // window
    int boardWidth;
    int boardHeight;

    int cardWidth; // ratio should 1/1.4
    int cardHeight;
    // boolean firstRound = true;

    // Betting indicator
    int betAmount;
    String result;

    // Hand sum
    // String playerHandSum;
    // String dealerHandSum;
    String playerMoney;
    // UI Buttons
    JPanel buttonPanel = new JPanel();
    JTextField betInput = new JTextField(10);
    JButton confirmButton = new JButton("Confirm");
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton newRoundButton = new JButton("New Round Button");
    JButton exitButton = new JButton("Exit Game");
    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel;

    { // Logic of start game, for later use
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

    public void createMainPanel() {
        gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                try {
                    // playerHandSum = "You: " + p.getSum();
                    // dealerHandSum = "Dealer: " + d.getSum();
                    result = "Enter a bet: ";
                    playerMoney = "Money remaining: " + p.getMoney();
                    if (!beforeBet) {
                        // draw dealer's hand
                        for (int i = 0; i < d.getHand().size(); i++) {
                            Card card = d.getHand().get(i);
                            String imagePath = card.getImagePath();

                            Image cardImage = new ImageIcon(imagePath).getImage();
                            g.drawImage(cardImage, cardWidth + 25 + (cardWidth + 5) * i, 185, cardWidth, cardHeight,
                                    null);
                            result = "Current bet: " + betAmount;
                        }

                        // draw player's hand
                        for (int i = 0; i < p.getHand().size(); i++) {
                            // System.out.println("Player draws a card");
                            // System.err.println("Player's Sum is " + p.getSum());
                            Card card = p.getHand().get(i);
                            String imagePath = card.getImagePath();

                            Image cardImage = new ImageIcon(imagePath).getImage();

                            g.drawImage(cardImage, 20 + (cardWidth + 5) * i, 350, cardWidth, cardHeight, null);
                            System.out.println("Print Player Card: " + i);
                            System.out.println("Player's Current Score: " + p.getSum());

                        }

                        // draw hidden card
                        String hiddenImagePath = "images/cards/back.gif";
                        Image hiddenCardImg = new ImageIcon(hiddenImagePath).getImage();

                        if (!stayButton.isEnabled()) {
                            hiddenImagePath = hiddenCard.getImagePath();
                            hiddenCardImg = new ImageIcon(hiddenImagePath).getImage();
                        }

                        g.drawImage(hiddenCardImg, 20, 185, cardWidth, cardHeight, null);
                        if (!hitButton.isEnabled() && !beforeBet) {
                            d.setSum(recountPlayerSum(d));
                            p.setSum(recountPlayerSum(p));
                            System.out.println("Dealer sum = " + d.getSum());
                            System.out.println("Player sum = " + p.getSum());

                            result = determineWinner();
                            playerMoney = "Money remaining: " + p.getMoney();
                            g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
                            g.setColor(Color.WHITE);
                            g.drawString(Integer.toString(d.getSum()), 20, 155);
                        }
                        g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
                        g.setColor(Color.WHITE);
                        g.drawString(Integer.toString(p.getSum()), 20, 550);
                    }
                    g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
                    g.setColor(Color.WHITE);
                    g.drawString(result, 310, 550);

                    g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
                    g.setColor(Color.WHITE);
                    g.drawString(playerMoney, 310, 570);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public String determineWinner() {
        if (d.getSum() < 16){
            drawDealerCards();
            System.out.println("Dealer's Hand: " + hiddenCard + " " + d.getHand()  + "\tDealer's Sum: " + d.getSum());
        }
        if (p.getSum() > 21 && d.getSum() > 21){
            p.setMoney(p.getMoney() + betAmount);
            return "Tie! Both player and dealer bust!";
        }

        else if (p.getSum() > 21) {
            return "BUST! Dealer Win!";
        } else if (d.getSum() > 21) {
            p.setMoney(p.getMoney() + betAmount * 2);
            
            
            return "You Win!";
        }
        // both you and dealer <= 21
        else if (p.getSum() == d.getSum()) {
            p.setMoney(p.getMoney() + betAmount);
            return "Tie!";

        } else if (p.getSum() > d.getSum()) {
            p.setMoney(p.getMoney() + betAmount * 2);
            return "You Win!";
        } else if (p.getSum() < d.getSum()) {
            return "Dealer Win!";
        }
        return "";
    }

    public void preGameBet() {
        beforeBet = true;
        buttonPanel.add(betInput);
        buttonPanel.add(confirmButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setVisible(true);
    }

    public void initializeButtons() {
        buttonPanel.add(hitButton);

        buttonPanel.add(stayButton);

        buttonPanel.add(newRoundButton);
        newRoundButton.setVisible(false);

        buttonPanel.add(exitButton);
        exitButton.setVisible(false);

        hitButton.setEnabled(false);
        stayButton.setEnabled(false);

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

    public void showNewButtons() {
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        newRoundButton.setVisible(true);
        exitButton.setVisible(true);
    }

    public void createListeners() {

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    betAmount = Integer.parseInt(betInput.getText());
                    if (betAmount < 0 || betAmount > p.getMoney()) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                        return;
                    }

                    p.setMoney(p.getMoney() - betAmount);
                    confirmButton.setEnabled(false);
                    hitButton.setEnabled(true);
                    stayButton.setEnabled(true);
                    beforeBet = false;
                    gamePanel.repaint();
                } catch (NumberFormatException a) {
                    JOptionPane.showMessageDialog(frame, "Please enter a number.");
                }
            }
        });

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Card card = deck.remove(deck.size() - 1);
                p.setSum(p.getSum() + card.getValue());
                p.setAceCount(p.getAceCount() + (card.isAce() ? 1 : 0));
                p.getHand().add(card);
                if (recountPlayerSum(p) >= 21 || p.getHand().size() == 5) { // A + 2 + J --> 1 + 2 + J
                    showNewButtons();
                }
                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showNewButtons();
                drawDealerCards();
                gamePanel.repaint();
            }
        });

        newRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void restartGame() {
        beforeBet = true;
        confirmButton.setEnabled(true);
        startGame();

        newRoundButton.setVisible(false);
        confirmButton.setEnabled(true);
        hitButton.setVisible(true);
        stayButton.setVisible(true);
        buttonPanel.add(exitButton);
        exitButton.setVisible(false);
        gamePanel.repaint();
    }

    public void startGame() {
        // deck
        deck = DeckBuilder.buildDeck();

        // Boolean to not redraw the start screen
        // firstRound = false;
        d.setFreshHand();
        d.setAceCount(0);
        d.setSum(0);
        p.setFreshHand();
        p.setAceCount(0);
        p.setSum(0);

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
            if (p.getAceCount() > 2 && p.getHand().size() == 2) {
                p.setSum(12);
            }
            p.getHand().add(card);
            System.out.println(card + " added to player's hand");
            System.out.println("Player's hand size is: " + p.getHand().size());
        }

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

    public void drawDealerCards() {
        while (d.getSum() < 17) {
            Card card = deck.remove(deck.size() - 1);
            d.setSum(d.getSum() + card.getValue());
            d.setAceCount(d.getAceCount() + (card.isAce() ? 1 : 0));
            recountPlayerSum(d);
            d.getHand().add(card);
        }
    }

    public Blackjack() {
        boardWidth = 680;
        boardHeight = boardWidth;

        cardWidth = 100; // ratio should 1/1.4
        cardHeight = 140;

        preGameBet();
        startGame();
        createMainPanel();
        initializeFrame();
        initializeGamePanel();
        initializeButtons();
        createListeners();
    }

}
