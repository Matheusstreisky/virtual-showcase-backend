package com.streisky.virtualshowcasebackend.controller.product;

import java.util.List;

import com.google.gson.JsonParser;
import com.streisky.virtualshowcasebackend.domain.product.dto.ProductDTO;
import com.streisky.virtualshowcasebackend.domain.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.streisky.virtualshowcasebackend.constants.ProductTestConstants.PAGE_REQUEST_DEFAULT;
import static com.streisky.virtualshowcasebackend.constants.ProductTestConstants.PRODUCT_DTO_ACTIVE;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ProductDTO> productDTORequestJson;

    @Autowired
    private JacksonTester<ProductDTO> productDTOResponseJson;

    @Autowired
    private JacksonTester<Page<ProductDTO>> productDTOListResponseJson;

    @MockBean
    private ProductService productService;

    public static final String PRODUCT_URL = "/products";
    public static final String PRODUCT_URL_WITH_ID = PRODUCT_URL + "/{id}";

    @Test
    @WithMockUser
    void shouldReturnBadRequestStatus_WhenRequestPostWithInvalidBody_Test() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(PRODUCT_URL)).andReturn().getResponse();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @WithMockUser
    void shouldCreateProduct_AndReturnCreatedStatus_WhenRequestPostWithValidBody_Test() throws Exception {
        Mockito.when(productService.save(Mockito.any(ProductDTO.class))).thenReturn(PRODUCT_DTO_ACTIVE);

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(PRODUCT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(productDTORequestJson.write(PRODUCT_DTO_ACTIVE).getJson())
                )
                .andReturn()
                .getResponse();
        String jsonExpected = productDTOResponseJson.write(PRODUCT_DTO_ACTIVE).getJson();

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        Assertions.assertEquals(JsonParser.parseString(jsonExpected), JsonParser.parseString(response.getContentAsString()));
    }

    @Test
    @WithMockUser
    void shouldReturnBadRequestStatus_WhenRequestPutWithInvalidBody_Test() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_URL)).andReturn().getResponse();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @WithMockUser
    void shouldUpdateProduct_AndReturnOkStatus_WhenRequestPutWithValidBody_Test() throws Exception {
        Mockito.when(productService.update(Mockito.any(ProductDTO.class))).thenReturn(PRODUCT_DTO_ACTIVE);

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put(PRODUCT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(productDTORequestJson.write(PRODUCT_DTO_ACTIVE).getJson())
                )
                .andReturn()
                .getResponse();
        String jsonExpected = productDTOResponseJson.write(PRODUCT_DTO_ACTIVE).getJson();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertEquals(JsonParser.parseString(jsonExpected), JsonParser.parseString(response.getContentAsString()));
    }

    @Test
    @WithMockUser
    void shouldDeleteProduct_AndReturnNoContentStatus_WhenRequestDeleteWithValidId_Test() throws Exception {
        Mockito.doNothing().when(productService).delete(PRODUCT_DTO_ACTIVE.id());

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .delete(PRODUCT_URL_WITH_ID, PRODUCT_DTO_ACTIVE.id())
                                .contentType(MediaType.TEXT_PLAIN)
                )
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    @WithMockUser
    void shouldReturnProduct_AndReturnOkStatus_WhenRequestGetWithValidId_Test() throws Exception {
        Mockito.when(productService.find(PRODUCT_DTO_ACTIVE.id())).thenReturn(PRODUCT_DTO_ACTIVE);

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(PRODUCT_URL_WITH_ID, PRODUCT_DTO_ACTIVE.id())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();
        String jsonExpected = productDTOResponseJson.write(PRODUCT_DTO_ACTIVE).getJson();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertEquals(JsonParser.parseString(jsonExpected), JsonParser.parseString(response.getContentAsString()));
    }

    @Test
    @WithMockUser
    void shouldReturnAllProducts_AndReturnOkStatus_WhenRequestGetWithValidId_Test() throws Exception {
        Page<ProductDTO> productDTOPage = new PageImpl<>(List.of(PRODUCT_DTO_ACTIVE), PAGE_REQUEST_DEFAULT, 1);
        Mockito.when(productService.findAll(Mockito.any(Pageable.class))).thenReturn(productDTOPage);

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(PRODUCT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();
        String jsonExpected = productDTOListResponseJson.write(productDTOPage).getJson();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertEquals(JsonParser.parseString(jsonExpected), JsonParser.parseString(response.getContentAsString()));
    }
}
