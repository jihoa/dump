package org.springframework.samples.petclinic.hello;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;




	public Long save(Order order) {

		em.persist(order);

		return order.getId();

	}


    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Order> o = cq.from(Order.class);
		Join<Order, Member> m = o.join("member", JoinType.INNER);

		List<Object> criteria = new ArrayList<>();

		if (orderSearch.getOrderStatus() != null) {
			Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
			criteria.add(status);
		}

		if (orderSearch.getMemberName() != null) {
			Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
			criteria.add(name);
		}

		cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);

		return query.getResultList();
    }

	public Order findOne(Long orderId) {
		return em.find(Order.class, orderId);
	}


//	public List<Order> findAllByString(OrderSearch orderSearch) {
//		String jpql = "select o from Order o join o.member m";
//
//		boolean isFirstCondition = true;
//
//		if (orderSearch.getOrderStatus() != null) {
//			jpql += " where o.status = :status";
//		}
//
//		if (StringUtils.hasText(orderSearch.getMemberName())) {
//			if (isFirstCondition) {
//				jpql += " where";
//				isFirstCondition = false;
//			} else {
//				jpql += " and";
//			}
//
//			jpql += " m.name like :name";
//		}
//
//		TypedQuery<Order> query = em.createQuery(jpql, Order.class)
//			.setMaxResults(1000);
//
//		if (orderSearch.getOrderStatus() != null) {
//			query.setParameter("status", orderSearch.getOrderStatus());
//		}
//
//		if (StringUtils.hasText(orderSearch.getMemberName())) {
//			query.setParameter("name", orderSearch.getMemberName());
//		}
//
//		return query.getResultList();
//	}


}
