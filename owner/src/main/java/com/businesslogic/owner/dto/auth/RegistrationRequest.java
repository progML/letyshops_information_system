package com.businesslogic.owner.dto.auth;


import lombok.Data;


@Data
public class RegistrationRequest {


    private String login;

    private String password;

    private String name;

    private float cashback;

}