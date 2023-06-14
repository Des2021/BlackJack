import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HandTest {

    private Hand hand; // Objeto Hand para realizar las pruebas

    @Before
    public void setUp() {
        hand = new Hand(); // Se crea una nueva mano antes de cada prueba
    }

    @Test
    public void testAddCard() {
        Card card = new Card("As", "Tréboles"); // Se crea una nueva carta
        hand.addCard(card); // Se agrega la carta a la mano

        assertEquals(1, hand.getCards().size()); // Se verifica que la mano tenga 1 carta
        assertTrue(hand.getCards().contains(card)); // Se verifica que la carta esté presente en la mano
    }
}



