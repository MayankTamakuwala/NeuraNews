package com.neuranews.backend.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseData implements ResponseObject{
    private Object neuraData;

    public ResponseData(Object data) {
            this.neuraData = data;
    }
}

