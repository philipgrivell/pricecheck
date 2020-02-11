package uk.grivell.pricebasket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PriceBasketApplicationTest {

    @Autowired
    private PriceBasketRunner priceBasketRunner;

    @Autowired
    private Audit audit;

    @Test
    public void testRun() {
        priceBasketRunner.run("Soup", "Soup", "Bread");
        assertEquals(audit.getEntries().size(), 1);
        assertEquals(audit.getEntries().get(0), "Subtotal: £2.10\n" +
                "Discount: £0.40\n" +
                "Total: £1.70\n" +
                "Offers Applied:\n" +
                "Buy 2 soups and get bread half price");
    }

    @Test
    public void testInvalidProduct() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            priceBasketRunner.run("Soop", "Soup", "Bread");
        });
    }
}
