package org.springframework.samples.petclinic.todo;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.todo.jpa.JpaTodoRepository;
import org.springframework.samples.petclinic.todo.mybatis.MybatisTodoRepository;

@Configuration
public class TodoSpringConfig {



	//SpringDataJPA

	private final TodoRepository todoRepository;

	public TodoSpringConfig(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	@Bean
	public TodoServiceImpl todoService() {
		return new TodoServiceImpl(todoRepository);
	}


	//JPA
//	private final EntityManager entityManager;
//
//	@Autowired
//	public TodoSpringConfig(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}
//
//	@Bean
//	public TodoServiceImpl todoService() {
//		return new TodoServiceImpl(todoRepository());
//	}
//
//	@Bean
//	public TodoRepository todoRepository() {
//		return new JpaTodoRepository(entityManager);
//	}



	//MyBatis..
//	private final TodoMapper todoMapper;
//
//	public TodoSpringConfig(TodoMapper todoMapper) {
//		this.todoMapper = todoMapper;
//	}
//
//	@Bean
//	public TodoServiceImpl todoService() {
//		return new TodoServiceImpl(todoRepository());
//	}
//
//	@Bean
//	public TodoRepository todoRepository() {
//		return new MybatisTodoRepository(todoMapper);
//	}


}
