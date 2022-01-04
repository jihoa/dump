package org.springframework.samples.petclinic.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.hello.Member;
import org.springframework.samples.petclinic.product.ProductDataHandlerImpl;
import org.springframework.samples.petclinic.product.ProductServiceImpl;
import org.springframework.samples.petclinic.todo.MybatisTodoServiceImpl;
import org.springframework.samples.petclinic.todo.Todo;
import org.springframework.samples.petclinic.todo.TodoMapper;
import org.springframework.samples.petclinic.todo.TodoService;
import org.springframework.transaction.annotation.Transactional;

// MockitoExtension: Mockito framework를 juni5에 통합
// @Mock : create 모의객체
// @InjectMocks : create 객체 and  모의 의존성 주입

@Transactional
//@ExtendWith(MockitoExtension.class)
@SpringBootTest//(classes = {MybatisTodoServiceImpl.class, TodoMapper.class})//추후 추가하기..
class MybatisTodoServiceImplTest {
    Logger logger = LoggerFactory.getLogger(MybatisTodoServiceImplTest.class);
    //@InjectMocks
    @Autowired
    private TodoService todoService;
    //private TodoService todoService = new MybatisTodoServiceImpl();
    @Autowired
    private TodoMapper todoMapper;

    @Test
    @DisplayName("테스트설명")
    void when_then() throws Exception {
        // given

        // when

        // then
     }

    @Test
    @DisplayName("todoService 생성확인")
    void whenTodoServiceInjected_thenNotNull() {
        assertThat(todoService).isNotNull();
        assertThat(todoMapper).isNotNull();
    }

	@Test
	@DisplayName("overlap no")
	void overlap_no() {

		Todo todo1 = new Todo();
//		todo1.setId(1L);
		todo1.setUsername("arori tester");
		todo1.setDescription("mybatis 연동 테스트1");
		todo1.setTargetDate(new Date());

		Todo todo2 = new Todo();
//		todo2.setId(2L);
		todo2.setUsername("arori tester");
		todo2.setDescription("mybatis 연동 테스트2");
		todo2.setTargetDate(new Date());

		//when
		todoService.saveTodo(todo1);
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> todoService.saveTodo(todo2));

		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
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
        todoService.saveTodo(todo1);
        //given(todoMapper.getTodosByUser(todo1.getUsername())).willReturn(todoList);

        // when
        List<Todo> resultList= todoMapper.getTodosByUser(todo1.getUsername());
        // then

		assertThat(todo1.getId()).isEqualTo(resultList.get(0).getId());
        assertThat(todo1.getUsername()).isEqualTo(resultList.get(0).getUsername());
		assertThat(todo1.getDescription()).isEqualTo(resultList.get(0).getDescription());
		assertThat(todo1.getTargetDate()).isEqualTo(resultList.get(0).getTargetDate());
//        verify(todoMapper).getTodosByUser(todo1.getUsername());
    }


}
