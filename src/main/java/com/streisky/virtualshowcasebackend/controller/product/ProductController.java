package com.streisky.virtualshowcasebackend.controller.product;

import java.net.URI;

import com.streisky.virtualshowcasebackend.dto.product.ProductDTO;
import com.streisky.virtualshowcasebackend.service.product.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Transactional
    public ResponseEntity<ProductDTO> register(@RequestBody @Valid ProductDTO productDTO) {
        ProductDTO savedProduct = productService.save(productDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.id())
                .toUri();

        return ResponseEntity.created(location).body(savedProduct);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO productDTO) {
        return ResponseEntity.ok(productService.update(productDTO));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> find(@PathVariable Long id) {
        return ResponseEntity.ok(productService.find(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(@PageableDefault(size = 10, sort = "description") Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }
}
