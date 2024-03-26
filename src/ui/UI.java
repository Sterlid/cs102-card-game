package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import entities.Card;
import entities.Player;
import gameplay.*;

public class UI extends Game{


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
    private int betAmount;
    private String result;
    private int playerHandY;
    private int dealerHandY;

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
    private JButton tryAgainButton;
    private JButton exitButton;
    private JFrame frame;
    private JPanel gamePanel;


    //This method draws the player cards into the game panel.
    //It takes each card inside of the player's hand, finds its image path using the Player class' getImagePath function, and draws the image onto the screen.
    //The x value for the drawImage is set the way it is so that each card will be drawn apart from each other by the order of the card. 
    //The first card will be drawn 20 pixels away from the edge of the panel, and the next one will be drawn 5 pixels + the card width away from the previous..
    public void paintPlayerCards(Graphics g, Player player){
        int i = 0;
        for (Card card : player.getHand()) {
            // System.out.println("Player draws a card");
            // System.err.println("Player's Sum is " + player.getSum());
            String imagePath = card.getImagePath();
            Image cardImage = new ImageIcon(imagePath).getImage();
            g.drawImage(cardImage, 20 + (cardWidth + 5) * i, playerHandY, cardWidth, cardHeight, null);
            System.out.println("Print Player Card: " + i);
            System.out.println("Player's Current Score: " + player.getSum());
            System.out.println("player's ace count: " + player.getAceCount());
            i++;
        }
    }

    //This paintDealerCards method works quite similar to paintPlayer cards, but with slight differences.
    //paintDealerCards will draw all the other cards the dealer has that is not hidden.
    public void paintDealerCards(Graphics g, Player dealer){
        for (int i = 1; i < dealer.getHand().size() ; i++) {
            Card card = dealer.getHand().get(i);
            String imagePath = card.getImagePath();
            Image cardImage = new ImageIcon(imagePath).getImage();
            g.drawImage(cardImage, cardWidth + 25 + (cardWidth + 5) * (i - 1), dealerHandY, cardWidth,
                    cardHeight, null);
        }
    }


    //Creation of the game panel. The order of commands executed are as follow:
    /*
     * 1. call parent constructor of Graphics
     */
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
                        result = "Current bet: $" + betAmount;

                        // draw dealer's hand
                        paintDealerCards(g, dealer);
                        
                        // draw player's hand
                        paintPlayerCards(g, player);

                        // draw hidden card
                        String hiddenImagePath = "images/cards/back.gif";
                        Image hiddenCardImg = new ImageIcon(hiddenImagePath).getImage();

                        if (!stayButton.isEnabled()) {
                            hiddenImagePath = getHiddenCardPath();
                            System.out.println("Hidden image path: " + hiddenImagePath);
                            hiddenCardImg = new ImageIcon(hiddenImagePath).getImage();
                        }

                        g.drawImage(hiddenCardImg, 20, dealerHandY, cardWidth, cardHeight, null);
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
                    
                    writeText(g, 20, 310, 550, result);

                    writeText(g, 20, 310, 580, playerMoney);
                }
                else{
                writeText(g, 70, 165, 250, "BlackJack ♠");

                writeText(g, 20, 200, 310, result);
                
                writeText(g, 20, 200, 350, playerMoney);
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

        //This method will help draw text into the panel quicker as we need to call multiple functions to draw text into the panel
        //Text is in Times New Roman by default as a standard
    public void writeText(Graphics g, int fontSize, int x, int y, String text){
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }


    //This method is to show the bet Input and confirm Button before betting
    public void preGameBet() {
        beforeBet = true;
        buttonPanel.add(betInput);
        buttonPanel.add(confirmButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setVisible(true);
    }

    public void addButtons() {
        buttonPanel.add(hitButton);
        hitButton.setVisible(false);

        buttonPanel.add(stayButton);
        stayButton.setVisible(false);

        buttonPanel.add(tryAgainButton);
        tryAgainButton.setVisible(false);

        buttonPanel.add(exitButton);
        exitButton.setVisible(false);

        hitButton.setEnabled(false);
        stayButton.setEnabled(false);

        gamePanel.setVisible(true);
    }

    public void setUpFrame() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setUpGamePanel() {
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

    }

    public void showNewButtons() {
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        tryAgainButton.setVisible(true);
        exitButton.setVisible(true);
    }

    public void afterBet(boolean beforeBet){
        confirmButton.setVisible(beforeBet);
        betInput.setVisible(beforeBet);
        hitButton.setVisible(!beforeBet);
        stayButton.setVisible(!beforeBet);
        hitButton.setEnabled(!beforeBet);
        stayButton.setEnabled(!beforeBet);
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
                    beforeBet = false;
                    player.setMoney(player.getMoney() - betAmount);
                    afterBet(beforeBet);
                    gamePanel.repaint();
                } catch (NumberFormatException a) {
                    JOptionPane.showMessageDialog(frame, "Please enter a number.");
                }
            }
        });

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawCard(player);
                if (player.getSum() > 21) { // A + 2 + J --> 1 + 2 + J
                    if(recountPlayerSum(player) > 21){
                    drawDealerCards(dealer);
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
                drawDealerCards(dealer);
                gamePanel.repaint();
            }
        });

        tryAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getMoney() == 0){
                noMoneyPopup();
                }
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

    public void noMoneyPopup(){
        int option = JOptionPane.showConfirmDialog(frame, "You have no more money! Do you want to restart?", "No more money!", JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                player.setMoney(5000);
                restartGame();
            } else {
                System.exit(0);
            }
    }

    public void resetAllButtons(){
        confirmButton.setVisible(beforeBet);
        betInput.setVisible(beforeBet);
        tryAgainButton.setVisible(false);
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        buttonPanel.add(exitButton);
        exitButton.setVisible(false);
        gamePanel.repaint();

    }

    public void restartGame() {
        beforeBet = true;
        resetAllButtons();
        startGame(player, dealer);

    }



    public UI() {
        boardWidth = 680;
        boardHeight = boardWidth;
        
        cardWidth = 100; // ratio should 1/1.4
        cardHeight = 140;

        playerHandY = 350;
        dealerHandY = 185;
        buttonPanel = new JPanel();
        betInput = new JTextField(10);
        confirmButton = new JButton("Confirm");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        tryAgainButton = new JButton("Try Again");
        exitButton = new JButton("Exit Game");
        frame = new JFrame("Black Jack");

        player = new Player();
        dealer = new Player();

    }

      public void setup(){
        preGameBet();
        startGame(player, dealer);
        createMainPanel();
        setUpFrame();
        setUpGamePanel();
        addButtons();
        createActionListeners();
    }

}
