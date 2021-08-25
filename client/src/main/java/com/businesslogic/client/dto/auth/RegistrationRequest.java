package com.businesslogic.client.dto.auth;


import lombok.Data;


@Data
public class RegistrationRequest {


  private String login;

  private String password;
}