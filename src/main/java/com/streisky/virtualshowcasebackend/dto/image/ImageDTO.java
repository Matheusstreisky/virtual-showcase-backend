package com.streisky.virtualshowcasebackend.dto.image;

import com.streisky.virtualshowcasebackend.entity.image.Image;
import com.streisky.virtualshowcasebackend.entity.product.Product;
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
