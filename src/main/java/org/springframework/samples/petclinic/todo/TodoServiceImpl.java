package org.springframework.samples.petclinic.todo;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TodoServiceImpl implements TodoService{


	// 생성자 DI
	private final TodoRepository todoRepository;

	public TodoServiceImpl(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	@Override
	public List<Todo> getTodosByUser(String user) {
		return todoRepository.findByUsername(user);
	}

	public Optional<Todo> getTodoById(long id) {
		return todoRepository.findById(id);
	}

	public void updateTodo(Todo todo) {
		todoRepository.update(todo);
	}

	public void addTodo(String name, String desc, Date targetDate, Boolean isDone) {
		todoRepository.save(new Todo(name, desc, targetDate));
	}

	public void deleteTodo(long id) {
		Optional<Todo> todo = todoRepository.findById(id);
		if (todo.isPresent()) {
			todoRepository.delete(todo.get());
		}
	}

	@Override
	public void saveTodo(Todo todo) {
		log.info("{} {}", todo.getUsername(), todo.getDescription());
		todoRepository.save(todo);
	}
}
