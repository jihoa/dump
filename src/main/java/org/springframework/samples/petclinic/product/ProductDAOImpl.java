package org.springframework.samples.petclinic.product;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.product.exception.ProductExistedException;
import org.springframework.samples.petclinic.product.exception.ProductNotFoundException;
import org.springframework.samples.petclinic.restful.exception.MemberExistedException;
import org.springframework.samples.petclinic.restful.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductDAOImpl implements ProductDAO {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductDAOImpl.class);

	static final boolean DELETE_SUCCESS = true;
	static final boolean DELETE_FAILED = false;

    ProductRepository productRepository;

    @Autowired
    public ProductDAOImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity saveProduct(ProductEntity productEntity) {
		if (productRepository.findById(productEntity.getProductId()).isPresent()) {
			throw new ProductExistedException("product is already joined : " + productEntity.getProductId());
		} else {
			LOGGER.info("[saveProduct] product 정보 저장. productId : {}", productEntity.getProductId());
			ProductEntity productEntity1 = productRepository.save(productEntity);
			LOGGER.info("[saveProduct] product 정보 저장완료. productId : {}", productEntity1.getProductId());
			return productEntity1;
		}
    }

//	public ProductEntity get(String productId) {
//
//
//		LOGGER.info("[getProduct] product 정보 요청. productId : {}", productId);
//		ProductEntity productEntity = productRepository.findById(productId)
//			.orElseThrow(() -> new ProductNotFoundException("not found : " + productId));
//
//
//		LOGGER.info("[getProduct] product 정보 요청 완료. productId : {}", productEntity.getProductId());
////		LOGGER.error("[getProduct] product 정보 요청 ?. productName : {}", productEntity.getProductName());
////		LOGGER.error("[getProduct] product 정보 요청 ?. productPrice : {}", productEntity.getProductPrice());
////		LOGGER.error("[getProduct] product 정보 요청 ?. productStock : {}", productEntity.getProductStock());
//		return productEntity;
//	}

	public ProductEntity getProductName(String productName) {

		ProductEntity productEntity = productRepository.findByProductName(productName)
			.orElseThrow(() -> new ProductNotFoundException("[findById] not found : " + productName));

		return productEntity;

	}

	@Override
    public ProductEntity getProduct(String productId) {


//		productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("not found : "+productId));


//        LOGGER.info("[getProduct] product 정보 요청. productId : {}", productId);
//        ProductEntity productEntity = productRepository.getById(productId);
		LOGGER.info("[getProduct] product 정보 요청. productId : {}", productId);
		ProductEntity productEntity = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException("[findById] not found : " + productId));


        LOGGER.info("[getProduct] product 정보 요청 완료. productId : {}", productEntity.getProductId());
//		LOGGER.error("[getProduct] product 정보 요청 ?. productName : {}", productEntity.getProductName());
//		LOGGER.error("[getProduct] product 정보 요청 ?. productPrice : {}", productEntity.getProductPrice());
//		LOGGER.error("[getProduct] product 정보 요청 ?. productStock : {}", productEntity.getProductStock());
        return productEntity;
    }

	@Override
	public boolean deleteProduct(String productId) {
		productRepository.deleteById(productId);
		if (!productRepository.findById(productId).isPresent()) {
			return DELETE_SUCCESS;
		} else {
			return DELETE_FAILED;
		}
	}

	@Override
	public List<ProductEntity> getProducts() {
		return productRepository.findAll();
	}

	@Override
	public ProductEntity modifyProduct(final ProductEntity productEntity) {
		if (productRepository.findById(productEntity.getProductId()).isPresent()) {
			productRepository.save(productEntity);
		} else {
			throw new ProductNotFoundException("[update] productEntity is not found by : " + productEntity.getProductId());
		}
		return productEntity;
	}

	/**
     * Repository에서 기본적으로 제공하는 대표적인 메소드
     */
    private void testRepositoryMethod() {
/*    productRepository.save();
    productRepository.getById();
    productRepository.delete();
    productRepository.deleteAll();
    productRepository.findAll();
    productRepository.saveAll();*/
    }
}
