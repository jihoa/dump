package org.springframework.samples.petclinic.todo.springdatajpa;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.todo.Todo;
import org.springframework.samples.petclinic.todo.TodoRepository;

public interface SpringDataJpaTodoRepository extends CrudRepository<Todo, Long> , TodoRepository { //TodoRepo 상속하여야 빈 객체 생성 완료.
    List<Todo> findByUsername(String username);

	//CrudRepo는 update 사용 불가. 주의!!
}
