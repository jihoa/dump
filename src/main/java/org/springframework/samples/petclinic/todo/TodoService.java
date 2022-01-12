package org.springframework.samples.petclinic.todo;

import java.util.List;
import org.springframework.samples.petclinic.hello.Member;

public interface TodoService {

	List<Todo> getTodosByUser(String name);

	void saveTodo(Todo toEntity);

	List<Todo> findAll();
}
