package com.neuranews.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseError implements ResponseObject{
    private String message;

    public ResponseError(String message) {
        this.message = message;
    }
}
