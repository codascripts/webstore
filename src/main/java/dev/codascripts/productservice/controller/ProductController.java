package dev.codascripts.productservice.controller;

import dev.codascripts.productservice.dtos.GenericProductDto;
import dev.codascripts.productservice.exceptions.NotFoundException;
import dev.codascripts.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    public ProductController(@Qualifier("fakeStoreProductService")ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public GenericProductDto getProductById(@PathVariable("id") Long id) throws NotFoundException {
        // even if you handle the Error case in service spring controller needs to know what error to throw
        return productService.getProductById(id);
    }

    @GetMapping
    public List<GenericProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    // @RequestBody is imp without it you won't get the output
    public GenericProductDto createProduct(@RequestBody GenericProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @DeleteMapping("/{id}")
    public GenericProductDto deleteProductById(@PathVariable("id") Long id){
        // if you look at the code it is deleting the id
        // therefore when perform delete operation you are getting an empty body because it get deleted
        // to get the deleted body you must use ResponseEntity
//        return new ResponseEntity<>(productService.deleteProductById(id), HttpStatus.OK);
        // this also works
        return productService.deleteProductById(id);
    }
/*
    @PutMapping
    public GenericProductDto updateProduct(@PathVariable("id") Long id, @RequestBody FakeStoreProductDto productDto) {
//        GenericProductDto updatedProduct = productService.updateProduct(id, productDto);
        return productService.updateProduct(id, productDto);
    }
*/
}
