package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor(onConstructor = @__(@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)))
public class RequestEntity implements Serializable {
    @Override
    public String toString() {
        return "RequestEntity{" +
                "productId='" + productId + '\'' +
                '}';
    }

    private String productId;
}
