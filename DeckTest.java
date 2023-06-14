import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DeckTest {

    private Deck deck; // Objeto Deck para realizar las pruebas

    @Before
    public void setUp() {
        deck = new Deck(); // Se crea un mazo de cartas antes de cada prueba
    }

    @Test
    public void testDeckSize() {
        assertEquals(52, deck.getCards().size()); // Se verifica que el tamaño del mazo sea de 52 cartas
    }
}



