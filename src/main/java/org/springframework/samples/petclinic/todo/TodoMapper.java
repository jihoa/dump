package org.springframework.samples.petclinic.todo;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper {
	List<Todo> getTodosByUser(String name);

    void save(Todo todo);

	Optional<Todo> getTodosByUserOne(String name);
}
