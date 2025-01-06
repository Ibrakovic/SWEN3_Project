package org.openapitools.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityResponse {

    private int status;

    private String message;

    private Object data;

    public EntityResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
