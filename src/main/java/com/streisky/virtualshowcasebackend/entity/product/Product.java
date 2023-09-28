package com.streisky.virtualshowcasebackend.entity.product;

import java.math.BigDecimal;
import java.util.List;

import com.streisky.virtualshowcasebackend.dto.image.ImageDTO;
import com.streisky.virtualshowcasebackend.dto.product.ProductDTO;
import com.streisky.virtualshowcasebackend.entity.image.Image;
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
        return getImages() != null ? getImages().stream().map(Image::toDTO).toList() : null;
    }

    public void inactivate() {
        this.activate = false;
    }
}
