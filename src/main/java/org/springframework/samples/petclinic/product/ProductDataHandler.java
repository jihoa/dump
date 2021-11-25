package org.springframework.samples.petclinic.product;

import org.springframework.samples.petclinic.product.ProductEntity;

public interface ProductDataHandler {

  ProductEntity saveProductEntity(String productId, String productName, int productPrice, int productStock);

  ProductEntity getProductEntity(String productId);

}
