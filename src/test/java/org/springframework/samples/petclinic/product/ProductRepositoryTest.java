package org.springframework.samples.petclinic.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.transaction.annotation.Propagation.*;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.samples.petclinic.restful.Member;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(value = "dev")
class ProductRepositoryTest {

	@Autowired
	ProductRepository productRepository;

	private ProductEntity getProduct(String id, int nameNumber, int price, int stock) {
		return new ProductEntity(id, "상품" + nameNumber, price, stock);
	}

	@BeforeEach
	void GenerateData() {
		int count = 1;
		productRepository.save(getProduct(Integer.toString(count), count++, 2000, 3000));
		productRepository.save(getProduct(Integer.toString(count), count++, 3000, 9000));
		productRepository.save(getProduct(Integer.toString(--count), count = count + 2, 1500, 200));
		productRepository.save(getProduct(Integer.toString(count), count++, 4000, 5000));
		productRepository.save(getProduct(Integer.toString(count), count++, 10000, 1500));
		productRepository.save(getProduct(Integer.toString(count), count++, 1000, 1000));
		productRepository.save(getProduct(Integer.toString(count), count++, 500, 10000));
		productRepository.save(getProduct(Integer.toString(count), count++, 8500, 3500));
		productRepository.save(getProduct(Integer.toString(count), count++, 7200, 2000));
		productRepository.save(getProduct(Integer.toString(count), count++, 5100, 1700));
	}

	@Test
	@DisplayName("Respository 생성확인")
	void whenProductRepositoryInjected_thenNotNull() {
		assertThat(productRepository).isNotNull();
	}


	@Test
	@DisplayName("생성한 findByUsername 메서드 정상 작동 확인")
	void whenFindByUsernameSuccess_thenCorrectResponse() {
		// given
		final ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId("1");
		productEntity.setProductName("name");
		productEntity.setProductPrice(2000);
		productEntity.setProductStock(3000);
		ProductEntity savedProduct = productRepository.save(productEntity);

		// when
		Optional<ProductEntity> findByProductName = productRepository.findByProductName(productEntity.getProductName());

		// then
		findByProductName.ifPresent(value -> assertThat(savedProduct.getProductName()).isEqualTo(value.getProductName()));
	}


	@Test
	@DisplayName("Optional.empty() 확인")
	void whenFindByUsernameFailure_thenCorrectResponse() {
		Optional<ProductEntity> findByProductName = productRepository.findByProductName("not exist product");
		assertThat(Optional.empty()).isEqualTo(findByProductName);
	}

	@Test
	public void queryTest() {
		List<ProductEntity> foundAll = productRepository.findAll();

		for (ProductEntity productEntity : foundAll) {
			System.out.println(productEntity.toString());
		}
		List<ProductEntity> foundProducts = productRepository.findByPriceBasis();

		for (ProductEntity productEntity : foundProducts) {
			System.out.println(productEntity);
		}
	}


//	/* 정렬과 페이징 */
//	@Test
//	void orderByTest() {
//		List<ProductEntity> foundAll = productRepository.findAll();
//		System.out.println("====↓↓ Test Data ↓↓====");
//		for (ProductEntity product : foundAll) {
//			System.out.println(product.toString());
//		}
//		System.out.println("====↑↑ Test Data ↑↑====");
//
//		List<ProductEntity> foundProducts = productRepository.findByNameContainingOrderByStockAsc("상품");
//		for(ProductEntity product : foundProducts){
//			System.out.println(product);
//		}
//
//		foundProducts = productRepository.findByNameContainingOrderByStockDesc("상품");
//		for(Product product : foundProducts){
//			System.out.println(product);
//		}
//
//		foundProducts = productRepository.findByNameContainingOrderByPriceAscStockDesc("상품");
//		for(Product product : foundProducts){
//			System.out.println(product);
//		}
//	}
//
//	@Test
//	void multiOrderByTest() {
//		List<Product> foundAll = productRepository.findAll();
//		System.out.println("====↓↓ Test Data ↓↓====");
//		for (Product product : foundAll) {
//			System.out.println(product.toString());
//		}
//		System.out.println("====↑↑ Test Data ↑↑====");
//
//		List<Product> foundProducts = productRepository.findByNameContainingOrderByPriceAscStockDesc("상품");
//		for(Product product : foundProducts){
//			System.out.println(product);
//		}
//	}
//
//	@Test
//	void orderByWithParameterTest() {
//		List<Product> foundAll = productRepository.findAll();
//		System.out.println("====↓↓ Test Data ↓↓====");
//		for (Product product : foundAll) {
//			System.out.println(product.toString());
//		}
//		System.out.println("====↑↑ Test Data ↑↑====");
//
//		List<Product> foundProducts = productRepository.findByNameContaining("상품", Sort.by(Order.asc("price")));
//		for(Product product : foundProducts){
//			System.out.println(product);
//		}
//
//		foundProducts = productRepository.findByNameContaining("상품", Sort.by(Order.asc("price"), Order.asc("stock")));
//		for(Product product : foundProducts){
//			System.out.println(product);
//		}
//	}
//
//	@Test
//	void pagingTest() {
//		List<Product> foundAll = productRepository.findAll();
//		System.out.println("====↓↓ Test Data ↓↓====");
//		for (Product product : foundAll) {
//			System.out.println(product.toString());
//		}
//		System.out.println("====↑↑ Test Data ↑↑====");
//
//		List<Product> foundProducts = productRepository.findByPriceGreaterThan(200, PageRequest.of(0, 2));
//		for(Product product : foundProducts){
//			System.out.println(product);
//		}
//
//		foundProducts = productRepository.findByPriceGreaterThan(200, PageRequest.of(2, 2));
//		for(Product product : foundProducts){
//			System.out.println(product);
//		}
//	}

