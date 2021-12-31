package org.springframework.samples.petclinic.restful.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.restful.Member;
import org.springframework.samples.petclinic.restful.MemberRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// SpringExtension: Spring5 스펙으로 Spring TestContext Framework 를 JUnit5 프로그래밍 모델에 통합해줌.
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(value = "dev")
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("MemberRespository 생성확인")
    void whenMemberRepositoryInjected_thenNotNull() {
        assertThat(memberRepository).isNotNull();
    }

    @Test
    @DisplayName("멤버 생성하고 저장된 멤버와 동일한지 확인")
    void whenSaveMember_thenCorrectResponse() {
        // given
        Member member = new Member();
        member.setUsername("name");
        member.setPassword("password");
        member.setEmail("test@asianaidt.com");

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(member.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(member.getPassword()).isEqualTo(savedMember.getPassword());
        assertThat(member.getEmail()).isEqualTo(savedMember.getEmail());
    }

    @Test
    @DisplayName("생성한 findByUsername 메서드 정상 작동 확인")
    void whenFindByUsernameSuccess_thenCorrectResponse() {
        // given
        Member member = new Member();
        member.setUsername("name");
        member.setPassword("password");
        member.setEmail("test@asianaidt.com");
        Member savedMember = memberRepository.save(member);

        // when
        Optional<Member> findByUsername = memberRepository.findByUsername(member.getUsername());

        // then
        findByUsername.ifPresent(value -> assertThat(savedMember.getUsername()).isEqualTo(value.getUsername()));

    }

    @Test
    @DisplayName("Optaion.empty() 확인")
    void whenFindByUsernameFailure_thenCorrectResponse() {
        Optional<Member> findByUsername = memberRepository.findByUsername("not exist member");
        assertThat(Optional.empty()).isEqualTo(findByUsername);
    }

    @Test
    @DisplayName("ID 생성 전략 확인")
    void whenIdStrategy_thenCorrectResponse() {
        Member member1 = new Member();
        member1.setUsername("name1");
        member1.setPassword("password1");
        member1.setEmail("test@asianaidt.com");
        Member savedMemeber1 = memberRepository.save(member1);

        Member member2 = new Member();
        member2.setUsername("name2");
        member2.setPassword("password");
        member2.setEmail("test@asianaidt.com");
        Member savedMemeber2 = memberRepository.save(member2);

        assertThat(1).isEqualTo(Math.abs(savedMemeber1.getId() - savedMemeber2.getId()));

    }


}
