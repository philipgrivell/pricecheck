package uk.grivell.pricebasket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import uk.grivell.pricebasket.persistence.Offer;
import uk.grivell.pricebasket.persistence.OfferRepository;

@Component
public class DiscountCalculator {
    @Autowired
    private OfferRepository offers;

    public void applyDiscount(Basket basket, BasketItem item) {
        Offer offer = offers.findByProductName(item.getProduct().getName());
        if (offer != null && matchesRule(basket, offer.getRule())) {
            item.setDiscount(offer.getDiscount());
            item.setOffer(offer.getDescription());
        }
    }

    boolean matchesRule(Basket basket, String rule) {
        if (rule == null) {
            return true;
        }

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(rule);
        EvaluationContext context = new StandardEvaluationContext(basket);
        return (boolean) exp.getValue(context);
    }
}
