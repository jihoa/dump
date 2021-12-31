package org.springframework.samples.petclinic.product;


import org.springframework.http.ResponseEntity;

public interface ProductService {

	ProductDto saveProduct(String productId, String productName, int productPrice, int productStock);
	ProductDto getProduct(String productId);

    boolean deleteProduct(String productId);
}
