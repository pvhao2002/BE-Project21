package com.example.beproject21.model;


import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String address;
    private Integer roleId;
    private String isDeleted;
}
