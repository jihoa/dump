package org.springframework.samples.petclinic.product;


public interface ProductService {

  ProductDto saveProduct(String productId, String productName, int productPrice, int productStock);

  ProductDto getProduct(String productId);

}
