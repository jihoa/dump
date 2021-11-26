package org.springframework.samples.petclinic.product;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.common.Constants.ExceptionClass;
import org.springframework.samples.petclinic.common.exception.AroundHubException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-api")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

	// http://localhost:8080/api/v1/product-api/product/{productId}
	@GetMapping(value = "/product/{productId}")
	public ProductDto getProduct(@PathVariable String productId) {
		return productService.getProduct(productId);
	}

	// http://localhost:8080/api/v1/product-api/product
	@PostMapping(value = "/product")
	public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {

		String productId = productDto.getProductId();
		String productName = productDto.getProductName();
		int productPrice = productDto.getProductPrice();
		int productStock = productDto.getProductStock();

		ProductDto response = productService.saveProduct(productId, productName, productPrice, productStock);
		//return productService.saveProduct(productId, productName, productPrice, productStock);
		//return response;

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@GetMapping(value = "/product/exception")
	public void exceptionTest() throws AroundHubException {
		throw new AroundHubException(ExceptionClass.PRODUCT, HttpStatus.FORBIDDEN, "접근이 금지되었습니다.");
	}


//	@ExceptionHandler(value = Exception.class)
//	public ResponseEntity<Map<String, String>> ExceptionHandler(Exception e) {
//		HttpHeaders responseHeaders = new HttpHeaders();
//		//responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
//		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//
//		LOGGER.error("controller 내 ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());
//
//		Map<String, String> map = new HashMap<>();
//		map.put("error type", httpStatus.getReasonPhrase());
//		map.put("code", "400");
//		map.put("message", "에러 발생");
//
//		return new ResponseEntity<>(map, responseHeaders, httpStatus);
//	}

}
