package org.springframework.samples.petclinic.todo.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.todo.Todo;
import org.springframework.samples.petclinic.todo.TodoRepository;

@Disabled("TodoSpringConfig Bean 생성 필수")
@DataJpaTest
//@SpringBootTest
class JpaTodoRepositoryTest {
    @Autowired
	TodoRepository todoRepository ;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("JpaTodoRepository 생성확인")
    void whenMemberRepositoryInjected_thenNotNull() {
        assertThat(entityManager).isNotNull();
        assertThat(todoRepository).isNotNull();
    }

    @Test
    @DisplayName("생성한 findByUsername 메서드 정상 작동 확인")
    void whenFindByUsernameSuccess_thenCorrectResponse() {
        // given
        Todo todo = new Todo();
        todo.setUsername("tester");
        todo.setDescription("10자이상입력해야 처리됨");
        todo.setTargetDate(new Date());
        Todo savedMember = todoRepository.save(todo);

        // when
        Optional<List<Todo>> findByUsername = Optional.ofNullable(todoRepository.findByUsername(todo.getUsername()));

        // then
        Assertions.assertThat(savedMember.getUsername()).isEqualTo(findByUsername.get().get(0).getUsername());

    }
}
