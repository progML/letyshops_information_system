package com.businesslogic.client.dto;

import lombok.Data;


@Data
public class OrderItemsDto {

    private int orderId;
    private int basketId;

    public OrderItemsDto(int orderId, int basketId) {
        this.orderId = orderId;
        this.basketId = basketId;
    }
}
