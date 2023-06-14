import static org.junit.Assert.*;
import org.junit.Test;

public class CardTest {
    @Test
    public void testGetRank() {
        Card card = new Card("Ace", "Hearts");
        assertEquals("Ace", card.getRank());
    }

    @Test
    public void testGetSuit() {
        Card card = new Card("Ace", "Hearts");
        assertEquals("Hearts", card.getSuit());
    }
}


