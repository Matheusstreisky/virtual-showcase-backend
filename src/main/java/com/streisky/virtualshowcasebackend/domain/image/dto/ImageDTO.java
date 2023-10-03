package com.streisky.virtualshowcasebackend.domain.image.dto;

import com.streisky.virtualshowcasebackend.domain.image.entity.Image;
import com.streisky.virtualshowcasebackend.domain.product.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImageDTO(
        Long id,
        @NotBlank
        String description,
        @NotNull
        byte[] image
) {

    public Image toEntity(Product product) {
        return new Image(id(), description(), image(), product);
    }
}
