package com.prorigo.model.response;

import lombok.Data;

@Data
public class UserResponse {
  private Long id;
  private String name;

  public UserResponse(Long id, String name) {
    this.id = id;
    this.name = name;
    // Set other fields as needed
  }

}
