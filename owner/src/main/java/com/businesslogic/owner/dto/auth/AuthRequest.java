package com.businesslogic.owner.dto.auth;

import lombok.Data;

@Data
public class AuthRequest {
  private String login;
  private String password;
}