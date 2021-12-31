package org.springframework.samples.petclinic.product;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    ProductDataHandler productDataHandler;

    @Autowired
    public ProductServiceImpl(ProductDataHandler productDataHandler) {
        this.productDataHandler = productDataHandler;
    }

    @Override
    public ProductDto saveProduct(String productId, String productName, int productPrice,
        int productStock) {

        LOGGER.info("[saveProduct] productDataHandler 로 상품 정보 저장 요청");
        ProductEntity productEntity = productDataHandler.saveProductEntity(productId, productName,
            productPrice, productStock);

        LOGGER.info("[saveProduct] Entity 객체를 DTO 객체로 변환 작업. productId : {}",
            productEntity.getProductId());
        ProductDto productDto = new ProductDto(productEntity.getProductId(),
            productEntity.getProductName(), productEntity.getProductPrice(),
            productEntity.getProductStock());

        return productDto;
    }

    @Override
    public ProductDto getProduct(String productId) {

        LOGGER.info("[getProduct] productDataHandler 로 상품 정보 조회 요청");
        ProductEntity productEntity = productDataHandler.getProductEntity(productId);

        LOGGER.info("[getProduct] Entity 객체를 DTO 객체로 변환 작업. productId : {}",
            productEntity.getProductId());
        ProductDto productDto = new ProductDto(productEntity.getProductId(),
            productEntity.getProductName(), productEntity.getProductPrice(),
            productEntity.getProductStock());

        return productDto;
    }

	@Override
	public boolean deleteProduct(String productId) {
		boolean deleteProduct = productDataHandler.deleteProduct(productId);
		return deleteProduct;
	}

	@Override
	public ResponseEntity<List<ProductDto>> findAll() {
		List<ProductEntity> productEntitys = productDataHandler.findAll();
		//모르겠다..
		return null;
	}

	@Override
	public ProductDto modifyProduct(String productId, String productName, int productPrice, int productStock) {
		ProductEntity productEntity = productDataHandler.modifyProductEntity(productId, productName,
			productPrice, productStock);

		ProductDto productDto = new ProductDto(productEntity.getProductId(),
			productEntity.getProductName(), productEntity.getProductPrice(),
			productEntity.getProductStock());

		return productDto;
	}


}
