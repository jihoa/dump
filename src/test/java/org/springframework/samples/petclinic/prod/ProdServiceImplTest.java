package org.springframework.samples.petclinic.prod;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.samples.petclinic.product.ProductEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@Import({ProdDataHandlerImpl.class, ProdServiceImpl.class})
class ProdServiceImplTest {
	
	@MockBean
	ProdDataHandlerImpl prodDataHandlerImpl;

	@Autowired
	ProdServiceImpl prodServiceImpl;

	@Test
	public void getProductTest() {

		//given
		Mockito.when(prodDataHandlerImpl.getProductEntity("123")).thenReturn(new ProductEntity("123", "pen", 2000, 3000));


	}
}
