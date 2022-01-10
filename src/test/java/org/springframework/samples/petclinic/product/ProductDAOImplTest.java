package org.springframework.samples.petclinic.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.product.exception.ProductExistedException;
import org.springframework.samples.petclinic.product.exception.ProductNotFoundException;
import org.springframework.samples.petclinic.restful.Member;
import org.springframework.samples.petclinic.restful.MemberRepository;
import org.springframework.samples.petclinic.restful.exception.MemberExistedException;
import org.springframework.samples.petclinic.restful.exception.MemberNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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


	@Test
	@DisplayName("상품 등록 실패")
	void whenAddFailure_thenThrowExistedException() {
		// given
		final ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId("1");
		productEntity.setProductName("name");
		productEntity.setProductPrice(2000);
		productEntity.setProductStock(3000);
		given(productRepository.findById(productEntity.getProductId())).willReturn(Optional.of(productEntity));//pk값 중복 삽입 조건 처리

		log.info("{}", productEntity.getProductId());
		log.info("{}", productEntity.getProductName());
		log.info("{}", productEntity.getProductPrice());
		log.info("{}", productEntity.getProductStock());
		// when
		//when().thenThrow()를 사용하면 지정한 조건의 메서드가 호출될때 예외를 발생시킨다.
		// then
		assertThatThrownBy(() -> productDAO.saveProduct(productEntity)).isInstanceOf(ProductExistedException.class)
			.hasMessage("product is already joined : " + productEntity.getProductId());
		verify(productRepository, never()).save(any(ProductEntity.class));
		// verify() 두 번째 인자 값
		// times(int) - 지정한 회수 만큼 호출되었는 지 검증.
		// never() - 호출되지 않았는지 여부 검증.
		// atLeastOnce() - 최소한 한번은 호출되었는 지 검증.
		// atLeast(int) - 최소한 지정한 회수 만큼 호출되었는 지 검증.
		// atMost(int) - 최대 지정한 회수 만큼 호출되었는 지 검증.
	}


	@Test
	@DisplayName("ID에 맞는 상품 정보 조회 성공")
	void whenGetByIdSuccess_thenCorrectResponse() {
		// given
		final ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId("1");
		productEntity.setProductName("name");
		productEntity.setProductPrice(2000);
		productEntity.setProductStock(3000);
		given(productRepository.findById(productEntity.getProductId())).willReturn(Optional.of(productEntity));

		log.info("{}", productEntity.getProductId());
		log.info("{}", productEntity.getProductName());
		log.info("{}", productEntity.getProductPrice());
		log.info("{}", productEntity.getProductStock());

		// when
		ProductEntity getProduct = productDAO.getProduct(productEntity.getProductId());

		// then
		assertThat(productEntity).isEqualTo(getProduct);
//		verify(productRepository, times(1)).findById(anyString());

	}

	@Test
	@DisplayName("ID에 해당하는 사용자가 존재하지 않을 때")
	void whenGetMemberByIdFailed_thenThrowMemberNotFoundException() {
		// given
		given(productRepository.findById(anyString())).willReturn(Optional.empty());

		// when
		//when().thenThrow()를 사용하면 지정한 조건의 메서드가 호출될때 예외를 발생시킨다.
		//when(memberService.get(anyLong())).thenThrow(MemberNotFoundException.class);

		// then
		assertThatThrownBy(() -> productDAO.getProduct(anyString())).isInstanceOf(ProductNotFoundException.class)
			.hasMessage("[findById] not found : ");

		verify(productRepository, times(1)).findById(anyString());
	}


	@Test
	@DisplayName("상품 정보 수정 성공")
	void whenUpdateMemberSuccess_thenCorrectResponse() {
		// given
		final ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId("1");
		productEntity.setProductName("name");
		productEntity.setProductPrice(2000);
		productEntity.setProductStock(3000);
		given(productRepository.findById(productEntity.getProductId())).willReturn(Optional.ofNullable(productEntity));
		given(productRepository.save(productEntity)).willReturn(productEntity);

		log.info("{}", productEntity.getProductId());
		log.info("{}", productEntity.getProductName());
		log.info("{}", productEntity.getProductPrice());
		log.info("{}", productEntity.getProductStock());

		ProductEntity updateProduct = productDAO.modifyProduct(productEntity);

		assertThat(updateProduct).isNotNull();
		verify(productRepository, atLeastOnce()).findById(anyString());
		verify(productRepository, times(1)).save(any(ProductEntity.class));
	}

	@Test
	@DisplayName("해당 상품이 없어 예외 발생")
	void whenUpdateProductFailed_thenThrowProductNotFoundException() {

		// given
		final ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId("1");
		productEntity.setProductName("name");
		productEntity.setProductPrice(2000);
		productEntity.setProductStock(3000);
		given(productRepository.findById(productEntity.getProductId())).willReturn(Optional.empty());

		assertThatThrownBy(() -> productDAO.getProduct("1")).isInstanceOf(ProductNotFoundException.class).hasMessage("[findById] not found : 1");
		;
	}


	@Test
	@DisplayName("상품 삭제 성공")
	void whenDeleteMemberSuccess_thenCorrectResponse() {
		// given
		final String productId = "1";

		// when
		productDAO.deleteProduct(productId);

		// then
		verify(productRepository, times(1)).deleteById(anyString());
		verify(productRepository, times(1)).findById(anyString());

	}

	@Test
	@DisplayName("상품 전체 목록 조회 성공")
	void whenFindByAllListSuccess_thenCorrectResponse() {
		// given
		ArrayList<ProductEntity> productEntityList = new ArrayList<>();

		ProductEntity productEntity = new ProductEntity();

		productEntity.setProductId("1");
		productEntity.setProductName("name");
		productEntity.setProductPrice(2000);
		productEntity.setProductStock(3000);

		productEntityList.add(productEntity);

		ProductEntity productEntity1 = new ProductEntity();

		productEntity1.setProductId("2");
		productEntity1.setProductName("name2");
		productEntity1.setProductPrice(20000);
		productEntity1.setProductStock(30000);

		productEntityList.add(productEntity1);

		given(productRepository.findAll()).willReturn(productEntityList);


		//when
		List<ProductEntity> resultList = productDAO.getProducts();

		assertThat(resultList).isNotNull();
		assertThat(resultList.size()).isEqualTo(productEntityList.size());
		assertThat(resultList.get(0).getProductName()).isEqualTo(productEntityList.get(0).getProductName());
	}


		@Test
	@DisplayName("이름으로 상품 정보 조회 성공")
	void whenFindByUsernameSuccess_thenCorrectResponse() {
		// given
		final ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId("1");
		productEntity.setProductName("name");
		productEntity.setProductPrice(2000);
		productEntity.setProductStock(3000);
		given(productRepository.findByProductName(productEntity.getProductName())).willReturn(Optional.of(productEntity));

		//when
		ProductEntity findByProductName = productDAO.getProductName(productEntity.getProductName());

		assertThat(productEntity).isEqualTo(findByProductName);
		verify(productRepository).findByProductName(any(String.class));
	}

}
