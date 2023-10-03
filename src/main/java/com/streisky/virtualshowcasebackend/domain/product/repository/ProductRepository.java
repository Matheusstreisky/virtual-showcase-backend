package com.streisky.virtualshowcasebackend.domain.product.repository;

import java.util.Optional;

import com.streisky.virtualshowcasebackend.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndActivateTrue(Long id);

    Page<Product> findAllByActivateTrue(Pageable pageable);
}
