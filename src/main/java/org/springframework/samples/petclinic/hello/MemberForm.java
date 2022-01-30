package org.springframework.samples.petclinic.hello;


import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

	@NotEmpty(message = "haveToName")
    private String name;

	private String city;
	private String street;
	private String zipcode;
}
