package org.springframework.samples.petclinic.springjpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "team")
public class Team {

	@Id
	@GeneratedValue
	@Column(name = "team_id")
	private Long id;

	private String name;


	@OneToMany(mappedBy = "team")
	private List<MemberEntity> members =new ArrayList<>();

	public Team(String name) {
		this.name = name;
	}

	public List<MemberEntity> getMembers() {
		return members;
	}
}
