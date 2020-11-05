package io.wisoft.myshop.order.command.domain;

import java.util.List;

public interface OrderRepository {

  Order findById(OrderNo no);

  void save(Order order);

  void remove(Order order);

  List<Order> findAll();

  List<Order> findByOrdererId(String ordererId, int startRow, int fetchSize);

}