	//Paging 끝.



	@Test
	void findTest() {
		List<ProductEntity> foundAll = productRepository.findAll();
		System.out.println("====↓↓ Test Data ↓↓====");
		for (ProductEntity productEntity : foundAll) {
			System.out.println(productEntity.toString());
		}
		System.out.println("====↑↑ Test Data ↑↑====");

//		List<ProductEntity> foundEntities = productRepository.findByProductName("상품4");

//		for(ProductEntity productEntity : foundEntities){
//			System.out.println(productEntity.toString());
//		}

		List<ProductEntity> queryEntities = productRepository.queryByProductName("상품4");

		for (ProductEntity productEntity : queryEntities) {
			System.out.println(productEntity.toString());
		}
	}




	@Test
	void existTest() {
		List<ProductEntity> foundAll = productRepository.findAll();
		System.out.println("====↓↓ Test Data ↓↓====");
		for (ProductEntity productEntity : foundAll) {
			System.out.println(productEntity.toString());
		}
		System.out.println("====↑↑ Test Data ↑↑====");

		System.out.println(productRepository.existsByProductName("상품4"));
		System.out.println(productRepository.existsByProductName("상품2"));// limit ?
	}

	@Test
	void countTest() {
		List<ProductEntity> foundAll = productRepository.findAll();
		System.out.println("====↓↓ Test Data ↓↓====");
		for (ProductEntity productEntity : foundAll) {
			System.out.println(productEntity.toString());
		}
		System.out.println("====↑↑ Test Data ↑↑====");

		System.out.println(productRepository.countByProductName("상품4"));
	}

	@Test
	@Transactional
	void deleteTest() {
		System.out.println("before : " + productRepository.count());

		productRepository.deleteByProductId("1");
		productRepository.removeByProductId("9");

		System.out.println("After : " + productRepository.count());
	}

	@Test
	void topTest() {
		productRepository.save(getProduct("109", 123, 1500, 5000));
		productRepository.save(getProduct("101", 123, 2500, 5000));
		productRepository.save(getProduct("102", 123, 3500, 5000));
		productRepository.save(getProduct("103", 123, 4500, 5000));
		productRepository.save(getProduct("104", 123, 1000, 5000));
		productRepository.save(getProduct("105", 123, 2000, 5000));
		productRepository.save(getProduct("106", 123, 3000, 5000));
		productRepository.save(getProduct("107", 123, 4000, 5000));

		List<ProductEntity> foundEntities = productRepository.findFirst5ByProductName("상품123");
		for (ProductEntity productEntity : foundEntities) {
			System.out.println(productEntity.toString());
		}

		List<ProductEntity> foundEntities2 = productRepository.findTop3ByProductName("상품123");
		for (ProductEntity productEntity : foundEntities2) {
			System.out.println(productEntity.toString());
		}
	}

