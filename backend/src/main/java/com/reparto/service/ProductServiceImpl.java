package com.reparto.service;

import com.reparto.converter.ProductConverter;
import com.reparto.models.dto.ProductDTO;
import com.reparto.repository.ProductRepository;
import com.reparto.entity.ProductEntity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();

        return products.stream()
                .map(ProductConverter::entityToDTO)
                .collect(Collectors.toList());
    }
}
