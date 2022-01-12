package org.springframework.samples.petclinic.todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {

	List<Todo> getTodosByUser(String name);

	void saveTodo(Todo toEntity);
}
