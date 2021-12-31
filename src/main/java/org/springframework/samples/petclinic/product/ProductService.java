package org.springframework.samples.petclinic.product;


import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ProductService {

	ProductDto saveProduct(String productId, String productName, int productPrice, int productStock);
	ProductDto getProduct(String productId);

    boolean deleteProduct(String productId);

    ResponseEntity<List<ProductDto>> findAll();
}
