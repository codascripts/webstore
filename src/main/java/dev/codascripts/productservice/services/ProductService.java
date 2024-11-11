package dev.codascripts.productservice.services;

import dev.codascripts.productservice.dtos.GenericProductDto;

import java.util.List;

public interface ProductService {

    GenericProductDto getProductById(Long id);
    List<GenericProductDto> getAllProducts();
    GenericProductDto createProduct(GenericProductDto product);
    GenericProductDto deleteProductById(Long id);
//    GenericProductDto updateProduct(Long id, FakeStoreProductDto product);

}
