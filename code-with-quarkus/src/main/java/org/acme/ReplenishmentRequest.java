package org.acme;

public class ReplenishmentRequest {
  private String id;
  private String name;
  private Integer quantity;

  public ReplenishmentRequest(String id, String name, Integer quantity) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
