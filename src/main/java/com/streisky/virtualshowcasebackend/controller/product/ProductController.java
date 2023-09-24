package com.streisky.virtualshowcasebackend.controller.product;

import com.streisky.virtualshowcasebackend.dto.product.ProductDTO;
import com.streisky.virtualshowcasebackend.service.product.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid ProductDTO productDTO) {
        productService.save(productDTO);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid ProductDTO productDTO) {
        productService.save(productDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    public Page<ProductDTO> list(@PageableDefault(size = 10, sort = "description") Pageable pageable) {
        return productService.list(pageable);
    }
}
