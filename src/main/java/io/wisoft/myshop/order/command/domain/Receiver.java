package io.wisoft.myshop.order.command.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.*;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Receiver {

  @Column(name = "receiver_name")
  private String name;

  @Column(name = "receiver_phone")
  private String phone;

}
