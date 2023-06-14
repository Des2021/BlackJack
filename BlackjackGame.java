import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}

class Card {
    private final String rank;
    private final String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }
}

class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Corazones", "Diamantes", "Tr�boles", "Rombos"};
        String[] ranks = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        // Implement card shuffling algorithm here
    }

    public Card drawCard() {
        // Remove and return a card from the deck
        return cards.remove(0);
    }
}

class Hand {
    private final List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getHandValue() {
        int value = 0;
        int numAces = 0;

        for (Card card : cards) {
            String rank = card.getRank();
            if (rank.equals("As")) {
                value += 11;
                numAces++;
            } else if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack")) {
                value += 10;
            } else {
                try {
                    value += Integer.parseInt(rank);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }

    public void printHand() {
        for (Card card : cards) {
            System.out.println(card.getRank() + " of " + card.getSuit());
        }
    }
}

class Player {
    private final String name;
    private int credits;
    private Hand hand;

    public Player(String name, int credits) {
        this.name = name;
        this.credits = credits;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public void addCredits(int amount) {
        credits += amount;
    }

    public void subtractCredits(int amount) {
        credits -= amount;
    }

    public Hand getHand() {
        return hand;
    }

    public void clearHand() {
        hand = new Hand();
    }
}

public class BlackjackGame extends JFrame implements ActionListener {
    private final Player player;
    private final Deck deck;

    private JLabel nameLabel;
    private JLabel creditsLabel;
    private JLabel betLabel;
    private JLabel handValueLabel;
    private JButton hitButton;
    private JButton standButton;
    private JButton playWithCreditsButton;
    private JButton playWithoutCreditsButton;
    private JButton quitButton;
    private JButton backButton;

    private int currentBet;

    public BlackjackGame(String playerName) {
        player = new Player(playerName, 1000);
        deck = new Deck();
        currentBet = 0;

        setTitle("Juego de BlackJack");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1));

        nameLabel = new JLabel("Bienvenido, " + player.getName() + "!");
        creditsLabel = new JLabel("Cr�ditos: " + player.getCredits());
        betLabel = new JLabel("Haz tu apuesta: ");
        handValueLabel = new JLabel("Valor de tu mano: ");

        hitButton = new JButton("Pedir");
        hitButton.addActionListener(this);

        standButton = new JButton("Plantarse");
        standButton.addActionListener(this);

        playWithCreditsButton = new JButton("Jugar con cr�ditos");
        playWithCreditsButton.addActionListener(this);

        playWithoutCreditsButton = new JButton("Jugar sin cr�ditos");
        playWithoutCreditsButton.addActionListener(this);

        quitButton = new JButton("Salir");
        quitButton.addActionListener(this);

        backButton = new JButton("Volver al men�");
        backButton.addActionListener(this);

        add(nameLabel);
        add(creditsLabel);
        add(betLabel);
        add(playWithCreditsButton);
        add(playWithoutCreditsButton);
        add(quitButton);

        setVisible(true);
    }

    private void dealInitialCards() {
        player.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());
    }

    private int getPlayerBet() {
        String betString = JOptionPane.showInputDialog(this, "Cr�ditos actuales: " + player.getCredits());
        int bet = 0;

        try {
            bet = Integer.parseInt(betString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Apuesta inv�lida. Introduce un valor.");
            return getPlayerBet();
        }

        if (bet <= 0) {
            JOptionPane.showMessageDialog(this, "Apuesta inv�lida. Introduce una apuesta.");
            return getPlayerBet();
        } else if (bet > player.getCredits()) {
            JOptionPane.showMessageDialog(this, "Cr�ditos insuficientes. Introduce una apuesta menor.");
            return getPlayerBet();
        }

        return bet;
    }

    private void playPlayerTurn() {
        player.getHand().addCard(deck.drawCard());
        updateUI();
        int handValue = player.getHand().getHandValue();
        handValueLabel.setText("Valor de tu mano: " + handValue);

        if (handValue > 21) {
            JOptionPane.showMessageDialog(this, "�Mala suerte! Has perdido.");
            endRound(false);
        }
    }

    private void playDealerTurn() {
        Hand dealerHand = new Hand();
        dealerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        int dealerHandValue = dealerHand.getHandValue();

        while (dealerHandValue < 17) {
            dealerHand.addCard(deck.drawCard());
            dealerHandValue = dealerHand.getHandValue();
        }

        int playerHandValue = player.getHand().getHandValue();

        if (dealerHandValue > 21) {
            JOptionPane.showMessageDialog(this, "El dealer pierde, �t� ganas!");
            endRound(true);
        } else if (dealerHandValue > playerHandValue) {
            JOptionPane.showMessageDialog(this, "Gana el dealer.");
            endRound(false);
        } else if (dealerHandValue < playerHandValue) {
            JOptionPane.showMessageDialog(this, "�Has ganado!");
            endRound(true);
        } else {
            JOptionPane.showMessageDialog(this, "�Es un empate!");
            endRound(false);
        }
    }

    private void updateUI() {
        nameLabel.setText("Bienvenido, " + player.getName() + "!");
        creditsLabel.setText("Cr�ditos: " + player.getCredits());
    }

    private void endRound(boolean playerWins) {
        if (playerWins) {
            player.addCredits(currentBet);
        } else {
            player.subtractCredits(currentBet);
        }

        remove(handValueLabel);
        remove(hitButton);
        remove(standButton);

        add(betLabel);
        add(playWithCreditsButton);
        add(playWithoutCreditsButton);

        currentBet = 0;

        updateUI();

        validate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hitButton) {
            playPlayerTurn();
        } else if (e.getSource() == standButton) {
            playDealerTurn();
        } else if (e.getSource() == playWithCreditsButton) {
            if (player.getCredits() <= 0) {
                JOptionPane.showMessageDialog(this, "No tienes suficientes cr�ditos para jugar.");
                return;
            }

            currentBet = getPlayerBet();

            remove(betLabel);
            remove(playWithCreditsButton);
            remove(playWithoutCreditsButton);
            add(handValueLabel);
            add(hitButton);
            add(standButton);
            add(backButton);

            deck.shuffle();
            player.clearHand();
            dealInitialCards();

            updateUI();

            int handValue = player.getHand().getHandValue();
            handValueLabel.setText("Valor de tu mano: " + handValue);

            validate();
            repaint();
        } else if (e.getSource() == playWithoutCreditsButton) {
            remove(betLabel);
            remove(playWithCreditsButton);
            remove(playWithoutCreditsButton);
            add(handValueLabel);
            add(hitButton);
            add(standButton);
            add(backButton);

            deck.shuffle();
            player.clearHand();
            dealInitialCards();

            int handValue = player.getHand().getHandValue();
            handValueLabel.setText("Valor de tu mano: " + handValue);

            validate();
            repaint();
        } else if (e.getSource() == backButton) {
            remove(handValueLabel);
            remove(hitButton);
            remove(standButton);
            remove(backButton);

            add(betLabel);
            add(playWithCreditsButton);
            add(playWithoutCreditsButton);

            currentBet = 0;

            validate();
            repaint();
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        String playerName = JOptionPane.showInputDialog(null, "Introduce un nombre:");
        if (playerName == null || playerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Introduce un nombre v�lido.");
            return;
        }
        
        BlackjackGame game = new BlackjackGame(playerName);
        game.setVisible(true);
    }
}
