	/* ↓↓ 조건자 키워드 테스트 ↓↓ */

	@Test
	void isEqualsTest() {
		List<ProductEntity> foundAll = productRepository.findAll();
		System.out.println("====↓↓ Test Data ↓↓====");
		for (ProductEntity productEntity : foundAll) {
			System.out.println(productEntity.toString());
		}
		System.out.println("====↑↑ Test Data ↑↑====");

		System.out.println(productRepository.findByProductIdIs("1"));
		System.out.println(productRepository.findByProductIdEquals("1"));
	}

	@Test
	void notTest() {
		List<ProductEntity> foundAll = productRepository.findAll();
		System.out.println("====↓↓ Test Data ↓↓====");
		for (ProductEntity productEntity : foundAll) {
			System.out.println(productEntity.toString());
		}
		System.out.println("====↑↑ Test Data ↑↑====");

		List<ProductEntity> foundEntities = productRepository.findByProductIdNot("1");
		for (ProductEntity productEntity : foundEntities) {
			System.out.println(productEntity);
		}
		//System.out.println(productRepository.findByProductIdNot("1"));
		System.out.println(productRepository.findByProductIdIsNot("1"));
	}

	@Test
	void nullTest() {
		List<ProductEntity> foundAll = productRepository.findAll();
		System.out.println("====↓↓ Test Data ↓↓====");
		for (ProductEntity productEntity : foundAll) {
			System.out.println(productEntity.toString());
		}
		System.out.println("====↑↑ Test Data ↑↑====");

		System.out.println(productRepository.findByProductStockIsNull());
		System.out.println(productRepository.findByProductStockIsNotNull());
	}

	@Test
	void andTest() {
		List<ProductEntity> foundAll = productRepository.findAll();
		System.out.println("====↓↓ Test Data ↓↓====");
		for (ProductEntity productEntity : foundAll) {
			System.out.println(productEntity.toString());
		}
		System.out.println("====↑↑ Test Data ↑↑====");

		System.out.println(productRepository.findTopByProductIdAndProductName("1", "상품1"));
	}

	@Test
	void greaterTest() {
		List<ProductEntity> foundAll = productRepository.findAll();
		System.out.println("====↓↓ Test Data ↓↓====");
		for (ProductEntity productEntity : foundAll) {
			System.out.println(productEntity.toString());
		}
		System.out.println("====↑↑ Test Data ↑↑====");

		List<ProductEntity> productEntities = productRepository.findByProductPriceGreaterThan(5000);

		for (ProductEntity productEntity : productEntities) {
			System.out.println(productEntity);
		}
	}

	@Test
	void containTest() {
		List<ProductEntity> foundAll = productRepository.findAll();
		System.out.println("====↓↓ Test Data ↓↓====");
		for (ProductEntity productEntity : foundAll) {
			System.out.println(productEntity.toString());
		}
		System.out.println("====↑↑ Test Data ↑↑====");

		System.out.println(productRepository.findByProductNameContaining("상품1"));
	}

	@Test
	public void basicCRUDTest() {
		/* create */
		// given
		ProductEntity productEntity = ProductEntity.builder().productId("testProduct").productName("testP").productPrice(1000).productStock(500)
			.build();

		// when
		ProductEntity savedEntity = productRepository.save(productEntity);

		// then
		Assertions.assertThat(savedEntity.getProductId()).isEqualTo(productEntity.getProductId());
		Assertions.assertThat(savedEntity.getProductName()).isEqualTo(productEntity.getProductName());
		Assertions.assertThat(savedEntity.getProductPrice()).isEqualTo(productEntity.getProductPrice());
		Assertions.assertThat(savedEntity.getProductStock()).isEqualTo(productEntity.getProductStock());

		/* read */
		// when
		ProductEntity selectedEntity = productRepository.findById("testProduct").orElseThrow(() -> new RuntimeException());

		// then
		Assertions.assertThat(selectedEntity.getProductId()).isEqualTo(productEntity.getProductId());
		Assertions.assertThat(selectedEntity.getProductName()).isEqualTo(productEntity.getProductName());
		Assertions.assertThat(selectedEntity.getProductPrice()).isEqualTo(productEntity.getProductPrice());
		Assertions.assertThat(selectedEntity.getProductStock()).isEqualTo(productEntity.getProductStock());
	}

}
