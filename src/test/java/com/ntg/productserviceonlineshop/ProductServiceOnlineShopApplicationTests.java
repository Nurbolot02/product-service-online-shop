package com.ntg.productserviceonlineshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntg.productserviceonlineshop.dto.ProductRequestDTO;
import com.ntg.productserviceonlineshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@AllArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductServiceOnlineShopApplicationTests {

    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
    @BeforeEach
    void createProducts (){
        ProductRequestDTO productRequestDTO = getProductRequestDTO();
        Product product = modelMapper.map(productRequestDTO, Product.class);
        productRepository.save(product);
    }
    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequestDTO productRequestDTO = getProductRequestDTO();
        String productRequestDTOString = objectMapper.writeValueAsString(productRequestDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestDTOString)
                )
                .andExpect(status().isCreated());
        List<Product> products = productRepository.findAll();
        Assertions.assertEquals(2, products.size());
        Assertions.assertEquals(productRequestDTO, modelMapper.map(products.get(0), ProductRequestDTO.class));
    }

    @Test
    void shouldReturnProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Is.is("IPhone 14")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Is.is("IPhone 14")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Is.is(1500)));

    }

    private ProductRequestDTO getProductRequestDTO() {
        return ProductRequestDTO.builder()
                .name("IPhone 14")
                .description("IPhone 14")
                .price(BigDecimal.valueOf(1500))
                .build();
    }

    @AfterEach
    void deleteAllProducts() {
        productRepository.deleteAll();
    }

}
