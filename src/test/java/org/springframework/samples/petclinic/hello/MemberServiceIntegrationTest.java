package org.springframework.samples.petclinic.hello;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
	MemberRepository memberRepository;

    @Autowired
	MemberService memberService;


	@Test
	@DisplayName("객체생성여부")
	void Injection() {
		assertThat(memberService).isNotNull();
		assertThat(memberRepository).isNotNull();
	}

	@Test
	@DisplayName("insert 및 사용자 아이디로 조회")
	void 회원가입() {
		// given
		Member member = new Member();
		member.setName("spring10character");

		// when
		Long savedId = memberService.join(member);

		// then
		Member findMember = memberService.findOne(savedId).get();
		assertThat(member.getId()).isEqualTo(findMember.getId());
		assertThat(member.getName()).isEqualTo(findMember.getName());
	}

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring10character");

        Member member2 = new Member();
        member2.setName("spring10character");

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

}
