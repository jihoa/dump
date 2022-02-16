package org.springframework.samples.petclinic.hello;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.hello.item.ItemRepository;
import org.springframework.samples.petclinic.hello.item.entity.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

    public List<Order> findOrders(OrderSearch orderSearch) {
		return orderRepository.findAllByCriteria(orderSearch);
    }

	@Transactional
	public Long order(Long memberId, Long itemId, int count) {

		//엔티티 조회
		Member member = memberRepository.findById(memberId).get();
		Item item = itemRepository.findOne(itemId);

		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());

		//주문상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		//주문생성
		Order order = Order.createOrder(member, delivery, orderItem);

		//주문저장
		orderRepository.save(order);

		return order.getId();
	}

	@Transactional
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findOne(orderId);

		order.cancel();
	}


}
