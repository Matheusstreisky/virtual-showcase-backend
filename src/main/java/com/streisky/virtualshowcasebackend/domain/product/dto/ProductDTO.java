package com.streisky.virtualshowcasebackend.domain.product.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.streisky.virtualshowcasebackend.domain.image.dto.ImageDTO;
import com.streisky.virtualshowcasebackend.domain.image.entity.Image;
import com.streisky.virtualshowcasebackend.domain.product.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        Long id,
        @NotNull
        boolean activate,
        @NotBlank
        String description,
        @NotNull
        @DecimalMin(value = "0.0")
        BigDecimal amount,
        @Valid
        List<ImageDTO> images,
        String observation
) {

    public Product toEntity() {
        Product product = new Product(id(), activate(), description(), amount(), null, observation());
        product.setImages(mapImageEntity(product));
        return product;
    }

    private List<Image> mapImageEntity(Product product) {
        return Objects.nonNull(images()) ? images().stream().map(imageDTO -> imageDTO.toEntity(product)).toList() : null;
    }
}
