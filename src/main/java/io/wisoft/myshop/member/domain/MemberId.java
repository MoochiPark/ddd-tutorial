package io.wisoft.myshop.member.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class MemberId implements Serializable {

  @Column(name = "member_id")
  private String id;

}
