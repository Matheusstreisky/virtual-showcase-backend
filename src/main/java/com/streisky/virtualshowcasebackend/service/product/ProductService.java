package com.streisky.virtualshowcasebackend.service.product;


import com.streisky.virtualshowcasebackend.dto.product.ProductDTO;
import com.streisky.virtualshowcasebackend.entity.product.Product;
import com.streisky.virtualshowcasebackend.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void save(ProductDTO productDTO) {
        productRepository.save(productDTO.toEntity());
    }

    public void delete(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.inactivate();
    }

    public Page<ProductDTO> list(Pageable pageable) {
        return productRepository.findAllByActivateTrue(pageable).map(Product::toDTO);
    }
}
