package org.springframework.samples.petclinic.sample;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {
    private static final AtomicLong sequence = new AtomicLong(0);
    private static final List<Todo> todos = new ArrayList<>();

    // WAS가 올라가면서 Bean이 생성될 때 딱 한번 초기함.
    @PostConstruct
    public void initData() {
        Todo data1 = new Todo(sequence.incrementAndGet(), "techLeader-1", "아시아나IDT 기술 리더 교육 실습", new Date());
        Todo data2 = new Todo(sequence.incrementAndGet(), "techLeader-2", "아시아나IDT 기술 리더 교육 실습", new Date());
        todos.add(data1);
        todos.add(data2);
    }
    public List<Todo> findAll() {
        return todos;
    }

    public Todo save(final Todo todo) {
        todo.setId(sequence.incrementAndGet());
        todos.add(todo);
        System.out.println("repository: "+todo);
        return todo;
    }

    public Optional<Todo> findById(final long id) {
        return todos.stream()
                    .filter(todo -> todo.getId() == id )
                    .findAny();
    }

    public List<Todo> findByUsername(final String username) {
        System.out.println("call  findByUsername : " + username);
        return todos.stream().filter(todo -> todo.getUsername().equals(username)).collect(Collectors.toList());
    }

    public void clearData() {
        todos.clear();
    }
}
