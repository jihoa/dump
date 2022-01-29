package org.springframework.samples.petclinic.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// ProductController에서 잡고 있는 Bean 객체에 대해 Mock 형태의 객체를 생성해줌
	@MockBean
	ProductService productService;


	@Test
	public void whenUserControllerInjected_thenNotNull()  {
		assertThat(ProductController.class).isNotNull();
		assertThat(productService).isNotNull();
	}

//	@Test
//	@DisplayName("Product 데이터 리스트 가져오기 테스트")
//	void getProductsTest() throws Exception {
//		mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//	}

	// http://localhost:8080/api/v1/product-api/product/{productId}
	@Test
	@DisplayName("Product 데이터 가져오기 테스트")
	void getProductTest() throws Exception {

		// given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드
		given(productService.getProduct("12315")).willReturn(new ProductDto("12315", "pen", 5000, 2000));

		String productId = "12315";

		// andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드
		mockMvc.perform(get("/api/v1/product-api/product/" + productId)).andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").exists()) // json path의 depth가 깊어지면 .을 추가하여 탐색할 수 있음 (ex : $.productId.productIdName)
			.andExpect(jsonPath("$.productName").exists()).andExpect(jsonPath("$.productPrice").exists())
			.andExpect(jsonPath("$.productStock").exists())
			//.andExpect(jsonPath("$.test").exists())
			.andDo(print());

		// verify : 해당 객체의 메소드가 실행되었는지 체크해줌
		verify(productService).getProduct("12315");
		//verify(productService).getProduct("test");
	}


	// http://localhost:8080/api/v1/product-api/product
	@Test
	@DisplayName("Product 데이터 생성 테스트")
	void 		createProductTest() throws Exception {
		//Mock 객체에서 특정 메소드가 실행되는 경우 실제 Return을 줄 수 없기 때문에 아래와 같이 가정 사항을 만들어줌
		given(productService.saveProduct("15871", "pen", 5000, 2000)).willReturn(
			new ProductDto("15871", "pen", 5000, 2000));

		ProductDto productDto = ProductDto.builder().productId("15871").productName("pen")
			.productPrice(5000).productStock(2000).build();
		Gson gson = new Gson();
		String content = gson.toJson(productDto);

		// 아래 코드로 json 형태 변경 작업을 대체할 수 있음
		String json = new ObjectMapper().writeValueAsString(productDto);

		mockMvc.perform(
				post("/api/v1/product-api/product")
					.content(content)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").exists())
			.andExpect(jsonPath("$.productName").exists())
			.andExpect(jsonPath("$.productPrice").exists())
			.andExpect(jsonPath("$.productStock").exists())
			.andDo(print());

		verify(productService).saveProduct("15871", "pen", 5000, 2000);
	}




	// http://localhost:8080/api/v1/product-api/product
	@Test
	@DisplayName("Product Validation 테스트")
	void createProductValid() throws Exception {
		//Mock 객체에서 특정 메소드가 실행되는 경우 실제 Return을 줄 수 없기 때문에 아래와 같이 가정 사항을 만들어줌
//		given(productService.saveProduct("15871", "pen", 5000, 2000)).willReturn(
//			new ProductDto("15871", "pen", 5000, 2000));

		ProductDto productDto = ProductDto.builder().productId("15871").productName("")
			.productPrice(5000).productStock(2000).build();
		Gson gson = new Gson();
		String content = gson.toJson(productDto);

		// 아래 코드로 json 형태 변경 작업을 대체할 수 있음
		String json = new ObjectMapper().writeValueAsString(productDto);

		mockMvc.perform(
				post("/api/v1/product-api/product")
					.content(content)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
//			.andExpect(jsonPath("$.productId").exists())
			.andExpect(jsonPath("$.productName", Is.is("필수입력사항입니다.")))
//			.andExpect(jsonPath("$.productPrice").exists())
//			.andExpect(jsonPath("$.productStock").exists())
			.andDo(print());

//		verify(productService).saveProduct("15871", "pen", 5000, 2000);
	}


}
