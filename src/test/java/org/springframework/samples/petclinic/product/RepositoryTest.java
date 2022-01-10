package org.springframework.samples.petclinic.product;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class RepositoryTest {

	@Autowired
	ProductRepository productRepository;

	@Test
	public void basicCRUDTest() {

		/* create */
		// given
		ProductEntity productEntity = ProductEntity.builder()
			.productId("123")
			.productName("pen")
			.productPrice(2000)
			.productStock(3000)
			.build();

		// when
		ProductEntity savedEntity = productRepository.save(productEntity);


		//then
		Assertions.assertThat(savedEntity.getProductId()).isEqualTo(productEntity.getProductId());
		Assertions.assertThat(savedEntity.getProductName()).isEqualTo(productEntity.getProductName());


		//when
		ProductEntity selectedEntity = productRepository.getById("123");

		//then
		Assertions.assertThat(selectedEntity.getProductId()).isEqualTo(productEntity.getProductId());

	}

}
