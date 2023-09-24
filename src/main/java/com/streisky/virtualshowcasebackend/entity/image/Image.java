package com.streisky.virtualshowcasebackend.entity.image;

import com.streisky.virtualshowcasebackend.dto.image.ImageDTO;
import com.streisky.virtualshowcasebackend.entity.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Lob
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ImageDTO toDTO() {
        return new ImageDTO(getId(), getDescription(), getImage());
    }
}
