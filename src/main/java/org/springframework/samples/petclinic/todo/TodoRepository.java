package org.springframework.samples.petclinic.todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
	List<Todo> findAll();

	List<Todo> findByUsername(String username);

	Optional<Todo> findById(long id) ;

	Todo save(Todo todo) ;

	void delete(Todo todo);

	//implements CrudRepo // update 메소드 사용 불가..

}
