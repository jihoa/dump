package org.springframework.samples.petclinic.product.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductExistedException extends RuntimeException {

	public ProductExistedException(String message) {
		super(message);
	}
}
