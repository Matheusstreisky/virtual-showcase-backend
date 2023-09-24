package com.streisky.virtualshowcasebackend.dto.product;

import com.streisky.virtualshowcasebackend.dto.image.ImageDTO;
import com.streisky.virtualshowcasebackend.entity.image.Image;
import com.streisky.virtualshowcasebackend.entity.product.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

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
        Product product = (id() != null && id() > 0)
                ? new Product(id(), activate(), description(), amount(), null, observation())
                : new Product(id(), description(), amount(), null, observation());

        product.setImages(toImageEntity(product));
        return product;
    }

    public List<Image> toImageEntity(Product product) {
        return images() != null ? images().stream().map(imageDTO -> imageDTO.toEntity(product)).toList() : null;
    }
}
