package org.springframework.samples.petclinic.todo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * <pre>
 * com.asianaidt.springmvc.step03.todo.web
 * L TodoControllerTest.java
 * @WebMvcTest + @MockBean 조합
 *
 * @date : 2021-05-24 오후 3:24
 * @author : YHKIM
 **/
@WebMvcTest(TodoController.class)
class TodoControllerTest /*extends AbstractControllerTest*/ {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	TodoService todoService;

	@Test
	@DisplayName("TodoController 빈 생성")
	void whenTodoControllerInjected_thenNotNull() {
		assertThat(TodoController.class).isNotNull();
	}

	@Test
	void getTodos_test() throws Exception {
		// given
		List<Todo> actualList = new ArrayList<>();
		Todo todo = new Todo();
		todo.setId(0L);
		todo.setUsername("admin");
		todo.setDescription("실습테스트");
		todo.setTargetDate(new Date());
		actualList.add(todo);

		// todoService.getTodosByUser("admin") 메소드를 실행하면 actualList를 리턴해줘라.
		given(todoService.getTodosByUser("admin")).willReturn(actualList);

		// when
		final ResultActions actions = mockMvc.perform(get("/hello/todo/"+todo.getUsername()));

		// then
		actions//.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("members/todoList"))
			.andExpect(model().attributeExists("todosByUser"))
			.andExpect(model().attribute("todosByUser", IsCollectionWithSize.hasSize(1)));


		// verify
		verify(todoService).getTodosByUser("admin");

	}

}
