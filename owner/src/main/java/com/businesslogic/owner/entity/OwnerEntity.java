package com.businesslogic.owner.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "owners")
@Data
public class OwnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @Column
    private int storeCount = 1;


}
