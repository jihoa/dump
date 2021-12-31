package org.springframework.samples.petclinic.todo.springdatajpa;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.todo.Todo;

public interface SpringDataJpaTodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByUsername(String username);


}
