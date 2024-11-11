package dev.codascripts.productservice.thirdpartyclient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private String id;
    private String title;
    private double price;
    private String imageUrl;
    private String description;
    private String category;
}
