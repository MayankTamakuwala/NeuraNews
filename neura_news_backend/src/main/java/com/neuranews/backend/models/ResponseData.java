package com.neuranews.backend.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseData implements ResponseObject{
    private Object data;

    public ResponseData(Object data) {
            this.data = data;
    }
}

