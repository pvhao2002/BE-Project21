package com.example.beproject21.model;


import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class Booking implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer packageId;
    private String fullName;
    private String email;
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private Date tourDate;
    private String request;
    private String status;

    private User user;
    private Package pack;
}
