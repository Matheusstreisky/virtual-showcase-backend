package com.streisky.virtualshowcasebackend.domain.product.repository;

import java.util.Optional;

import com.streisky.virtualshowcasebackend.domain.product.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static com.streisky.virtualshowcasebackend.constants.ProductTestConstants.PAGE_REQUEST_DEFAULT;
import static com.streisky.virtualshowcasebackend.constants.ProductTestConstants.PRODUCT_DTO_ACTIVE;
import static com.streisky.virtualshowcasebackend.constants.ProductTestConstants.PRODUCT_DTO_INACTIVE;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    protected ProductRepository productRepository;

    @BeforeEach
    private void saveProducts() {
        productRepository.save(PRODUCT_DTO_ACTIVE .toEntity());
        productRepository.save(PRODUCT_DTO_INACTIVE.toEntity());
    }

    @Test
    void shouldFindExistingAndActiveProduct_AndNotFindNotExistingAndInactiveProduct_Test() {
        Optional<Product> productActive = productRepository.findByIdAndActivateTrue(PRODUCT_DTO_ACTIVE.id());
        Optional<Product> productInactive = productRepository.findByIdAndActivateTrue(PRODUCT_DTO_INACTIVE.id());
        Assertions.assertTrue(productActive.isPresent());
        Assertions.assertTrue(productInactive.isEmpty());
    }

    @Test
    void shouldFindAllActivateProducts_Test() {
        Page<Product> productPage = productRepository.findAllByActivateTrue(PAGE_REQUEST_DEFAULT);
        Assertions.assertEquals(1, productPage.getTotalElements());
        Assertions.assertEquals(PRODUCT_DTO_ACTIVE.description(), productPage.getContent().get(0).getDescription());
    }
}
