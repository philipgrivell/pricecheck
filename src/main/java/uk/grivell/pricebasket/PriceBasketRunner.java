package uk.grivell.pricebasket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uk.grivell.pricebasket.persistence.Product;
import uk.grivell.pricebasket.persistence.ProductRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class PriceBasketRunner implements CommandLineRunner {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiscountCalculator discountCalculator;

    @Autowired
    private Audit audit;

    private Map<Product, Integer> basket = new HashMap<>();

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            return;
        }

        Basket basket = new Basket();
        for (String arg : args) {
            Product product = productRepository.findByName(arg);
            if (product == null) {
                throw new IllegalArgumentException("Cannot find product " + arg);
            }
            basket.addProduct(product);
        }
        basket.getItems().forEach(i -> System.out.println(i.getProduct().getName() + "->" + i.getQuantity()));
        basket.getItems().forEach(i -> discountCalculator.applyDiscount(basket, i));
        audit.addEntry(basket.getSummary());
        System.out.println(basket.getSummary());
    }
}
