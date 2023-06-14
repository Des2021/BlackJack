import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlackjackGameTest {

    private Player player; // Objeto Player para realizar las pruebas

    @Before
    public void setUp() {
        player = new Player("Player", 1000); // Se crea un jugador con nombre "Player" y 1000 créditos antes de cada prueba
    }

    @Test
    public void testInitialCredits() {
        assertEquals(1000, player.getCredits()); // Se verifica que el valor inicial de los créditos del jugador sea 1000
    }
}







