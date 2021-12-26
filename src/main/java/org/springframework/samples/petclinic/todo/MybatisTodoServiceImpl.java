package org.springframework.samples.petclinic.todo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MybatisTodoServiceImpl implements TodoService{

	@Autowired
	TodoMapper todoMapper;

	@Override
	public List<Todo> getTodosByUser(String user) {
		return todoMapper.getTodosByUser(user);
	}

}
