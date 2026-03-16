package com.vet.vetweb;

public enum Role {
  ROOT,
  ADMIN,
  VET;

  public String authority() {
    return "ROLE_" + this.name();
  }
}
