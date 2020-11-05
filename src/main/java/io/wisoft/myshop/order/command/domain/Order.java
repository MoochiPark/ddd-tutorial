package io.wisoft.myshop.order.command.domain;

import io.wisoft.myshop.common.model.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static io.wisoft.myshop.order.command.domain.OrderState.PAYMENT_WAITING;
import static io.wisoft.myshop.order.command.domain.OrderState.PREPARING;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "purchase_order")
@Access(AccessType.FIELD)
public class Order {

  @EmbeddedId
  private OrderNo number;

  @Version
  // 엔티티 버전 관리
  private long version;

  @Embedded
  private Orderer orderer;

  @ElementCollection
  @CollectionTable(name = "order_line",
      joinColumns = @JoinColumn(name = "order_number"))
  /*
   * @OrderColumn : 순서가 있는 복수 컬렉션을 의미
   * line_idx : 순서 컬럼
   */
  @OrderColumn(name = "line_idx")
  private List<OrderLine> orderLines;

  @Column(name = "total_amounts")
  private Money totalAmounts;

  @Embedded
  private ShippingInfo shippingInfo;

  @Column
  @Enumerated(EnumType.STRING) // Enum 문자열 그대로 DB에 저장
  private OrderState state;

  @Temporal(TemporalType.TIMESTAMP) // TIMESTAMP 형식으로 DB에 저장
  private Date orderDate;

  public Order(OrderNo number, Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo, OrderState state) {
    setNumber(number);
    setOrderer(orderer);
    setOrderLines(orderLines);
    setShippingInfo(shippingInfo);
    this.state = state;
  }

  private void setNumber(OrderNo number) {
    if (number == null) throw new IllegalArgumentException("no number");
    this.number = number;
  }

  private void setOrderer(Orderer orderer) {
    if (orderer == null) throw new IllegalArgumentException("no orderer");
    this.orderer = orderer;
  }

  private void setOrderLines(List<OrderLine> orderLines) {
    verifyAtLeastOneOrMoreOrderLines(orderLines);
    this.orderLines = orderLines;
    calculateTotalAmounts();
  }

  private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
    if (orderLines == null || orderLines.isEmpty())
      throw new IllegalArgumentException("no OrderLine");
  }

  private void calculateTotalAmounts() {
    this.totalAmounts = new Money(orderLines.stream()
        .mapToInt(x -> x.getAmounts().getValue()).sum());
  }

  private void setShippingInfo(ShippingInfo shippingInfo) {
    if (shippingInfo == null) throw new IllegalArgumentException("no shipping info");
    this.shippingInfo = shippingInfo;
  }

  public void changeShippingInfo(ShippingInfo shippingInfo) {
    verifyNotYetShipped();
  }

  private void verifyNotYetShipped() {
    if (!isNotYetShipped())
      throw new AlreadyShippedException();
  }

  private boolean isNotYetShipped() {
    return state == PAYMENT_WAITING || state == PREPARING;
  }

}


