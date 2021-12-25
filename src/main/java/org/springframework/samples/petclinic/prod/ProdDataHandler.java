package org.springframework.samples.petclinic.prod;

import org.springframework.samples.petclinic.product.ProductEntity;

public interface ProdDataHandler {

  ProductEntity saveProductEntity(String productId, String productName, int productPrice, int productStock);

  ProductEntity getProductEntity(String productId);

}
