package uk.grivell.pricebasket;

import uk.grivell.pricebasket.persistence.Product;

import java.math.BigDecimal;
import java.util.Optional;

public class BasketItem {
    private Product product;
    private Integer quantity;
    private BigDecimal discount;
    private String offer;

    public BasketItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void addQuantity() {
        this.quantity++;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getDiscount() {
        // Calculate total discount based on number of items.
        return Optional.ofNullable(discount)
                .map(d -> getTotalPrice().multiply(d).setScale(2, BigDecimal.ROUND_HALF_DOWN))
                .orElse(new BigDecimal(0));
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
