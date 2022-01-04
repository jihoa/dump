package org.springframework.samples.petclinic.todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {

	Optional<Todo> getTodoByUserOne(String name);

	List<Todo> getTodosByUser(String name);

    void updateTodo(Todo todo);

	void saveTodo(Todo toEntity);
}
