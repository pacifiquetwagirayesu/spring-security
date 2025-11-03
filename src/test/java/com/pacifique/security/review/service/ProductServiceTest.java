package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.ProductPaginationResponse;
import com.pacifique.security.review.dto.ProductRequest;
import com.pacifique.security.review.dto.ProductResponse;
import com.pacifique.security.review.model.Product;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.IProductRepository;
import com.pacifique.security.review.repository.IUserRepository;
import com.pacifique.security.review.utils.Utility;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    private IProductService productService;
    @Mock
    private IProductRepository productRepository;
    @Mock
    private IUserRepository userRepository;
    private Page<Product> pageable;
    private ProductResponse productResponse;
    private ProductRequest productRequest;
    private ProductPaginationResponse productPaginationResponse;
    private Product product;
    private User user;

    private static void authenticatedUser() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", null);
        SecurityContext securityContext = mock(SecurityContext.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        authenticationManager.authenticate(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @BeforeEach
    void setUp() {
        user = User.builder().email("e").firstName("f").password("p").lastName("l").role(Role.ADMIN.name())
                .permissions(Role.ADMIN.getPermissions())
                .id(1L)
                .build();

        product = Product.builder().id(1L).owner(user).name("Product 1").description("Description 1").build();

        productResponse = Utility.convertProductResponse().apply(product);
        productRequest = new ProductRequest("name", "desc");
        productPaginationResponse = new ProductPaginationResponse(1, 1, 2, List.of(productResponse));
        productService = new ProductService(productRepository, userRepository);
        pageable = new PageImpl<>(List.of(product));
    }

    @Test
    void getProductsTest() {
        PageRequest pageRequest = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "name"));
        when(productRepository.findAll(pageRequest)).thenReturn(pageable);
        Iterable<ProductResponse> products = productService.getProducts(1, 2);
        assertNotNull(products);
    }

    @Test
    void getMyProductsTest() {
        PageRequest pageRequest = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "name"));
        when(productRepository.findAllByOwner_Email(pageRequest)).thenReturn(pageable);
        ProductPaginationResponse myProducts = productService.getMyProducts(1, 2);
        assertNotNull(myProducts);
        assertEquals(productPaginationResponse, myProducts);
    }

    @Test
    void getProductTest() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        ProductResponse productById = productService.getProductById(1L);
        assertNotNull(productById);
        assertEquals(productById.name(), productResponse.name());
    }

    @Test
    void productNotFoundTest() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(1L), "Product not found");
    }

    @Test
    void addProductToNoUserTest() {
        authenticatedUser();
        assertThrows(EntityNotFoundException.class, () -> productService.addProduct(productRequest));
    }

    @Test
    void addProductTest() {
        authenticatedUser();
        doAnswer(new Answer<Product>() {
            @Override
            public Product answer(InvocationOnMock invocation) throws Throwable {
                return product;
            }
        }).when(productRepository).save(any());

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        ProductResponse response = productService.addProduct(productRequest);
        assertNotNull(response);
        assertEquals(productResponse.name(), response.name());
    }

    @Test
    void addProductsListTest() {
        doAnswer(new Answer<List<Product>>() {
            @Override
            public List<Product> answer(InvocationOnMock invocation) throws Throwable {
                return List.of(product);
            }
        }).when(productRepository).saveAllAndFlush(anyList());

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        authenticatedUser();
        when(productRepository.saveAllAndFlush(anyList())).thenReturn(List.of(product));
        Iterable<ProductResponse> productResponses = productService.addProducts(List.of(productRequest));
        assertNotNull(productResponses);
    }

    @Test
    void deleteProductTest() {
        doNothing().when(productRepository).deleteById(anyLong());

        Map<String, String> deletedProduct = productService.deleteProduct(1L);
        assertNotNull(deletedProduct);
        assertEquals("Product with id 1 deleted", deletedProduct.get("message"));
    }
}
