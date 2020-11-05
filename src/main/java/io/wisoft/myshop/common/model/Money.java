package io.wisoft.myshop.common.model;

import lombok.*;

import javax.persistence.Access;
import javax.persistence.AccessType;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Access(AccessType.FIELD)
public class Money {

  private int value;

  public Money multiply(int multiplier) {
    return new Money(value * multiplier);
  }

}
