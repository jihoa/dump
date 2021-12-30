package org.springframework.samples.petclinic.product;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
		//then
		mockMvc.perform(get("/api/v1/product-api/product/" + productId)).andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").exists());
	}

	@Test
	@DisplayName("데이터 생성 테스트")
	public void saveProductTest() throws Exception {

		//given
		given(productService.saveProduct("12315", "pen", 2000, 3000)).willReturn(new ProductDto("12315", "pen", 2000, 3000));

		//when
		ProductDto productDto = ProductDto.builder().productId("12315").productName("pen").productPrice(2000).productStock(3000).build();

		Gson gson = new Gson();
		String content = gson.toJson(productDto);


		//then
		mockMvc.perform(post("/api/v1/product-api/product").content(content).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").exists());



		verify(productService).saveProduct("12315", "pen", 2000, 3000);


	}
}
