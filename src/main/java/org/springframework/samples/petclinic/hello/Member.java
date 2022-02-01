package org.springframework.samples.petclinic.hello;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "hello")
@Getter
@Setter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@ApiModelProperty("NAME")
	@NotNull @Size(min = 10, max = 50)//check..
	private String name;


	@Embedded
	private Address address;

	//Member.orders를 변경해도 fk 변경되지 않음.
	//읽기전용. member 필드에 의해서 거울이 되는 거다.
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

//	public List<Order> getOrders() {
//		return orders;
//	}
}
