package org.springframework.samples.petclinic.springjpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberEntityRepositoryTest {

	@Autowired
	MemberEntityRepository memberEntityRepository;


	@Test
	public void testMember() {
		MemberEntity member = new MemberEntity("memberA");
		MemberEntity savedMember = memberEntityRepository.save(member);
		MemberEntity findMember = memberEntityRepository.findById(savedMember.getId()).get();

		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
	}

	@Test
	public void callCustom() {
		//QueryDSL
		List<MemberEntity> result = memberEntityRepository.findMemberCustom();
	}

	@Test
	public void testQuery() {
		MemberEntity member1 = new MemberEntity("AAA", 10);
		MemberEntity member2 = new MemberEntity("AAA", 20);

		memberEntityRepository.save(member1);
		memberEntityRepository.save(member2);

		List<MemberEntity> result = memberEntityRepository.findUser("AAA", 10);
		assertThat(result.get(0)).isEqualTo(member1);
	}

	@Test
	public void basicCRUD() {
		MemberEntity member1 = new MemberEntity("member1");
		MemberEntity member2 = new MemberEntity("member2");

		memberEntityRepository.save(member1);
		memberEntityRepository.save(member2);

		MemberEntity findMember1 = memberEntityRepository.findById(member1.getId()).get();
		MemberEntity findMember2 = memberEntityRepository.findById(member2.getId()).get();

		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);



		List<MemberEntity> members = memberEntityRepository.findAll();
		assertThat(members.size()).isEqualTo(2);

		long count = memberEntityRepository.count();
		assertThat(count).isEqualTo(2);

		memberEntityRepository.delete(member1);
		memberEntityRepository.delete(member2);
		long deletedCount = memberEntityRepository.count();
		assertThat(deletedCount).isEqualTo(0);


	}


	@Test
	@Disabled
	@DisplayName("memberEntityRepository 초기화 필요..")
	public void findByUsernameAndAgeGreaterThen() {
		MemberEntity m1 = new MemberEntity("AAA", 10);
		MemberEntity m2 = new MemberEntity("AAA", 20);
		memberEntityRepository.save(m1);
		memberEntityRepository.save(m2);

		List<MemberEntity> result = memberEntityRepository.findByUsernameAndAgeGreaterThan ("AAA", 15);

		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result.size()).isEqualTo(1);
	}






}
