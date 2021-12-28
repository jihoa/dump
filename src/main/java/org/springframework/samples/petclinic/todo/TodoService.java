package org.springframework.samples.petclinic.todo;

import java.util.List;

public interface TodoService {

	List<Todo> getTodosByUser(String name);

    void updateTodo(Todo todo);

	void saveTodo(Todo toEntity);
}
