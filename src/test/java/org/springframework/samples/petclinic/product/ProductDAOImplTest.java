package org.springframework.samples.petclinic.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.restful.Member;
import org.springframework.samples.petclinic.restful.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(MockitoExtension.class)
class ProductDAOImplTest {
	@InjectMocks
	ProductDAOImpl productDAO;
	@Mock
	private ProductRepository productRepository;

	@Test
	@DisplayName("ProductDAO 생성확인")
	void whenMemberRepositoryInjected_thenNotNull() {
		assertThat(productDAO).isNotNull();
		assertThat(productRepository).isNotNull();
	}


	@Test
	@DisplayName("상품 등록 성공")
	void whenAddSuccess_thenCorrectResponse() {
		// given
		final ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId("1");
		productEntity.setProductName("name");
		productEntity.setProductPrice(2000);
		productEntity.setProductStock(3000);
		given(productRepository.findById(productEntity.getProductId())).willReturn(Optional.empty());//pk값 중복 삽입 조건 처리
		given(productRepository.save(productEntity)).willReturn(productEntity);

		// when
		ProductEntity productEntity1 = productDAO.saveProduct(productEntity);

		// then
		assertThat(productEntity1).isNotNull();
		verify(productRepository).save(any(ProductEntity.class));
	}
}
