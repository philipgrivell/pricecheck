package uk.grivell.pricebasket.persistence;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {
    Product findByName(String name);
    @Override
    Iterable<Product> findAll();
}

