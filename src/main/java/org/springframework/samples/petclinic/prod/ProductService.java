package org.springframework.samples.petclinic.prod;


import org.springframework.samples.petclinic.product.ProductDto;

public interface ProductService {

  ProductDto saveProduct(String productId, String productName, int productPrice, int productStock);

  ProductDto getProduct(String productId);

}
