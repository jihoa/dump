package org.springframework.samples.petclinic.restful;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	MemberService memberService;

	@Test
	public void whenUserControllerInjected_thenNotNull()  {
		assertThat(MemberController.class).isNotNull();
		assertThat(MemberService.class).isNotNull();
	}

	@Test
	public void whenGetRequestToMembers_thenCorrectResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/members")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	void whenPostRequestToMembersAndValidUser_thenCorrectResponse() throws Exception {
		MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
		String member = "{\"username\":\"yhkim\",\"password\":\"12345678\",\"email\":\"yuri56@asianaidt.com\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/members").content(member).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());//.andExpect(jsonPath("$.username").exists());
//			.andExpect(MockMvcResultMatchers.content().contentType(textPlainUtf8));
	}

	@Test
	public void whenPostRequestToUsersAndInValidUser_thenCorrectResponse() throws Exception {
		String member = "{\"username\":\" \",\"password\":\"12345678\",\"email\":\"bob@asianaidt.com\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/members")
				.content(member)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(jsonPath("$.username", Is.is("사용자이름은 필수 입력 사항입니다.")))
			.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON));
	}
}

