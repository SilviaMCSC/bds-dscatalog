package com.devsuperior.dscatalog.services;


import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tets.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

//@ExtendWith(SpringExtension.class);
@WebMvcTest
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repository;
    private long existingId;
    private long nonexistingId;
    private long independentId;
    private PageImpl<Product> page;
    private Product product;
    private String productDto;

    @BeforeEach
    void setUp() {
        long existingId = 1L;
        long nonexistingId = 1000L;
        long independentId = 4L;
        String productDto;
        Product product = Factory.createProduct();
        page = new PageImpl<Product>(List.of(product));

        //quando o método não é void a sintaxe começa com when
        Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonexistingId)).thenReturn(Optional.empty());
        Mockito.when(repository.getOne(existingId)).thenReturn(product);

        //quando metodo é void a sintaxe começa com a ação
        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).
                when(repository).deleteById(nonexistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).
                when(repository).deleteById(independentId);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).getOne(nonexistingId);
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {
        ProductDTO productDTO = new ProductDTO();
        ProductDTO result = service.upDate(existingId, productDTO);
        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any(Product.class));
    }

    @Test
    public void updateIdShouldResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonexistingId);
        });
        Mockito.verify(repository, Mockito.times(1)).delete(nonexistingId);
    }

    @Test
    public void findByIdPageShouldReturnProductDTOWhenIdExists() {

        ProductDTO productDTO = new ProductDTO();
        ProductDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonexistingId);
        });
        Mockito.verify(repository, Mockito.times(1)).delete(nonexistingId);
    }
    @Test
    public void findAllPageShouldReturnPage() {

        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductDTO> result = service.findAllPaged(pageable);
        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
    }
    @Test
    public void deleteShouldDataBaseExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(independentId);
        });
        Mockito.verify(repository, Mockito.times(1)).delete(independentId);
    }
    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNot() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonexistingId);
        });

        Mockito.verify(repository, Mockito.times(1)).delete(nonexistingId);
    }
    @Test
    public void deleteShouldDoNothingIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
        Mockito.verify(repository, Mockito.times(1))
                .delete(existingId);
    }

}