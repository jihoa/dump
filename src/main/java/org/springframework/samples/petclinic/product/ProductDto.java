package org.springframework.samples.petclinic.product;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {


//	public static ProductDtoBuilder builder(){
//		return new ProductDtoBuilder();
//	}


  //@Size(min = 8, max = 8) // abcdefg
  @NotNull
  private String productId;

  @NotNull
  private String productName;

  @NotNull
  @Min(value = 500)
  @Max(value = 3000000)
  private int productPrice;

  @NotNull
  @Min(value = 0)
  @Max(value = 9999)
  private int productStock;

  public ProductEntity toEntity(){
    return ProductEntity.builder()
        .productId(productId)
        .productName(productName)
        .productPrice(productPrice)
        .productStock(productStock)
        .build();
  }

}
