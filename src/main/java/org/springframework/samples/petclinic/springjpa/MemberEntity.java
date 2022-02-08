package org.springframework.samples.petclinic.springjpa;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class MemberEntity {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;

	private String username;

	private int age;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	public MemberEntity(String username) {
		this.username = username;
	}

	public MemberEntity(String username, int age) {
		this.username = username;
		this.age = age;
	}

	public MemberEntity(String username, int age, Team team) {
		this.username = username;
		this.age = age;

		if (team != null) {
			changeTeam(team);
		}
	}

	private void changeTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}
}
