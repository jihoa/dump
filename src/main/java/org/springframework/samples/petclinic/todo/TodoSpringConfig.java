package org.springframework.samples.petclinic.todo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoSpringConfig {

	private final TodoRepository todoRepository;

	public TodoSpringConfig(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	@Bean
	public TodoServiceImpl todoService() {
		return new TodoServiceImpl(todoRepository);
	}


}
