package io.wisoft.myshop.order.infra.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.wisoft.myshop.member.domain.MemberId;
import io.wisoft.myshop.order.command.domain.Order;
import io.wisoft.myshop.order.command.domain.OrderNo;
import io.wisoft.myshop.order.command.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static io.wisoft.myshop.order.command.domain.QOrder.order;


@Repository
public class JpaOrderRepository implements OrderRepository {

  @PersistenceContext
  private EntityManager entityManager;

  private final JPAQueryFactory queryFactory;

  public JpaOrderRepository(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  @Override
  public Order findById(final OrderNo id) {
    return entityManager.find(Order.class, id);
  }

  @Override
  public void save(final Order order) {
    entityManager.persist(order);
  }

  @Override
  public void remove(final Order order) {
    entityManager.remove(order);
  }

  @Override
  public List<Order> findAll() {
    return entityManager.createQuery("select o from Order o", Order.class).getResultList();
  }

//  @Override
//  public List<Order> findByOrdererId(final String ordererId, final int startRow, final int fetchSize) {
//    TypedQuery<Order> query = entityManager.createQuery(
//        "select o from Order o " +
//            "where o.orderer.memberId.id = :ordererId " +
//            "order by o.number.number desc",
//        Order.class);
//    query.setParameter("ordererId", ordererId);
//    query.setFirstResult(startRow);
//    query.setMaxResults(fetchSize);
//    return query.getResultList();
//  }

  @Override
  public List<Order> findByOrdererId(final String ordererId, final int startRow, final int fetchSize) {
    return queryFactory.selectFrom(order)
        .where(order.orderer.memberId.eq(new MemberId(ordererId)))
        .offset(startRow)
        .limit(fetchSize)
        .orderBy(order.number.number.desc())
        .fetch();
  }


}
