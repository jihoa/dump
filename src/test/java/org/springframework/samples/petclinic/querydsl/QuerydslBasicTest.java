package org.springframework.samples.petclinic.querydsl;


import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.springjpa.MemberEntity;
import org.springframework.samples.petclinic.springjpa.Team;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

	@PersistenceContext
	private EntityManager em;

	private JPAQueryFactory queryFactory;

	private long testId;

	@BeforeEach
	public void setup() {
		queryFactory= new JPAQueryFactory(em);

		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");

		em.persist(teamA);
		em.persist(teamB);

		MemberEntity member1 = new MemberEntity("member1", 10, teamA);

		em.persist(member1);

		testId = member1.getId();

	}
	@Test
	void strtJPQL() {
		MemberEntity findMember = em.createQuery("select m from MemberEntity m where m.username = :username", MemberEntity.class)
			.setParameter("username", "member1").getSingleResult();

		assertThat("member1").isEqualTo(findMember.getUsername());
	}

	@Test
	void startQuerydsl() {
	}
}
