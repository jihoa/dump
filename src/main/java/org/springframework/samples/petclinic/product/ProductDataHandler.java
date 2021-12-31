package org.springframework.samples.petclinic.product;

import java.util.List;
import org.springframework.samples.petclinic.product.ProductEntity;

public interface ProductDataHandler {

  ProductEntity saveProductEntity(String productId, String productName, int productPrice, int productStock);

  ProductEntity getProductEntity(String productId);

  boolean deleteProduct(String productId);

  List<ProductEntity> findAll();
}
