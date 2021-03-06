package org.springframework.samples.petclinic.todo;

import java.util.List;
import java.util.Optional;
import org.springframework.samples.petclinic.hello.Member;
import org.springframework.stereotype.Service;

//@Service
//NON-USED
public class MybatisTodoServiceImpl implements TodoService{

	private final TodoMapper todoMapper;

	public MybatisTodoServiceImpl(TodoMapper todoMapper) {
		this.todoMapper = todoMapper;
	}


	public Optional<Todo> getTodoByUserOne(String name) {
		return todoMapper.getTodosByUserOne(name);
	}

	//이름으로 조회..
	@Override
	public List<Todo> getTodosByUser(String name) {
		return todoMapper.getTodosByUser(name);
	}


	private void validateDuplicateMember(Todo todo) {
		todoMapper.getTodosByUserOne(todo.getUsername()).ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
	}

	@Override
	public void saveTodo(Todo todo) {
		validateDuplicateMember(todo);
		todoMapper.save(todo);
	}

	@Override
	public List<Todo> findAll() {
		return null;
	}

}
