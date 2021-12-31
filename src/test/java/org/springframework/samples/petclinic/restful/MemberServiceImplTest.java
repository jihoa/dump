package org.springframework.samples.petclinic.restful;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.restful.exception.MemberExistedException;
import org.springframework.samples.petclinic.restful.exception.MemberNotFoundException;
import org.springframework.transaction.annotation.Transactional;


// MockitoExtension: Mockito framework를 juni5에 통합
// @Mock : create 모의객체
// @InjectMocks : create 객체 and  모의 의존성 주입
@Transactional
@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = MemberServiceImpl.class)
class MemberServiceImplTest {
	@InjectMocks
	MemberServiceImpl memberService;
	@Mock
	private MemberRepository memberRepository;

	@Test
	@DisplayName("MemberService 생성확인")
	void whenMemberRepositoryInjected_thenNotNull() {
		assertThat(memberService).isNotNull();
		assertThat(memberRepository).isNotNull();
	}

	@Test
	@DisplayName("멤버등록 성공")
	void whenAddMemberSuccess_thenCorrectResponse() {
		// given
		final Member member = new Member();
		member.setUsername("name");
		member.setPassword("12345678");
		member.setEmail("test@asianaidt.com");
		given(memberRepository.findByEmail(member.getEmail())).willReturn(Optional.empty());
		given(memberRepository.save(member)).willReturn(member);

		// when
		Member savedMember = memberService.add(member);
		//when(memberService.add(member)).thenReturn(savedMember);

		// then
		assertThat(savedMember).isNotNull();
//		verify(memberRepository, times(2)).findByEmail(anyString());
		verify(memberRepository).save(any(Member.class));
	}

	@Test
	@DisplayName("멤버등록 실패")
	void whenAddMemberFailure_thenThrowMemberExistedException() {
		// given
		final Member member = new Member();
		member.setId(1L);
		member.setUsername("name");
		member.setPassword("12345678");
		member.setEmail("test@asianaidt.com");
		given(memberRepository.findByEmail(member.getEmail())).willReturn(Optional.of(member));

		// when
		//when().thenThrow()를 사용하면 지정한 조건의 메서드가 호출될때 예외를 발생시킨다.
		//when(memberService.add(member)).thenThrow(MemberExistedException.class);
		// then
		assertThatThrownBy(() -> memberService.add(member))
			.isInstanceOf(MemberExistedException.class)
			.hasMessage("member is already joined : " + member.getEmail());
		verify(memberRepository, never()).save(any(Member.class));
		// verify() 두 번째 인자 값
		// times(int) - 지정한 회수 만큼 호출되었는 지 검증.
		// never() - 호출되지 않았는지 여부 검증.
		// atLeastOnce() - 최소한 한번은 호출되었는 지 검증.
		// atLeast(int) - 최소한 지정한 회수 만큼 호출되었는 지 검증.
		// atMost(int) - 최대 지정한 회수 만큼 호출되었는 지 검증.
	}

	@Test
	@DisplayName("ID에 맞는 멤버 정보 조회 성공")
	void whenGetMemberByIdSuccess_thenCorrectResponse() {
		// given
		final Member member = new Member();
		member.setId(1L);
		member.setUsername("name");
		member.setPassword("12345678");
		member.setEmail("test@asianaidt.com");
		given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

		// when
		Member findByIdMember = memberService.get(member.getId());

		// then
		assertThat(member).isEqualTo(findByIdMember);
		verify(memberRepository, times(1)).findById(anyLong());

	}

	@Test
	@DisplayName("ID에 해당하는 사용자가 존재하지 않을 때")
	void whenGetMemberByIdFailed_thenThrowMemberNotFoundException() {
		// given
		given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

		// when
		//when().thenThrow()를 사용하면 지정한 조건의 메서드가 호출될때 예외를 발생시킨다.
		//when(memberService.get(anyLong())).thenThrow(MemberNotFoundException.class);

		// then
		assertThatThrownBy(() -> memberService.get(anyLong()))
			.isInstanceOf(MemberNotFoundException.class)
			.hasMessage("[findById]Member is not found by : " + 0L);
		verify(memberRepository, times(1)).findById(anyLong());
	}

