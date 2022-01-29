package org.springframework.samples.petclinic.hello;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
// 아래 옵션을 주면 메모리 DB를 이용하는 것이 아닌 실 DB를 이용함
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringDataJpaMemberRepositoryTest {

	@Autowired
	MemberRepository repository;

	@Test
	@DisplayName("존재여부 확인")
	void Inject_NotNull() {
		assertThat(repository).isNotNull();
	}

	@Test
	@DisplayName("멤버 생성하고 저장된 멤버와 동일한지 확인")
	void whenSaveMember() {
		Member member = new Member();

		//member.setId(1L);
		member.setName("spring10character");

		Member savedMember = repository.save(member);

		assertThat(savedMember.getName()).isEqualTo(member.getName());
	}

	@Test
	@DisplayName("Optaion.empty() 확인")
	void whenFindByUsernameFailure_thenCorrectResponse() {
		Optional<Member> findByName = repository.findByName("not exist member");
		assertThat(Optional.empty()).isEqualTo(findByName);
	}


	@Test
	@DisplayName("생성한 findByUsername 메서드 정상 작동 확인")
	void whenFindByUsernameSuccess_thenCorrectResponse() {

		//given
		Member member = new Member();

		//member.setId(1L);
		member.setName("spring10character");

		Member savedMember = repository.save(member);

		// when
		Optional<Member> findByName = repository.findByName(member.getName());
		// then
		findByName.ifPresent(value -> assertThat(savedMember.getName()).isEqualTo(value.getName()));
	}

	@Test
	@DisplayName("ID 생성 전략 확인")
	void ID_primarykey() {
		Member member1 = new Member();
		member1.setName("spring10character");
		Member savedMember1 = repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring10character");
		Member savedMember2 = repository.save(member2);

		assertThat(1).isEqualTo(Math.abs(savedMember2.getId() - savedMember1.getId()));


	}
}
