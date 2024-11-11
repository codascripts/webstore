package dev.codascripts.productservice.thirdpartyclient;

import dev.codascripts.productservice.dtos.GenericProductDto;
import dev.codascripts.productservice.exceptions.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
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

@Service
@Getter
@Setter
// this function is related to Fake Store's api therefore it should not contain GenericDto
public class FakeStoreProductServiceClient {

    private RestTemplateBuilder restTemplateBuilder;

    @Value("${fakestore.api.url}")
    private String fakeStoreApiUrl;
    @Value("${fakestore.api.paths.product}")
    private String fakeStoreProductApiPath;

    /* since we are defining this in applications.properties these links must be initialized in the constructore
    because spring needs to know that we have written it in application.properties

    private String baseProductUrl = fakeStoreApiUrl;
    private String productIdUrl = fakeStoreApiUrl + fakeStoreProductApiPath + "${id}";
    */
    // therefore only declaring is enough because we are defining these in constructor
    private String baseProductUrl;
    private String productIdUrl;
    // by writing the urls in application.properties we won't need to look into the code
    // if we want to change our 3rd party api then we just change it in application.properties
    // we don't need to look around inside the code to find where is the url
    // therefore below 2 urls are not needed
//    private String baseProductUrl0 = "https://fakestoreapi.com/products";
//    private String productIdUrl0 = "https://fakestoreapi.com/products/{id}";

    // RestTemplate is a synchronous REST client which performs HTTP requests
    // RestTemplateBuilder is a Builder that can be used to configure and create a RestTemplate
    public FakeStoreProductServiceClient(RestTemplateBuilder restTemplateBuilder,
                                         @Value("${fakestore.api.url}") String fakeStoreApiUrl,
                                         @Value("${fakestore.api.paths.product}") String fakeStoreProductApiPath) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.baseProductUrl = fakeStoreApiUrl + fakeStoreProductApiPath;
//        "{id}" is a placeholder that allows dynamic insertion of the actual product ID at runtime.
        // writing just "/id" is  like you are making the url static
        this.productIdUrl = fakeStoreApiUrl + fakeStoreProductApiPath + "/{id}";
    }

    // what if the modifier is public what would happen then?
    /*you don't need this method here*/
//    private GenericProductDto convertFakeStoreToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {
//        GenericProductDto product = new GenericProductDto();
//        product.setId(fakeStoreProductDto.getId());
//        product.setTitle(fakeStoreProductDto.getTitle());
//        product.setImageUrl(fakeStoreProductDto.getImageUrl());
//        product.setDescription(fakeStoreProductDto.getDescription());
//        product.setPrice(fakeStoreProductDto.getPrice());
//        product.setCategory(fakeStoreProductDto.getCategory());
//        return product;
//    }

    public FakeStoreProductDto getProductById(Long id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        // though we are returning the data in our Generic class but the data that the api is returning is FakeStoreProductDto
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.getForEntity(
                        productIdUrl, FakeStoreProductDto.class, id
                );

        // now we need to convert this FakeStoreDto into our class which we can control GenericProductDto
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        // converting the returned api resource into a class which we can handle

        if (fakeStoreProductDto == null) {
            throw new NotFoundException("Product with id " + id + " not found");
        }

        return fakeStoreProductDto;
    }

    public List<FakeStoreProductDto> getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        // to get all products we need an array
        ResponseEntity<FakeStoreProductDto[]> response =
                restTemplate.getForEntity(
                        baseProductUrl, FakeStoreProductDto[].class
                );

        // we want this list so we will expect Generic class not FakeStore
//        List<GenericProductDto> allProducts = new ArrayList<>();

//        for (FakeStoreProductDto fakeStoreProductDto : Arrays.stream(response.getBody()).toList()) {
//            allProducts.add(fakeStoreProductDto);
//        }
        return Arrays.stream(response.getBody()).toList();

    }

    public FakeStoreProductDto createProduct(GenericProductDto product){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.postForEntity(
                        baseProductUrl, product, FakeStoreProductDto.class
                );
        return response.getBody();
    }

    public FakeStoreProductDto deleteProductById(Long id){
        // to delete first  you need to get the object
        // standard getForEntity will only do a GET request, it won't delete
        // to do that we use delete(); but here we also want the body of the object which we deleted
        // therefore we use execute
        RestTemplate restTemplate = restTemplateBuilder.build();
        // .class refers to a class object that represents that class at runtime
        RequestCallback requestCallback =
                restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.execute(
                        productIdUrl,
                        HttpMethod.DELETE,
                        requestCallback,
                        responseExtractor, id
                );

        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        return fakeStoreProductDto;
    }
}
