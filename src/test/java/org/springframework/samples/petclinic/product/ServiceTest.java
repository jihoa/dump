package org.springframework.samples.petclinic.product;


import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest(classes={ProductDataHandlerImpl.class, ProductServiceImpl.class})
public class ServiceTest {

	@Autowired
	ProductService productService;

	@MockBean
	ProductDataHandler productDataHandler;


	@Test
	@DisplayName("productId로 조회")
	public void getProductTest() {
		//given
		Mockito.when(productDataHandler.getProductEntity("123")).thenReturn(new ProductEntity("123", "pen", 2000, 3000));

		//when
		ProductDto productDto = productService.getProduct("123");
		//then
		Assertions.assertThat(productDto.getProductId()).isEqualTo("123");
		Assertions.assertThat(productDto.getProductName()).isEqualTo("pen");

		//verify
		verify(productDataHandler).getProductEntity("123");
	}

	@Test
	@DisplayName("product Save 하기")
	public void saveProductTest() {
		Mockito.when(productDataHandler.saveProductEntity("123", "pen", 2000, 3000)).thenReturn(new ProductEntity("123","pen",2000,3000));

		ProductDto productDto = productService.saveProduct("123", "pen", 2000, 3000);

		Assertions.assertThat(productDto.getProductId()).isEqualTo("123");
	}
}
