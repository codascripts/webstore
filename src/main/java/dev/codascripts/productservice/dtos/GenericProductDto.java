package dev.codascripts.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenericProductDto {
    // since 3rd party api's can name their attributes anything note necessarily the below names
    // we create our owm Dto with our own names
    // and we then set these attributes to getMethods of 3rd party api
    // ex we are setting product.setImageUrl(fakeStoreProductDto.getImage())
    // so here even if a 3rd party api is naming it's image as only Image we can name our image link as imageUrl
    private String id;
    private String title;
    private double price;
    private String image;
    private String description;
    private String category;
}
