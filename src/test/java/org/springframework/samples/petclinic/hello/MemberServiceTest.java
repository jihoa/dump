package org.springframework.samples.petclinic.hello;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
	MemoryMemberRepository memberRepository;
	MemberService memberService;

	@BeforeEach
	void beforeEach() {
		memberRepository = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
	}

	@AfterEach
	void afterEach() {
		memberRepository.clearStore();
	}


	@Test
	void 회원가입() {
		// given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드
		Member member = new Member();
		member.setName("hello");//id는 자동 증가.

		// when
		Long savedId = memberService.join(member);

		// then
		Member findMember = memberService.findOne(savedId).get();
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(member.getName()).isEqualTo(findMember.getName());
	}

	@Test
	void 중복_회원_예외() {
		// given
		Member member1 = new Member();
		member1.setName("spring");

		Member member2 = new Member();
		member2.setName("spring");

		// when
		memberService.join(member1);
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/*

        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

*/

	}

	@Test
	void findMembers() {
	}

	@Test
	void findOne() {
	}
}
