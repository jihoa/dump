package org.springframework.samples.petclinic.todo.mybatis;

import java.util.List;
import java.util.Optional;
import org.springframework.samples.petclinic.todo.Todo;
import org.springframework.samples.petclinic.todo.TodoMapper;
import org.springframework.samples.petclinic.todo.TodoRepository;

public class MybatisTodoRepository implements TodoRepository {

	private final TodoMapper todoMapper;

	public MybatisTodoRepository(TodoMapper todoMapper) {
		this.todoMapper = todoMapper;
	}

	//널
	@Override
	public List<Todo> findAll() {
		return null;
	}

	@Override
	public List<Todo> findByUsername(String username) {
		return todoMapper.getTodosByUser(username);
	}

	//널
	@Override
	public Optional<Todo> findById(long id) {
		return Optional.empty();
	}


	//JPA type is Todo : Mybatis void!!!!!!!!
	@Override
	public Todo save(Todo todo) {
		todoMapper.save(todo);
		return null;
	}

	//널
	@Override
	public void delete(Todo todo) {

	}
}
