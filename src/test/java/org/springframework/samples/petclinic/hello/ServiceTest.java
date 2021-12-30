package org.springframework.samples.petclinic.hello;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServiceTest {
	MemoryMemberRepository memberRepository;
	MemberService memberService;

	@BeforeEach
	void beforeEach() {
		this.memberRepository = new MemoryMemberRepository();
		this.memberService = new MemberService(memberRepository);
	}


	@Test
	void 회원가입() {
		Member member = new Member();
		member.setName("spring");

		Long saveId = memberService.join(member);
		Member findMember = memberService.findOne(saveId).get();

		
		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getName()).isEqualTo(member.getName());

	}

	@Test
	@DisplayName("중복회원 제외")
	void againId() {
		Member member1 = new Member();
		member1.setName("spring");

		Member member2 = new Member();
		member2.setName("spring");

		memberService.join(member1);
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


	}


}
