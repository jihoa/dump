package org.springframework.samples.petclinic.todo.mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.todo.Todo;
import org.springframework.samples.petclinic.todo.TodoMapper;
import org.springframework.samples.petclinic.todo.TodoRepository;
import org.springframework.transaction.annotation.Transactional;


@Disabled("TodoSpringConfig 빈 생성 필수")
@SpringBootTest
@Transactional
class MybatisTodoRepositoryTest {

	@Autowired
	TodoRepository todoRepository;
	@Autowired
	TodoMapper todoMapper;

	@BeforeEach
	void BeforeEach() {
//		todoMapper = new TodoMapper();
	}

	@Test
	@DisplayName("todoService 생성확인")
	void whenTodoServiceInjected_thenNotNull() {
		assertThat(todoRepository).isNotNull();
		assertThat(todoMapper).isNotNull();
	}




	@Test
	@DisplayName("사용자 이름으로 조회")
	void whenGetTodosByUserSuccess_thenCorrectResponse() {
		// given
		final Todo todo1 = new Todo();
		todo1.setId(1L);
		todo1.setUsername("arori tester");
		todo1.setDescription("mybatis 연동 테스트");
		todo1.setTargetDate(new Date());
		todoRepository.save(todo1);
		//given(todoMapper.getTodosByUser(todo1.getUsername())).willReturn(todoList);

		// when
		List<Todo> resultList= todoMapper.getTodosByUser(todo1.getUsername());
		// then

		assertThat(todo1.getId()).isEqualTo(resultList.get(0).getId());
		assertThat(todo1.getUsername()).isEqualTo(resultList.get(0).getUsername());
		assertThat(todo1.getDescription()).isEqualTo(resultList.get(0).getDescription());
		assertThat(todo1.getTargetDate()).isEqualTo(resultList.get(0).getTargetDate());
		//verify(todoMapper).getTodosByUser(any(String.class));
	}

}
