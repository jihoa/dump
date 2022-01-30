package org.springframework.samples.petclinic.hello;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "hello")
@Getter
@Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ApiModelProperty("NAME")
	@NotNull
	@Size(min = 10, max =  50)//check..
    private String name;


	@Embedded
	private Address address;

}
