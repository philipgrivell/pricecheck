package uk.grivell.pricebasket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import uk.grivell.pricebasket.persistence.Offer;
import uk.grivell.pricebasket.persistence.OfferRepository;
import uk.grivell.pricebasket.persistence.Product;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountCalculatorTest {
    @Mock
    private OfferRepository offers;

    @InjectMocks
    private DiscountCalculator discountCalculator = new DiscountCalculator();

    @Test
    public void testDiscount() {
        when(offers.findByProductName(eq("Apple"))).thenReturn(
                new Offer("Apple", "Apples 20% off", new BigDecimal("0.2"), null));
        when(offers.findByProductName(eq("Pear"))).thenReturn(
                new Offer("Pear", "Get half price when you buy 2 oranges", new BigDecimal("0.5"), "getQuantity(\"Orange\") >= 2"));
        when(offers.findByProductName(eq("Orange"))).thenReturn(null);

        BasketItem apples = new BasketItem(new Product("Apple", new BigDecimal("1.00")), 3);
        BasketItem pears = new BasketItem(new Product("Pear", new BigDecimal("1.50")), 5);
        BasketItem oranges = new BasketItem(new Product("Orange", new BigDecimal("1.50")), 5);
        Basket basket = new BasketBuilder().withItems(apples, pears, oranges).build();

        BiConsumer<BasketItem, BigDecimal> test = (item, expected) -> {
            discountCalculator.applyDiscount(basket, item);
            assertEquals(expected, item.getDiscount(), item.getProduct().getName());
        };

        test.accept(apples, new BigDecimal("0.60"));
        test.accept(pears, new BigDecimal("3.75"));
        test.accept(oranges, new BigDecimal("0"));
    }

    @Test
    public void testRule() {
        Basket basket = new BasketBuilder().withItems(
                new BasketItem(new Product("Apple", new BigDecimal("1.00")), 3),
                new BasketItem(new Product("Pear", new BigDecimal("1.50")), 5),
                new BasketItem(new Product("Orange", new BigDecimal("1.50")), 5))
                .build();
        BiConsumer<String, Boolean> test = (rule, expected) -> {
            DiscountCalculator discountCalculator = new DiscountCalculator();
            boolean result = discountCalculator.matchesRule(basket, rule);
            assertEquals(expected, result, rule);
        };

        test.accept(null, true);
        test.accept("getQuantity(\"Apple\") >= 2", true);
        test.accept("getQuantity(\"Strawberries\") >= 2", false);
    }


    private Basket getBasket() {
        return new BasketBuilder().withItems(
                new BasketItem(new Product("Apple", new BigDecimal("1.00")), 3),
                new BasketItem(new Product("Pear", new BigDecimal("1.50")), 5),
                new BasketItem(new Product("Orange", new BigDecimal("1.50")), 5))
                .build();
    }
}
