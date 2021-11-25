package org.springframework.samples.petclinic.product;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


//@Builder 이해용.
public class ProductDtoBuilder {

	private String productId;
//	private String productName;
//	private int productPrice;
//	private int productStock;


	//Load
	public ProductDto build(){
		ProductDto productDto = new ProductDto();
		productDto.setProductId(this.productId);

		return productDto;
	}


	//set
	public ProductDtoBuilder productId(String productId){
		this.productId = productId;

		return this;
	}

}
