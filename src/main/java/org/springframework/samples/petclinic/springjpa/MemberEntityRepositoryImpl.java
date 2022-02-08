package org.springframework.samples.petclinic.springjpa;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MemberEntityRepositoryImpl implements MemberEntityRepositoryCustom{

	private final EntityManager em;
//	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<MemberEntity> findMemberCustom() {
		return em.createQuery("select m from MemberEntity m").getResultList();
	}
}
