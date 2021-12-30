package org.springframework.samples.petclinic.todo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("기본 라파지토리 테스트")
@Transactional
class BasicTodoRepositoryTest {

	BasicTodoRepository repository;

	@BeforeEach
	public void setRepository() {
		this.repository = new BasicTodoRepository();
	}

	// @AfterEach: 각 테스트가 종료될 때마다 실행
	// 모든 테스트는 각각 독립적으로 실행되어야 한다.
	@AfterEach
	public void afterEach() {
		// 테스트를 위해 저장된 데이터를 삭제한다.
		repository.clearData();
	}

	//@Test
	@RepeatedTest(2)
	public void save() {
		// Given
		Todo todo = new Todo();
		todo.setUsername("yhkim");
		todo.setDescription("데이터저장 테스트");
		todo.setTargetDate(Date.valueOf(LocalDate.now()));

		// When
		repository.save(todo);

		// Then
		Todo result = repository.findById(todo.getId()).get();
		Todo aresult = repository.findAll().get(0);

		assertThat(result).isEqualTo(todo);
		assertThat(aresult).isEqualTo(todo);
	}

	@Test
	public void findByName() {
		// Given
		Todo todo1 = new Todo();
		todo1.setUsername("yhkim1");
		todo1.setDescription("데이터저장 테스트");
		todo1.setTargetDate(Date.valueOf(LocalDate.now()));
		repository.save(todo1);

		Todo todo2 = new Todo();
		todo2.setUsername("yhkim2");
		todo2.setDescription("데이터저장 테스트");
		todo2.setTargetDate(Date.valueOf(LocalDate.now()));
		repository.save(todo2);

		// When
		Todo result = repository.findByUsername("yhkim1").get(0);
		// Then
		assertThat(result).isEqualTo(todo1);
	}

	@Test
	public void findAll() {
		// Given
		Todo todo1 = new Todo();
		todo1.setUsername("yhkim-1");
		todo1.setDescription("데이터저장 테스트");
		todo1.setTargetDate(Date.valueOf(LocalDate.now()));
		repository.save(todo1);

		Todo todo2 = new Todo();
		todo2.setUsername("yhkim-2");
		todo2.setDescription("데이터저장 테스트");
		todo2.setTargetDate(Date.valueOf(LocalDate.now()));
		repository.save(todo2);

		// When
		List<Todo> result = repository.findAll();

		// Then
		assertThat(result.size()).isEqualTo(2);
	}
}
