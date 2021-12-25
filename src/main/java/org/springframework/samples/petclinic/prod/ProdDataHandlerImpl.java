package org.springframework.samples.petclinic.prod;

import org.springframework.samples.petclinic.product.ProductEntity;

public class ProdDataHandlerImpl implements ProdDataHandler{

	@Override
	public ProductEntity saveProductEntity(String productId, String productName, int productPrice, int productStock) {
		return null;
	}

	@Override
	public ProductEntity getProductEntity(String productId) {
		return null;
	}
}
