package org.springframework.samples.petclinic.todo;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.swagger2.mappers.ModelMapper;


@Controller
@Api("TodoCon")
public class TodoController {

	@Autowired
	ModelMapper userMapper;

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}


	@ApiOperation(value = "REST APT SAMPLE", notes = "API 명세 샘플")
	@GetMapping(value = "/todoListApi/{name}")
//, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<Todo> getTodosApi(@PathVariable String name) {
		//String name = "James";
		List<Todo> actualList = todoService.getTodosByUser(name);
		return actualList;
	}


//	@ApiOperation(value = "select todoList", notes = "투두 목록 조회")
//	@GetMapping(value = "/todoList", produces = "text/html;charset=UTF-8")
//	public ModelAndView getTodos(ModelMap model) {
//		ModelAndView modelAndView = new ModelAndView("TodoList");
//		String name = "admin";
//		List<Todo> actualList = todoService.getTodosByUser(name);
//
//		model.put("todoList", actualList);
//		modelAndView.addObject("todoList", actualList);
//		return modelAndView;
//	}

	@ApiOperation(value = "add todo", notes = "투두 저장")
	@PostMapping("/addTodo")
	@ResponseBody

	public ResponseEntity<String> addTodo(ModelMap model, @Valid RequestDto requestDto, BindingResult result) {

//		if (result.hasErrors()) {
//			return "Todo";
//		}

		//requestDto.setUsername(getLoggedInUserName(model));
		//requestDto.setUsername("yhkim");
		todoService.saveTodo(requestDto.toEntity());

		return ResponseEntity.ok("SAVE COMPLETE");
	}


}

