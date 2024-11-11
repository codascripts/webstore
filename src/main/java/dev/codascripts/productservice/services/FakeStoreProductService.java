package dev.codascripts.productservice.services;

import dev.codascripts.productservice.thirdpartyclient.FakeStoreProductDto;
import dev.codascripts.productservice.dtos.GenericProductDto;
import dev.codascripts.productservice.thirdpartyclient.FakeStoreProductServiceClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("fakeStoreProductService")
@Getter
@Setter
public class FakeStoreProductService implements ProductService {

    private FakeStoreProductServiceClient fakeStoreProductServiceClient;

//    private RestTemplateBuilder restTemplateBuilder;
//    private String baseProductUrl = "https://fakestoreapi.com/products";
//    private String productIdUrl = "https://fakestoreapi.com/products/{id}";

    // RestTemplate is a synchronous REST client which performs HTTP requests
    // RestTemplateBuilder is a Builder that can be used to configure and create a RestTemplate
    public FakeStoreProductService(FakeStoreProductServiceClient fakeStoreProductServiceClient) {
        this.fakeStoreProductServiceClient = fakeStoreProductServiceClient;
    }

    // what if the modifier is public what would happen then?
    private GenericProductDto convertFakeStoreToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {
        GenericProductDto product = new GenericProductDto();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setImage(fakeStoreProductDto.getImageUrl());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setCategory(fakeStoreProductDto.getCategory());
        return product;
    }

    public GenericProductDto getProductById(Long id){
        /*
        YOU DONT NEED TO WRITE THIS LOGIC HERE ANYMORE BECAUSE thirdpartyclient package has all the logic that is related to talking to the fakestore api
        RestTemplate restTemplate = restTemplateBuilder.build();
        // though we are returning the data in our Generic class but the data that the api is returning is FakeStoreProductDto
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.getForEntity(
                        productIdUrl, FakeStoreProductDto.class, id
                );

        // now we need to convert this FakeStoreDto into our class which we can control GenericProductDto
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        // converting the returned api resource into a class which we can handle
//        return fakeStoreProductDto
        */
        return convertFakeStoreToGenericProductDto(fakeStoreProductServiceClient.getProductById(id));
    }

    public List<GenericProductDto> getAllProducts(){
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        // to get all products we need an array
//        ResponseEntity<FakeStoreProductDto[]> response =
//                restTemplate.getForEntity(
//                        baseProductUrl, FakeStoreProductDto[].class
//                );
//
//        // we want this list so we will expect Generic class not FakeStore
        List<GenericProductDto> allProducts = new ArrayList<>();
//
        // you need to loop through
        /*fakeStoreProductServiceClient.getAllProducts() is a method call that is expected to return a collection (like a list, array, etc.) of FakeStoreProductDto objects.
        FakeStoreProductDto fakeStoreProductDto is a variable that will hold each individual object from the collection as the loop iterates through it.*/
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductServiceClient.getAllProducts()) {
            allProducts.add(convertFakeStoreToGenericProductDto(fakeStoreProductDto));
        }
        return allProducts;

    }

    public GenericProductDto createProduct(GenericProductDto product){
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        ResponseEntity<GenericProductDto> response =
//                restTemplate.postForEntity(
//                        baseProductUrl, product, GenericProductDto.class
//                );
//        return response.getBody();
        return convertFakeStoreToGenericProductDto(fakeStoreProductServiceClient.createProduct(product));
    }

    public GenericProductDto deleteProductById(Long id){
        // to delete first  you need to get the object
        // standard getForEntity will only do a GET request, it won't delete
        // to do that we use delete(); but here we also want the body of the object which we deleted
        // therefore we use execute
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        // .class refers to a class object that represents that class at runtime
//        RequestCallback requestCallback =
//                restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
//        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
//                restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
//        ResponseEntity<FakeStoreProductDto> response =
//                restTemplate.execute(
//                        productIdUrl,
//                        HttpMethod.DELETE,
//                        requestCallback,
//                        responseExtractor, id
//                );
//
//        FakeStoreProductDto fakeStoreProductDto = response.getBody();
//        return convertFakeStoreToGenericProductDto(fakeStoreProductDto);
        return convertFakeStoreToGenericProductDto(fakeStoreProductServiceClient.deleteProductById(id));
    }
}


