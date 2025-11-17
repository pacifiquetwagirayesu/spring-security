package com.pacifique.security.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacifique.security.review.dto.ProductPaginationResponse;
import com.pacifique.security.review.dto.ProductRequest;
import com.pacifique.security.review.dto.ProductResponse;
import com.pacifique.security.review.model.Product;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.security.JwtAuthenticationFilter;
import com.pacifique.security.review.security.SuperAdminAuthFilter;
import com.pacifique.security.review.services.IProductService;
import com.pacifique.security.review.utils.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.shaded.com.google.common.collect.Maps;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IProductService productService;
    @MockitoBean
    private JwtAuthenticationFilter authenticationFilter;
    @MockitoBean
    private SuperAdminAuthFilter superAdminAuthFilter;
    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private ProductResponse productResponse;

    @BeforeEach
    void init() {
        User user = User.builder()
                .id(1L)
                .password("password")
                .role(Role.USER.name())
                .permissions(Role.USER.getPermissions())
                .lastName("l")
                .firstName("f")
                .email("user@gmail.com")
                .createdAt(LocalDateTime.now()).build();
        product = Product.builder()
                .id(1L)
                .description("desc")
                .name("name")
                .owner(user)
                .build();

    }

    @Test
    @DisplayName("Test Get Product By Id Controller Endpoint")
    void getProductTest() throws Exception {
        productResponse = Utility.convertProductResponse().apply(product);
        when(productService.getProductById(anyLong())).thenReturn(productResponse);

        this.mockMvc.perform(get("/api/v1/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("desc"));

        verify(productService).getProductById(anyLong());

    }


    @Test
    @DisplayName("Test Getting product List Endpoint")
    void getProductsTest() throws Exception {
        productResponse = Utility.convertProductResponse().apply(product);
        when(productService.getProducts(anyInt(),anyInt())).thenReturn(List.of(productResponse));

        this.mockMvc.perform(get("/api/v1/products")
                        .param("page", "1")
                        .param("size", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @DisplayName("Test Get My Products Controller Endpoint")
    void getMyProductsTest() throws Exception {
        productResponse = Utility.convertProductResponse().apply(product);
        ProductPaginationResponse productPaginationResponse = new ProductPaginationResponse(1, 1, 1, List.of(productResponse));
        when(productService.getMyProducts(anyInt(),anyInt())).thenReturn(productPaginationResponse);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("page", "0");
        multiValueMap.add("size", "1");
        this.mockMvc.perform(get("/api/v1/products/me").params(multiValueMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products.size()").value(1));
    }


    @Test
    @DisplayName("Test Add Product Endpoint")
    void addProductTest() throws Exception {
        productResponse = Utility.convertProductResponse().apply(product);
        when(productService.addProduct(new ProductRequest("name","desc"))).thenReturn(productResponse);

        this.mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Test Add Product Endpoint")
    void addProductListTest() throws Exception {
        productResponse = Utility.convertProductResponse().apply(product);
        when(productService.addProducts(List.of(new ProductRequest("name","desc")))).thenReturn(List.of(productResponse));

        this.mockMvc.perform(post("/api/v1/products/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(product))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @DisplayName("Test Delete Product Endpoint")
    void deleteProductTest() throws Exception {
        when(productService.deleteProduct(anyLong())).thenReturn(Map.of("message", "deleted product"));

        this.mockMvc.perform(delete(String.format("/api/v1/products/%d", product.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("deleted product"));
    }
}
