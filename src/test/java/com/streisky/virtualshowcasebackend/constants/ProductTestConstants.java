package com.streisky.virtualshowcasebackend.constants;

import java.math.BigDecimal;
import java.util.List;

import com.streisky.virtualshowcasebackend.domain.image.dto.ImageDTO;
import com.streisky.virtualshowcasebackend.domain.product.dto.ProductDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class ProductTestConstants {

    public static final ProductDTO PRODUCT_DTO_ACTIVE = new ProductDTO(1L, true, "Product active", new BigDecimal(10),
            List.of(
                    new ImageDTO(1L, "Image 1-1 test", new byte[11]),
                    new ImageDTO(2L, "Image 1-2 test", new byte[12])
            ), "Test 1");
    public static final ProductDTO PRODUCT_DTO_INACTIVE = new ProductDTO(2L, false, "Product inactive", new BigDecimal(20),
            List.of(
                    new ImageDTO(3L, "Image 2-1 test", new byte[21]),
                    new ImageDTO(4L, "Image 2-2 test", new byte[22])
            ), "Test 2");
    public static final PageRequest PAGE_REQUEST_DEFAULT = PageRequest.of(0, 10, Sort.by("description"));
}
