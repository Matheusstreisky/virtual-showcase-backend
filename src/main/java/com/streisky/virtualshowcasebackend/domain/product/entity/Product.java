package com.streisky.virtualshowcasebackend.domain.product.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.streisky.virtualshowcasebackend.domain.image.dto.ImageDTO;
import com.streisky.virtualshowcasebackend.domain.image.entity.Image;
import com.streisky.virtualshowcasebackend.domain.product.dto.ProductDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean activate;

    private String description;

    private BigDecimal amount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Image> images;

    @Column(columnDefinition = "TEXT")
    private String observation;

    public Product(Long id, String description, BigDecimal amount, List<Image> images, String observation) {
        this.id = id;
        this.activate = true;
        this.description = description;
        this.amount = amount;
        this.images = images;
        this.observation = observation;
    }

    public ProductDTO toDTO() {
        return new ProductDTO(getId(), getActivate(), getDescription(), getAmount(), mapImagesDTO(), getObservation());
    }

    private List<ImageDTO> mapImagesDTO() {
        return Objects.nonNull(getImages()) ? getImages().stream().map(Image::toDTO).toList() : null;
    }

    public void updateData(ProductDTO productDTO) {
        if (Objects.nonNull(productDTO.description())) {
            setDescription(productDTO.description());
        }

        if (Objects.nonNull(productDTO.amount())) {
            setAmount(productDTO.amount());
        }

        if (Objects.nonNull(productDTO.images())) {
            updateImagesData(productDTO.images());
        }

        if (Objects.nonNull(productDTO.observation())) {
            setObservation(productDTO.observation());
        }
    }

    private void updateImagesData(List<ImageDTO> imageDTOList) {
        imageDTOList.forEach(imageDTO -> {
            getImages().stream()
                .filter(image -> Objects.equals(image.getId(), imageDTO.id()))
                .findFirst()
                .ifPresent(image -> {
                    if (Objects.nonNull(imageDTO.description())) {
                        image.setDescription(imageDTO.description());
                    }

                    if (Objects.nonNull(imageDTO.image())) {
                        image.setImage(image.getImage());
                    }
                });
        });
    }

    public void inactivate() {
        this.activate = false;
    }
}
