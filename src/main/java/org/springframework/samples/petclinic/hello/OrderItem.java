package org.springframework.samples.petclinic.hello;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.hello.item.entity.Item;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {

	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	private int orderPrice;

	private int count;

	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);

		item.removeStock(count);

		return orderItem;
	}

	public void cancel() {
		item.addStock(count);
	}


	public int getTotalPrice() {
		return getOrderPrice() * getCount();
	}
}
