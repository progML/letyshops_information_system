package com.businesslogic.client.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Data
public class ClientEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String login;

  @Column
  private String password;

  @Column
  private float balance = 0;
}