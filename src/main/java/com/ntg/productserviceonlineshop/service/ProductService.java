package com.ntg.productserviceonlineshop.service;

import com.ntg.productserviceonlineshop.dto.ProductRequestDTO;
import com.ntg.productserviceonlineshop.dto.ProductResponseDTO;
import com.ntg.productserviceonlineshop.model.Product;
import com.ntg.productserviceonlineshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    public void save(ProductRequestDTO productRequestDTO) {
        Product product = modelMapper.map(productRequestDTO, Product.class);
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> productResponseDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .toList();
        log.info("returned {}-products from repository", productResponseDTOS.size());
        return productResponseDTOS;
    }
}
