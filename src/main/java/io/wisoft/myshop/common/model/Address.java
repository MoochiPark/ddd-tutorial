package io.wisoft.myshop.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
  @Column
  private String zipCode;

  @Column
  private String address1;

  @Column
  private String address2;
}
