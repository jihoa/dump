package org.springframework.samples.petclinic.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class BasicTodoRepository implements TodoRepository {
	// ConcurrentHashMap, AtomicLong은 동시성 문제 해결을 위해 사용
	// HashMap, long 은 Thread-Safe 하지 않아 실무에서는 사용을 금함.

	private static Map<Long, Todo> baseData = new ConcurrentHashMap<>();
	private static AtomicLong sequence = new AtomicLong(0);

	@Override
	public List<Todo> findAll() {
		return new ArrayList<>(baseData.values());
	}

	@Override
	public Todo save(Todo todo) {
		todo.setId(sequence.incrementAndGet());
		baseData.put(todo.getId(), todo);
		return todo;
	}

	@Override
	public List<Todo> findByUsername(String username) {
		return baseData.values().stream()
			.filter(todo -> todo.getUsername().equals(username))
			.collect(Collectors.toList());
	}

	@Override
	public Optional<Todo> findById(long id) {
		return Optional.ofNullable(baseData.get(id));
	}



	@Override
	public void delete(Todo todo) {
		baseData.remove(todo.getId());
	}

	@Override
	public void update(Todo todo) {

	}

	public void clearData() {
		baseData.clear();
	}
}

