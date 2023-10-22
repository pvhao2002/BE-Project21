package com.example.beproject21.model;


import lombok.Data;

@Data
public class Package {
    private Integer id;
    private String name;
    private Double price;
    private Integer destinationId;
    private Integer days;
    private Integer numberOfPeople;
    private String description;
    private String image;
    private Boolean isDeleted;

    private Destination destination;
}
