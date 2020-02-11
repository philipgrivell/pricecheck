package uk.grivell.pricebasket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasketItemBuilder {
    public List<BasketItem> items = new ArrayList<>();

    public BasketItemBuilder withItems(BasketItem ... items) {
        this.items.addAll(Arrays.asList(items));
        return this;
    }

    public Basket build() {
        Basket basket = new Basket();
        items.stream().forEach(i -> basket.addItem(i));
        return basket;
    }
}
