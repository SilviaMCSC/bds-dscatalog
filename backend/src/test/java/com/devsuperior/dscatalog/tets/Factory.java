package com.devsuperior.dscatalog.tets;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

import java.time.Instant;
import java.util.Set;

public class Factory {
    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png",
                Instant.parse("2020-10-20T03:00:00Z"));
        product.getCategories().add(new Category(2L, "Electronics"));
        return product;
    }

    //public static ProductDTO createProductDto() {
    // Product product = createProduct();
    //return new ProductDTO(product, product.getCategories());
    //}

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    private static class ProductDto {
        public ProductDto(Product product, Set<Category> categories) {
        }
    }
}
