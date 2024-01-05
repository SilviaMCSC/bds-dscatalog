package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tets.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;
    private long existingId;
    private long nonExistingId;
    private long CountTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        long existingId = 1L;
        long nonExistingId = 1000L;
        CountTotalProducts = 25L;
    }
@Test
public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
Product product = Factory.createProduct();
product.getId();
product = repository.save(product);

    long countTotalProducts = 25;

Assertions.assertNotNull(product.getId());
Assertions.assertEquals(countTotalProducts + 1, product.getId());
}


    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }


}


