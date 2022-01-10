package org.springframework.samples.petclinic.hello;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.todo.TodoController;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

	
	@Autowired
	MockMvc mockMvc;

	@MockBean
	MemberService memberService;

	@Test
	@DisplayName("MemberController 빈 생성")
	void whenTodoControllerInjected_thenNotNull() {
		assertThat(MemberController.class).isNotNull();
		assertThat(memberService).isNotNull();
	}

	@Test
	@DisplayName("members GET")
	void Members_Get_Test() throws Exception {
		ArrayList<Member> actualList = new ArrayList<>();

		Member member = new Member();
		member.setId(1L);
		member.setName("spring");

		actualList.add(member);

		given(memberService.findMembers()).willReturn(actualList);

		ResultActions resultActions = mockMvc.perform(get("/hello/members"));

		resultActions.andExpect(status().isOk());

		verify(memberService).findMembers();
	}


}
