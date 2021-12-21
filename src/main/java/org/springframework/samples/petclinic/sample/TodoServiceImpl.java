package org.springframework.samples.petclinic.sample;


import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements  TodoService {
    final TodoRepository todoRepository ;
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public List<Todo> getTodoByUsername(final String username) {
        System.out.println("Service :" + username);
        return todoRepository.findByUsername(username);
    }

    @Override
    public Optional<Todo> getTodoById(final long id) {
        return todoRepository.findById(id);
    }

    @Override
    public Todo saveTodo(Todo todo) {
        Todo todo1 = todoRepository.save(todo);

        return todo1;
    }
}
