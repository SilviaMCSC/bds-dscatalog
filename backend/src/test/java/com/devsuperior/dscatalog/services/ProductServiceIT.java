package com.devsuperior.dscatalog.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

@SpringBootTest@Transactional
public class ProductServiceIT {
    @Autowired
    private ProductService service;
    @Autowired
    private ProductRepository repository;
    private long existingId;
    private long nonexistingId;
    private Long countTotalProducts;


    @BeforeEach
    void setUp() throws Exception {
        long existingId = 1L;
        long nonexistingId = 1000L;
        Long countTotalProducts = 25L;
    }

    @Test
    public void deleteShouldResourceWhenIdExists() {
        service.delete(existingId);
        Assertions.assertEquals(countTotalProducts - 1, repository.count());
    }

}

