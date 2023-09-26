package com.streisky.virtualshowcasebackend.service.product;


import java.util.Objects;

import com.streisky.virtualshowcasebackend.dto.product.ProductDTO;
import com.streisky.virtualshowcasebackend.entity.product.Product;
import com.streisky.virtualshowcasebackend.exception.VirtualShowcaseNotFoundException;
import com.streisky.virtualshowcasebackend.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO save(ProductDTO productDTO) {
        Product product = productRepository.save(productDTO.toEntity());
        return product.toDTO();
    }

    public void delete(Long id) throws VirtualShowcaseNotFoundException {
        try {
            Product product = productRepository.getReferenceById(id);
            product.inactivate();
        } catch (EntityNotFoundException e) {
            throw new VirtualShowcaseNotFoundException(id);
        }
    }

    public ProductDTO find(Long id) throws VirtualShowcaseNotFoundException {
        Product product = productRepository.findByIdAndActivateTrue(id);
        if (Objects.isNull(product)) {
            throw new VirtualShowcaseNotFoundException(id);
        }

        return product.toDTO();
    }

    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAllByActivateTrue(pageable).map(Product::toDTO);
    }
}
