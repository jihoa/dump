package org.springframework.samples.petclinic.todo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MybatisTodoServiceImpl implements TodoService{

	@Autowired
	TodoMapper todoMapper;

	//이름으로 조회..
	@Override
	public List<Todo> getTodosByUser(String name) {
		return todoMapper.getTodosByUser(name);
	}

	@Override
	public void updateTodo(Todo todo) {

	}

	@Override
	public void saveTodo(Todo todo) {
		todoMapper.save(todo);
	}

}
