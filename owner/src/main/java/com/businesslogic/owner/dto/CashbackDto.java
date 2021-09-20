package com.businesslogic.owner.dto;

import lombok.Data;

@Data
public class CashbackDto {
  private int id;
  private float cashback;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public float getCashback() {
    return cashback;
  }




  public CashbackDto(int id, float cashback) {
    this.id = id;
    this.cashback = cashback;

  }
}
