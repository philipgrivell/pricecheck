package uk.grivell.pricebasket;

import uk.grivell.pricebasket.persistence.Product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class Basket {
    private LinkedHashMap<String, BasketItem> items = new LinkedHashMap<>();

    // Test method
    void addItem(BasketItem item) {
        items.put(item.getProduct().getName(), item);
    }

    public void addProduct(Product product) {
        BasketItem item = items.get(product.getName());
        if (item == null) {
            items.put(product.getName(), new BasketItem(product, 1));
        } else {
            item.addQuantity();
        }
    }

    public int getQuantity(String name) {
        return Optional.ofNullable(items.get(name)).map(i -> i.getQuantity()).orElse(0);
    }

    public Collection<BasketItem> getItems() {
        return items.values();
    }

    public BigDecimal getSubTotal() {
        return items.values().stream().map(BasketItem::getTotalPrice).reduce(new BigDecimal(0), BigDecimal::add);
    }

    public BigDecimal getTotalDiscount() {
        return items.values().stream().map(BasketItem::getDiscount).reduce(new BigDecimal(0), BigDecimal::add);
    }

    public String getSummary() {
        BigDecimal subTotal = this.getSubTotal();
        BigDecimal totalDiscount = this.getTotalDiscount();
        StringBuilder out = new StringBuilder();
        out.append("Subtotal: £" + subTotal + "\n");
        out.append("Discount: £" + totalDiscount + "\n");
        out.append("Total: £" + subTotal.subtract(totalDiscount) + "\n");
        if (totalDiscount.compareTo(new BigDecimal(0)) == 1) {
            out.append("Offers Applied:\n");
            out.append(items.values().stream()
                    .map(BasketItem::getOffer).filter(o -> o != null).collect(Collectors.joining("\n")));
        }
        return out.toString();
    }

}
