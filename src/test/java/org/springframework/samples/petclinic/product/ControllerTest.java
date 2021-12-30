package org.springframework.samples.petclinic.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(ProductController.class)
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ProductService productService;

	@Test
	@DisplayName("productService 데이터 가져오기 테스트")
	public void getProductTest() throws Exception {

		//given
		given(productService.getProduct("12315")).willReturn(new ProductDto("12315", "pen", 2000, 3000));
		//when
		String productId = "12315";
		mockMvc.perform(get("/api/v1/product-api/product/" + productId)).andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").exists());
		//then
	}
}
