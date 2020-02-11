package uk.grivell.pricebasket.persistence;

import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<Offer, String> {
    Offer findByProductName(String name);
}

