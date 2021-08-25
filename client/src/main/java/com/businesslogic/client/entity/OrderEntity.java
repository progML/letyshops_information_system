package com.businesslogic.client.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "orders")
@Data
public class OrderEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String state;

    @Column
    private String place;



    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId", nullable = false)
    private ClientEntity clientEntity;


    @JsonIgnore
    @OneToMany(mappedBy = "orderEntity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItemEntities;



}
