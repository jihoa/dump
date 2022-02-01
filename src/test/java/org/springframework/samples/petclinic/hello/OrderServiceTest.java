package org.springframework.samples.petclinic.hello;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.hello.item.entity.Book;
import org.springframework.samples.petclinic.hello.item.entity.Item;
import org.springframework.samples.petclinic.hello.item.entity.exception.NotEnoughStockException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;




	@Test
	@DisplayName("상품주문")
	public void productOrder() {
		Member member = createMember("회원1111111111111", "서울", "테헤란로", "12345");
		Item book = createBook("시골 JPA", 10000, 10);

		int orderCount = 2;

		//when
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

		//then
		Order getOrder = orderRepository.findOne(orderId);

		Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문 시 상태는 ORDER이다");
		Assertions.assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
		Assertions.assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
		Assertions.assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
	}

	@Test
	@DisplayName("재고수량초과")
	public void stockOver() {
		Member member = createMember("회원1111111111111", "서울", "테헤란로", "12345");
		Item item = createBook("시골 JPA", 10000, 10);

		int orderCount = 11;

		NotEnoughStockException e = assertThrows(NotEnoughStockException.class,
			() -> orderService.order(member.getId(), item.getId(), orderCount));

		assertThat(e.getMessage()).isEqualTo("need more stock");
	}

	@Test
	@DisplayName("주문취소")
	public void orderCancel() {
		Member member = createMember("회원1111111111111", "서울", "테헤란로", "12345");
		Item item = createBook("시골 JPA", 10000, 10);

		int orderCount = 2;

		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

		//when
		orderService.cancelOrder(orderId);

		//then
		Order getOrder = orderRepository.findOne(orderId);

		Assertions.assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소 시 상태는 CANCEL이다.");
		Assertions.assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
	}

	private Member createMember(String name, String city, String street, String zipcode) {
		Member member = new Member();

		member.setName(name);
		member.setAddress(new Address(city, street, zipcode));
		em.persist(member);
		return member;
	}

	private Item createBook(String name, int price, int stockQuantity) {
		Item book = new Book();

		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		return book;
	}

}
