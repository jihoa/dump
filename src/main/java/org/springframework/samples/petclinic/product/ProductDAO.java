package org.springframework.samples.petclinic.product;

import java.util.List;

public interface ProductDAO {

    ProductEntity saveProduct(ProductEntity productEntity);

    ProductEntity getProduct(String productId);

    boolean deleteProduct(String productId);

    List<ProductEntity> getProducts();

	ProductEntity modifyProduct(ProductEntity productEntity);
}
