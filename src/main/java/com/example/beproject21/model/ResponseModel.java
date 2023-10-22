package com.example.beproject21.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseModel {
    private String status;
    private String message;
    private Object data;
}
