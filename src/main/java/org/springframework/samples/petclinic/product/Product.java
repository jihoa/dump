package org.springframework.samples.petclinic.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "product")
public class Product {

	@Id
	String id;

	String name;

	Integer price;

	Integer stock;

  /*
  @Column
  String sellerId;
  @Column
  String sellerPhoneNumber;
   */

	public ProductDto toDto(){
		return ProductDto.builder()
			.productId(id)
			.productName(name)
			.productPrice(price)
			.productStock(stock)
			.build();
	}

}