	@Test
	@DisplayName("멤버 정보 수정 성공")
	void whenUpdateMemberSuccess_thenCorrectResponse() {
		// given
		final Member member = new Member();
		member.setId(1L);
		member.setUsername("name");
		member.setPassword("12345678");
		member.setEmail("test@asianaidt.com");
		given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
		given(memberRepository.save(member)).willReturn(member);

		// when
		Member updatedMember = memberService.update(member);

		// then
		assertThat(updatedMember).isNotNull();
		verify(memberRepository, atLeastOnce()).findById(anyLong());
		verify(memberRepository, times(1)).save(any(Member.class));
	}

	@Test
	@DisplayName("해당 멤버가 없어 예외 발생")
	void whenUpdateMemberFailed_thenThrowMemberNotFoundException() {
		// given
		final Member member = new Member();
		member.setId(1L);
		member.setUsername("name");
		member.setPassword("12345678");
		member.setEmail("test@asianaidt.com");
		given(memberRepository.findById(member.getId())).willReturn(Optional.empty());

		// when
		// when().thenThrow()를 사용하면 지정한 조건의 메서드가 호출될때 예외를 발생시킨다.
		//when(memberService.update(member)).thenThrow(MemberNotFoundException.class);
		// then
		assertThatThrownBy(() -> memberService.get(1L))
			.isInstanceOf(MemberNotFoundException.class)
			.hasMessage("[findById]Member is not found by : " + 1L);
		verify(memberRepository, atLeastOnce()).findById(anyLong());
		verify(memberRepository, times(0)).save(any(Member.class));
	}

	@Test
	@DisplayName("멤버 삭제 성공")
	void whenDeleteMemberSuccess_thenCorrectResponse() {
		// given
		final Long id = 1L;

		// when
		memberService.delete(id);

		// then
		verify(memberRepository, times(1)).deleteById(anyLong());
		verify(memberRepository, times(1)).findById(anyLong());

	}

	@Test
	@DisplayName("이름으로 멤버 정보 조회 성공")
	void whenFindByUsernameSuccess_thenCorrectResponse() {
		// given
		final Member member = new Member();
		member.setId(1L);
		member.setUsername("name");
		member.setPassword("12345678");
		member.setEmail("test@asianaidt.com");
		given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));

		// when
		Member findByUsername = memberService.findByUsername(member.getUsername());

		// then
		assertThat(member).isEqualTo(findByUsername);
		verify(memberRepository).findByUsername(any(String.class));
	}

	@Test
	@DisplayName("멤버 전체 목록 조회 성공")
	void whenFindByAllListSuccess_thenCorrectResponse() {
		// given
		List<Member> memberList = new ArrayList<>();
		final Member member1 = new Member();
		member1.setId(1L);
		member1.setUsername("name1");
		member1.setPassword("12345678");
		member1.setEmail("test@asianaidt.com");
		memberList.add(member1);
		final Member member2 = new Member();
		member2.setId(2L);
		member2.setUsername("name2");
		member2.setPassword("12345678");
		member2.setEmail("test@asianaidt.com");
		memberList.add(member2);

		given(memberRepository.findAll()).willReturn(memberList);
		// when
		List<Member> returnList = memberService.findByAll();
		//when(memberService.findByAll()).thenReturn(retunList);

		// then
		assertThat(returnList).isNotNull();
		assertThat(memberList.size()).isEqualTo(returnList.size());
		assertThat(memberList.get(0).getUsername()).isEqualTo(returnList.get(0).getUsername());
		assertThat(memberList.get(1).getUsername()).isEqualTo(returnList.get(1).getUsername());

		verify(memberRepository).findAll();
	}
}
