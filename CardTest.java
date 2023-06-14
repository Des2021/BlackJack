import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CardTest {

    private Card card; // Objeto Card para realizar las pruebas

    @Before
    public void setUp() {
        card = new Card("As", "Spades"); // Se crea una nueva carta antes de cada prueba
    }

    @Test
    public void testGetRank() {
        assertEquals("As", card.getRank()); // Se verifica que el valor devuelto por getRank() sea "Ace"
    }

    @Test
    public void testGetSuit() {
        assertEquals("Picas", card.getSuit()); // Se verifica que el valor devuelto por getSuit() sea "Spades"
    }
}


