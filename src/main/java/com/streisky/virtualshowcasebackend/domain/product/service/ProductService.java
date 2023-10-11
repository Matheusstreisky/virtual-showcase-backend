package com.streisky.virtualshowcasebackend.domain.product.service;


import java.util.Optional;

import com.streisky.virtualshowcasebackend.config.validation.exception.ResourceNotFoundException;
import com.streisky.virtualshowcasebackend.domain.product.dto.ProductDTO;
import com.streisky.virtualshowcasebackend.domain.product.entity.Product;
import com.streisky.virtualshowcasebackend.domain.product.repository.ProductRepository;
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

    public ProductDTO update(ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findByIdAndActivateTrue(productDTO.id());
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException(productDTO.id());
        }

        Product product = optionalProduct.get();
        product.updateData(productDTO);
        return product.toDTO();
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Product> optionalProduct = productRepository.findByIdAndActivateTrue(id);
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }

        optionalProduct.get().inactivate();
    }

    public ProductDTO find(Long id) throws ResourceNotFoundException {
        Optional<Product> optionalProduct = productRepository.findByIdAndActivateTrue(id);
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }

        return optionalProduct.get().toDTO();
    }

    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAllByActivateTrue(pageable).map(Product::toDTO);
    }
}
