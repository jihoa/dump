package org.springframework.samples.petclinic.sample;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ModelAndView;



@ExtendWith({MockitoExtension.class})
@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath:META-INF/config/step01-servlet.xml"})
class TodoControllerTest2 {


	@Mock
	private TodoService todoService;
	@InjectMocks
	private TodoController todoController;

	private MockMvc mockMvc;
	private ModelAndView mv;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		//MockMvc 인스턴스를 생성해주는 메소드.
		// standaloneSetup은 테스트에서 사용할 컨트롤러 하나만 지정해서 생성.
		mockMvc = MockMvcBuilders
			.standaloneSetup(todoController)
			.addFilter(new CharacterEncodingFilter("UTF-8", true))
			.build();
		mv = new ModelAndView();
	}

	@DisplayName("이름으로 todo 조회")
	@Test
	void getTodoByUsernameTest() throws Exception {
		String param = "techLeader-1";
		// given
		Todo todo = new Todo(1, "techLeader-1111", "아시아나IDT 기술 리더 교육 실습", new Date());
		todoService.saveTodo(todo);

		//ObjectMapper objectMapper = new ObjectMapper();
		// 테스트 대상

		// perform() : 매핑 url로 요청함.
		// andExpect() : 다양한 검증 가능
        mockMvc.perform(get("/todo/techLeader-1232434324"))
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(view().name("TodoList"))
//                .andExpect(content().string("data"))
                .andDo(print());

		mv = todoController.getTodoAll();

		assertThat(mv.getViewName()).isEqualTo("TodoList");


	}

}
