package org.springframework.samples.petclinic.hello;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
도메인 모델 패턴 : 엔티티가 비즈니스 로직을 가지고 객체 지향의 특성을 적극 활용하는 것.

트랜잭션 스크립트 패턴 : 서비스 계층에서 대부분의 비즈니스 로직 처리
 */

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")//fk
	private Member member;


	//cascade는 private owner 인 경우 사용.
	//두개의 엔티티의 라이프 사이클이 동일한 경우 사용
	//orderItems와 함께 생성되고 삭제된다.
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();


	//1:1관계에서 fk를 선택시 누굴 통해 접근 많이 하는지가 중요하다.
	//Order을 통해 Delivery 접근 많이 하니
	//fk는 Order
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id") //연관관계의 주인에게 JoinColumn을 쓰자.
	private Delivery delivery;

	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;


	//연관관계 편의 메서드.
	//양쪽이 있는 경우 컨트롤 하는쪽이 들고 있는게 좋음.
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}

	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		Order order = new Order();

		order.setMember(member);
		order.setDelivery(delivery);

		for (OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}

		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());

		return order;
	}

	public void cancel() {
		if (delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("이미 배송완료된 상품은 취소 불가");
		}
		 this.setStatus(OrderStatus.CANCEL);

		for (OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}

	public int getTotalPrice() {
		int totalPrice = 0;

		for (OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}

		return totalPrice;
	}

}
