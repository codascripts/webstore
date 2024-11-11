package dev.codascripts.productservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter

public class ErrorDto {
    private String message;
    private HttpStatus status;
    public ErrorDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
