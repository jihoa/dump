package org.springframework.samples.petclinic.hello.item;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.hello.item.entity.Book;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ItemServiceTest {


	@Autowired
	ItemService itemService;
	@Autowired ItemRepository itemRepository;
	@Autowired
	EntityManager em;

	@Test
	public void 상품저장() throws Exception {
		//given
		Book book = new Book();
		book.setAuthor("지토");
		book.setName("스프링 JPA");
		book.setPrice(10000);
		book.setStockQuantity(100);

		//when
		itemService.saveItem(book); // 저장

		//then
		Assertions.assertEquals(book, itemService.findOne((long) 1));
	}

	@Test
	@Disabled
	public void 이미존재하는상품은merge한다() throws Exception {
		//given
		Book book = new Book();
		book.setAuthor("지토");
		book.setName("스프링 JPA");
		book.setPrice(10000);
		book.setStockQuantity(100);

		itemService.saveItem(book); // 저장

		//when
		book.removeStock(1);
		itemService.saveItem(book); // 저장

		//then
		Assertions.assertEquals(book.getStockQuantity(), itemService.findOne((long) 1).getStockQuantity());
	}



}
