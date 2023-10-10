package com.streisky.virtualshowcasebackend.domain.product.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.streisky.virtualshowcasebackend.config.validation.exception.ResourceNotFoundException;
import com.streisky.virtualshowcasebackend.domain.image.dto.ImageDTO;
import com.streisky.virtualshowcasebackend.domain.product.dto.ProductDTO;
import com.streisky.virtualshowcasebackend.domain.product.entity.Product;
import com.streisky.virtualshowcasebackend.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static com.streisky.virtualshowcasebackend.constants.ProductTestConstants.PAGE_REQUEST_DEFAULT;
import static com.streisky.virtualshowcasebackend.constants.ProductTestConstants.PRODUCT_DTO_ACTIVE;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void shouldSaveProductSuccessfully_Test() {
        Product productEntity = PRODUCT_DTO_ACTIVE.toEntity();
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(productEntity);
        ProductDTO productDTO = productService.save(PRODUCT_DTO_ACTIVE);
        Assertions.assertEquals(productDTO, productEntity.toDTO());
    }

    @Test
    public void shouldUpdateProductSuccessfully_Test() {
        ProductDTO productToUpdateDTO = new ProductDTO(1L, true, "Product updated", new BigDecimal(30),
                List.of(
                        new ImageDTO(1L, "Image updated test 1", new byte[1]),
                        new ImageDTO(2L, "Image updated test 2", new byte[2])
                ), "Test update");

        Mockito.when(productRepository.findByIdAndActivateTrue(Mockito.anyLong())).thenReturn(Optional.of(PRODUCT_DTO_ACTIVE.toEntity()));
        ProductDTO productUpdatedDTO = productService.update(productToUpdateDTO);
        Assertions.assertEquals(productToUpdateDTO.description(), productUpdatedDTO.description());
        Assertions.assertEquals(productToUpdateDTO.amount(), productUpdatedDTO.amount());
        Assertions.assertEquals(productToUpdateDTO.observation(), productUpdatedDTO.observation());

        productUpdatedDTO.images().forEach(imageDTO -> {
            Optional<ImageDTO> optionalImageDTO = productToUpdateDTO.images().stream()
                    .filter(image -> Objects.equals(image.description(), imageDTO.description()))
                    .findFirst();

            Assertions.assertTrue(optionalImageDTO.isPresent());
            Assertions.assertEquals(optionalImageDTO.get().description(), imageDTO.description());
        });
    }

    @Test
    public void shouldDeleteProductSuccessfully_Test() {
        Mockito.when(productRepository.findByIdAndActivateTrue(Mockito.anyLong())).thenReturn(Optional.of(PRODUCT_DTO_ACTIVE.toEntity()));
        Assertions.assertDoesNotThrow(() -> productService.delete(PRODUCT_DTO_ACTIVE.id()));
    }

    @Test
    public void shouldFindProductSuccessfully_Test() {
        Mockito.when(productRepository.findByIdAndActivateTrue(Mockito.anyLong())).thenReturn(Optional.of(PRODUCT_DTO_ACTIVE.toEntity()));
        Assertions.assertDoesNotThrow(() -> productService.find(PRODUCT_DTO_ACTIVE.id()));
    }

    @Test
    public void shouldFindAllProductsSuccessfully_Test() {
        Page<Product> productPage = new PageImpl<>(List.of(PRODUCT_DTO_ACTIVE.toEntity()), PAGE_REQUEST_DEFAULT, 1);
        Mockito.when(productRepository.findAllByActivateTrue(Mockito.any(Pageable.class))).thenReturn(productPage);
        Assertions.assertDoesNotThrow(() -> productService.findAll(PAGE_REQUEST_DEFAULT));
    }

    @Test
    public void shouldThrowsResourceNotFoundException_WhenTryUpdateDeleteOrFind_ProductWithInvalidId_Test() {
        Mockito.when(productRepository.findByIdAndActivateTrue(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.update(PRODUCT_DTO_ACTIVE));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.delete(PRODUCT_DTO_ACTIVE.id()));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.find(PRODUCT_DTO_ACTIVE.id()));
    }

}
