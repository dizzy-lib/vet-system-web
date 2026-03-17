package com.vet.vetweb;

public enum Role {
  ADMIN,
  VET;

  public String authority() {
    return "ROLE_" + this.name();
  }
}
