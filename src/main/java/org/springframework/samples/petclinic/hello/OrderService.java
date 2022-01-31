package org.springframework.samples.petclinic.hello;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.hello.item.ItemRepository;
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

	public Long order(Long memberId, Long itemId, int count) {

//		memberRepository.findOne(memberId);
		return null;
	}

//	@Transactional
//	public Long order(Long member)

}
