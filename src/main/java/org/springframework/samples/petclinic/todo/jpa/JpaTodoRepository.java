package org.springframework.samples.petclinic.todo.jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.samples.petclinic.todo.Todo;
import org.springframework.samples.petclinic.todo.TodoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Repository
@Transactional
public class JpaTodoRepository implements TodoRepository {

    private final EntityManager entityManager;

    public JpaTodoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Todo> findAll() {
        StringBuilder sql = new StringBuilder(1024);
        sql.append("select t from TODO t");

        return entityManager.createQuery(String.valueOf(sql), Todo.class).getResultList();
    }

    @Override
    public List<Todo> findByUsername(String username) {
        StringBuilder sql = new StringBuilder(1024);
        sql.append("select t from TODO t where t.username = :username");
        List<Todo> result = entityManager.createQuery(String.valueOf(sql), Todo.class)
                .setParameter("username", username)
                .getResultList();

        return result;
    }

    @Override
    public Optional<Todo> findById(long id) {
        Todo todo = entityManager.find(Todo.class, id);
        return Optional.ofNullable(todo);
    }

    @Override
    public Todo save(Todo todo) {
        entityManager.persist(todo);
        return todo;
    }

    @Override
    public void delete(Todo todo) {
        StringBuilder sql = new StringBuilder(1024);
        sql.append("delete from TODO where id = :id");
        entityManager.createQuery(String.valueOf(sql), Todo.class)
                .setParameter("id", todo.getId() );
    }

    @Override
    public void update(Todo todo) {
        StringBuilder sql = new StringBuilder(1024);
        sql.append("update TODO set description = :description and taretDate = :targetDate where id = :id");
        entityManager.createQuery(String.valueOf(sql), Todo.class)
                .setParameter("description", todo.getDescription() )
                .setParameter("targetDate", todo.getTargetDate())
                .setParameter("id", todo.getId() );
    }
}
