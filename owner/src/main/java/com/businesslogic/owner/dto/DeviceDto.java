package com.businesslogic.owner.dto;

import lombok.Data;

@Data
public class DeviceDto {
  private float cost;
  private int id;

  public float getCost() {
    return cost;
  }

  public void setCost(float cost) {
    this.cost = cost;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public DeviceDto(float cost, int id) {
    this.cost = cost;
    this.id = id;
  }
}
