package utility;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import entities.Card;
import entities.Player;
import gameplay.*;

public class UI extends Game{

    private ArrayList<Card> deck;

    // dealer's hiddenCard

    // Instantiate player & dealer
    private Player player;
    private Player dealer;
    
    private boolean beforeBet;
    // window
    private int boardWidth;
    private int boardHeight;

    private int cardWidth; // ratio should 1/1.4
    private int cardHeight;
    // boolean firstRound = true;

    // Betting indicator
    int betAmount;
    private String result;

    // Strings that keep track of player's hand and balance
    String playerHandSum;
    String dealerHandSum;
    String playerMoney;
    // UI Buttons
    private JPanel buttonPanel;
    private JTextField betInput;
    private JButton confirmButton;
    private JButton hitButton;
    private JButton stayButton;
    private JButton newRoundButton;
    private JButton exitButton;
    private JFrame frame;
    private JPanel gamePanel;

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

    //Creation of the game panel
    public void createMainPanel() {
        gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                try {
                    playerHandSum = "You: " + player.getSum();
                    dealerHandSum = "Dealer: " + dealer.getSum();
                    result = "Enter a bet: ";
                    playerMoney = "Balance: $" + player.getMoney();

                    //Runs when the bet is placed
                    if (!beforeBet) {
                        // draw dealer's hand
                        // from 1 as dealer's first card is Hidden
                        for (int i = 1; i < dealer.getHand().size(); i++) {
                            Card card = dealer.getHand().get(i);
                            String imagePath = card.getImagePath();

                            Image cardImage = new ImageIcon(imagePath).getImage();
                            g.drawImage(cardImage, cardWidth + 25 + (cardWidth + 5) * (i - 1), 185, cardWidth, cardHeight, null);
                            result = "Current bet: $" + betAmount;
                        }

                        // draw player's hand
                        drawPlayerCards(g, player);

                        // draw hidden card
                        String hiddenImagePath = "images/cards/back.gif";
                        Image hiddenCardImg = new ImageIcon(hiddenImagePath).getImage();

                        if (!stayButton.isEnabled()) {
                            hiddenImagePath = getHiddenCardPath();
                            System.out.println("Hidden image path: " + hiddenImagePath);
                            hiddenCardImg = new ImageIcon(hiddenImagePath).getImage();
                        }

                        g.drawImage(hiddenCardImg, 20, 185, cardWidth, cardHeight, null);
                        if (!hitButton.isEnabled() && !beforeBet) {
                            dealer.setSum(recountPlayerSum(dealer));
                            player.setSum(recountPlayerSum(player));
                            System.out.println("Dealer sum = " + dealer.getSum());
                            System.out.println("Player sum = " + player.getSum());

                            result = determineWinner(player, dealer, betAmount);
                            playerMoney = "Balance: $" + player.getMoney();
                            
                        writeText(g, 30, 20, 155, dealerHandSum);
                    }
                    writeText(g, 30, 20, 550, playerHandSum);
                    
                }
                    writeText(g, 30, 310, 550, result);

                    writeText(g, 20, 310, 580, playerMoney);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
    public void drawPlayerCards(Graphics g, Player player){
        for (int i = 0; i < player.getHand().size(); i++) {
            // System.out.println("Player draws a card");
            // System.err.println("Player's Sum is " + player.getSum());
            Card card = player.getHand().get(i);
            String imagePath = card.getImagePath();

            Image cardImage = new ImageIcon(imagePath).getImage();
            
            g.drawImage(cardImage, 20 + (cardWidth + 5) * i, 350, cardWidth, cardHeight, null);
            System.out.println("Print Player Card: " + i);
            System.out.println("Player's Current Score: " + player.getSum());
            System.out.println("player's ace count: " + player.getAceCount());
        }
    }
        //Text is in Times New Roman by default as a standard
    public void writeText(Graphics g, int fontSize, int x, int y, String text){
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
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

    public void createActionListeners() {

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    betAmount = Integer.parseInt(betInput.getText());
                    if (betAmount < 0 || betAmount > player.getMoney()) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                        return;
                    }

                    player.setMoney(player.getMoney() - betAmount);
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
                player.setSum(player.getSum() + card.getValue());
                player.setAceCount(player.getAceCount() + (card.isAce() ? 1 : 0));
                player.getHand().add(card);
                if (player.getSum() > 21) { // A + 2 + J --> 1 + 2 + J
                    if(recountPlayerSum(player) > 21){
                    drawDealerCards(dealer, deck);
                    showNewButtons();
                    }
                }
                if(player.getHand().size() == 5 && player.getSum() <= 21){
                        showNewButtons();
    
                }
                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showNewButtons();
                drawDealerCards(dealer, deck);
                gamePanel.repaint();
                
            }
        });

        newRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Deck at the end of game= \n" + deck);
                System.out.println("Deck size= " + deck.size());
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
        startGame(player, dealer,deck);

        newRoundButton.setVisible(false);
        confirmButton.setEnabled(true);
        hitButton.setVisible(true);
        stayButton.setVisible(true);
        buttonPanel.add(exitButton);
        exitButton.setVisible(false);
        gamePanel.repaint();

        if(player.getMoney() == 0){
            int option = JOptionPane.showConfirmDialog(frame, "You have no more money! Do you want to restart?", "No more money!", JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                player.setMoney(5000);
                restartGame();
            } else {
                System.exit(0);
            }
        }
    
    }




    public UI() {
        boardWidth = 680;
        boardHeight = boardWidth;
        
        cardWidth = 100; // ratio should 1/1.4
        cardHeight = 140;

        buttonPanel = new JPanel();
        betInput = new JTextField(10);
        confirmButton = new JButton("Confirm");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        newRoundButton = new JButton("Try Again");
        exitButton = new JButton("Exit Game");
        frame = new JFrame("Black Jack");

        player = new Player();
        player.setName("Player");
        dealer = new Player();
        player.setName("Dealer");

        deck = DeckBuilder.buildDeck();

    }

    public void setup(){
        preGameBet();
        startGame(player, dealer, deck);
        createMainPanel();
        initializeFrame();
        initializeGamePanel();
        initializeButtons();
        createActionListeners();
    }

}
