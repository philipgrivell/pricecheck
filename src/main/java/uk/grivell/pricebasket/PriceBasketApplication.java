package uk.grivell.pricebasket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.grivell.pricebasket.persistence.Product;

@SpringBootApplication
public class PriceBasketApplication {
	public static void main(String[] args) {
		SpringApplication.run(PriceBasketApplication.class, args);
	}
}
