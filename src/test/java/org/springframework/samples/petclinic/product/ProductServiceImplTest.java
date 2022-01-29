package org.springframework.samples.petclinic.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {ProductDataHandlerImpl.class, ProductServiceImpl.class})
//@ExtendWith(SpringExtension.class)
//@Import({ProductDataHandlerImpl.class, ProductServiceImpl.class})
class ProductServiceImplTest {

	@MockBean
	ProductDataHandler productDataHandler;
	@Autowired
	ProductService productService;


	@Test
	public void getProductTest() {
		//given
		Mockito.when(productDataHandler.getProductEntity("123"))
			.thenReturn(new ProductEntity("123", "pen", 2000, 3000));

		//when //verify
		ProductDto productDto = productService.getProduct("123");

		assertThat(productDto.getProductId()).isEqualTo("123");
		assertThat(productDto.getProductName()).isEqualTo("pen");
		assertThat(productDto.getProductPrice()).isEqualTo(2000);
		assertThat(productDto.getProductStock()).isEqualTo(3000);


		verify(productDataHandler).getProductEntity("123");
	}

	@Test
	public void saveProductTest() {
		//given
		Mockito.when(productDataHandler.saveProductEntity("123", "pen", 2000, 3000))
			.thenReturn(new ProductEntity("123", "pen", 2000, 3000));

		ProductDto productDto = productService.saveProduct("123", "pen", 2000, 3000);

		Assertions.assertEquals(productDto.getProductId(), "123");
		Assertions.assertEquals(productDto.getProductName(), "pen");
		Assertions.assertEquals(productDto.getProductPrice(), 2000);
		Assertions.assertEquals(productDto.getProductStock(), 3000);

		verify(productDataHandler).saveProductEntity("123", "pen", 2000, 3000);
	}




}
