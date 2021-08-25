package com.businesslogic.client.dto;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
public class BasketEntityDto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private  int id;
    private int device_id;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getDevice_id() {
        return device_id;
    }


    public BasketEntityDto(int id, int device_id) {
        this.id = id;
        this.device_id = device_id;
    }


}
