package org.springframework.samples.petclinic.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        LOGGER.info("[saveProduct] product 정보 저장. productId : {}", productEntity.getProductId());
        ProductEntity productEntity1 = productRepository.save(productEntity);
        LOGGER.info("[saveProduct] product 정보 저장완료. productId : {}", productEntity1.getProductId());
        return productEntity1;
    }

    @Override
    public ProductEntity getProduct(String productId) {
        LOGGER.info("[getProduct] product 정보 요청. productId : {}", productId);
        ProductEntity productEntity = productRepository.getById(productId);
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
