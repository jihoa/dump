package org.springframework.samples.petclinic.springjpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberEntityRepository extends JpaRepository<MemberEntity, Long>, MemberEntityRepositoryCustom, JpaSpecificationExecutor<MemberEntity> {

	@Query("select m from MemberEntity m where m.username = :username and m.age = :age") //(이름이 없는 NamedQuery)애플리케이션 실행시점에 오류가 난다    (실무에서 복잡한 정적쿼리를 해결할때 자주 사용) 동적쿼리는 QueryDSL 사용
	List<MemberEntity> findUser(@Param("username") String username, @Param("age") int age);

	List<MemberEntity> findByUsernameAndAgeGreaterThan(String username, int age);
}
