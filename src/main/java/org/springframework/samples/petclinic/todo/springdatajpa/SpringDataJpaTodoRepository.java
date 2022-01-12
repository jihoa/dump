package org.springframework.samples.petclinic.todo.springdatajpa;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.todo.Todo;
import org.springframework.samples.petclinic.todo.TodoRepository;

public interface SpringDataJpaTodoRepository extends CrudRepository<Todo, Long> , TodoRepository {
    List<Todo> findByUsername(String username);
}
