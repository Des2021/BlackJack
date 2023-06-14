import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

// Clase personalizada de excepción para un nombre no válido
class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}

// Clase para representar una carta
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

// Clase para representar una baraja de cartas
class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Corazones", "Diamantes", "Tréboles", "Picas"};
        String[] ranks = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        // Implementa aquí el algoritmo de mezcla de cartas
    }

    public Card drawCard() {
        // Elimina y devuelve una carta del mazo
        return cards.remove(0);
    }

    public List<Card> getCards() {
        return cards;
    }
}

// Clase para representar una mano de cartas
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

    public List<Card> getCards() {
        return cards;
    }
}

// Clase para representar un jugador
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

// Clase principal que extiende JFrame e implementa ActionListener para la interfaz gráfica del juego
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
        creditsLabel = new JLabel("Créditos: " + player.getCredits());
        betLabel = new JLabel("");
        handValueLabel = new JLabel("Valor de tu mano: ");

        hitButton = new JButton("Pedir");
        hitButton.addActionListener(this);

        standButton = new JButton("Plantarse");
        standButton.addActionListener(this);

        playWithCreditsButton = new JButton("Jugar con créditos");
        playWithCreditsButton.addActionListener(this);

        playWithoutCreditsButton = new JButton("Jugar sin créditos");
        playWithoutCreditsButton.addActionListener(this);

        quitButton = new JButton("Salir");
        quitButton.addActionListener(this);

        backButton = new JButton("Volver al menú");
        backButton.addActionListener(this);

        add(nameLabel);
        add(creditsLabel);
        add(betLabel);
        add(playWithCreditsButton);
        add(playWithoutCreditsButton);
        add(quitButton);

        setVisible(true);
    }

    // Reparte las cartas iniciales al jugador
    void dealInitialCards() {
        player.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());
    }

    // Obtiene la apuesta del jugador a través de una ventana de diálogo
    private int getPlayerBet() {
        String betString = JOptionPane.showInputDialog(this, "Créditos actuales: " + player.getCredits());
        int bet = 0;

        try {
            bet = Integer.parseInt(betString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Apuesta inválida. Introduce un valor.");
            return getPlayerBet();
        }

        if (bet <= 0) {
            JOptionPane.showMessageDialog(this, "Apuesta inválida. Introduce una apuesta.");
            return getPlayerBet();
        } else if (bet > player.getCredits()) {
            JOptionPane.showMessageDialog(this, "Créditos insuficientes. Introduce una apuesta menor.");
            return getPlayerBet();
        }

        return bet;
    }

    // Realiza el turno del jugador
    private void playPlayerTurn() {
        player.getHand().addCard(deck.drawCard());
        updateUI();
        int handValue = player.getHand().getHandValue();
        handValueLabel.setText("Valor de tu mano: " + handValue);

        if (handValue > 21) {
            JOptionPane.showMessageDialog(this, "¡Mala suerte! Has perdido.");
            endRound(false);
        }
    }

    // Realiza el turno del crupier
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
            JOptionPane.showMessageDialog(this, "El dealer pierde, ¡tú ganas!");
            endRound(true);
        } else if (dealerHandValue > playerHandValue) {
            JOptionPane.showMessageDialog(this, "Gana el dealer.");
            endRound(false);
        } else if (dealerHandValue < playerHandValue) {
            JOptionPane.showMessageDialog(this, "¡Has ganado!");
            endRound(true);
        } else {
            JOptionPane.showMessageDialog(this, "¡Es un empate!");
            endRound(false);
        }
    }

    // Actualiza la interfaz gráfica
    private void updateUI() {
        nameLabel.setText("Bienvenido, " + player.getName() + "!");
        creditsLabel.setText("Créditos: " + player.getCredits());
    }

    // Finaliza la ronda, ajusta los créditos del jugador y actualiza la interfaz gráfica
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

    // Acciones a realizar cuando se produce un evento de acción
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hitButton) {
            playPlayerTurn();
        } else if (e.getSource() == standButton) {
            playDealerTurn();
        } else if (e.getSource() == playWithCreditsButton) {
            if (player.getCredits() <= 0) {
                JOptionPane.showMessageDialog(this, "No tienes suficientes créditos para jugar.");
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

    // Método principal para iniciar el juego
    public static void main(String[] args) {
        String playerName = JOptionPane.showInputDialog(null, "Introduce tu nombre:");
        if (playerName == null || playerName.trim().isEmpty()) {
            try {
                throw new InvalidNameException("Nombre inválido");
            } catch (InvalidNameException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                System.exit(0);
            }
        }

        new BlackjackGame(playerName);
    }
}
















