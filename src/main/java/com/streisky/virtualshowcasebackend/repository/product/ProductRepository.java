package com.streisky.virtualshowcasebackend.repository.product;

import com.streisky.virtualshowcasebackend.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByActivateTrue(Pageable pageable);

}
