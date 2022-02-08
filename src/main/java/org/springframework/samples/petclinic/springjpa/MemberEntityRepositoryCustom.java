package org.springframework.samples.petclinic.springjpa;

import java.util.List;

public interface MemberEntityRepositoryCustom {

	List<MemberEntity> findMemberCustom();

}
