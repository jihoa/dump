package org.springframework.samples.petclinic.todo.springdatajpa;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.todo.Todo;
import org.springframework.samples.petclinic.todo.TodoRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
// 아래 옵션을 주면 메모리 DB를 이용하는 것이 아닌 실 DB를 이용함
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringDataJpaTodoRepositoryTest {
    @Autowired
	TodoRepository todoRepository;

    @Test
    @DisplayName("todoRespository 생성확인")
    void whenTodoRepositoryInjected_thenNotNull() {
        Assertions.assertThat(todoRepository).isNotNull();
    }

    @Test
    @DisplayName("멤버 생성하고 저장된 멤버와 동일한지 확인")
    void whenSaveMember_thenCorrectResponse() {
        // given
        Todo todo = new Todo();
        todo.setUsername("name");
        todo.setDescription("10자 이상 설명 입력하세요.");
        todo.setTargetDate(new Date());

        // when
        Todo savedTodo = todoRepository.save(todo);

        // then
        assertThat(todo.getUsername()).isEqualTo(savedTodo.getUsername());
        assertThat(todo.getDescription()).isEqualTo(savedTodo.getDescription());
        assertThat(todo.getTargetDate()).isEqualTo(savedTodo.getTargetDate());
    }

    @Test
    @DisplayName("생성한 findByUsername 메서드 정상 작동 확인")
    void whenFindByUsernameSuccess_thenCorrectResponse() {
        // given
        Todo todo = new Todo();
        todo.setUsername("name");
        todo.setDescription("10자 이상 설명 입력하세요.");
        todo.setTargetDate(new Date());
        Todo savedTodo = todoRepository.save(todo);

        // when
        List<Todo> findByUsername = todoRepository.findByUsername(todo.getUsername());

        // then
        assertThat(todo.getUsername()).isEqualTo(findByUsername.get(0).getUsername());

    }

    @Test
    @DisplayName("Optaion.empty() 확인")
    void whenFindByUsernameFailure_thenCorrectResponse() {
        List<Todo> findByUsername = todoRepository.findByUsername("not exist member");
        assertThat(Collections.emptyList()).isEqualTo(findByUsername);
    }

    @Test
    @DisplayName("ID 생성 전략 확인")
    void whenIdStrategy_thenCorrectResponse() {
        Todo todo1 = new Todo();
        todo1.setUsername("name");
        todo1.setDescription("10자 이상 설명 입력하세요.");
        todo1.setTargetDate(new Date());
        Todo savedTodo1 = todoRepository.save(todo1);

        Todo todo2 = new Todo();
        todo2.setUsername("name");
        todo2.setDescription("10자 이상 설명 입력하세요.");
        todo2.setTargetDate(new Date());
        Todo savedTodo2 = todoRepository.save(todo2);

        assertThat(1).isEqualTo(Math.abs(savedTodo1.getId() - savedTodo2.getId()));

    }
}
