package org.springframework.samples.petclinic.todo;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.hello.Member;
import org.springframework.samples.petclinic.hello.MemberForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


	@GetMapping("/hello/todo/{name}")
	@ApiOperation("TODO")
	public String list(Model model, @PathVariable String name) {

		List<Todo> todosByUser = todoService.getTodosByUser(name);
		model.addAttribute("todosByUser", todosByUser);
		return "members/todoList";
	}


	@GetMapping("/hello/todo/members/new")
	@ApiOperation("todo_get")
	public String createForm() {
		return "members/createTodoForm";
	}

	@PostMapping("/hello/todo/members/new")
	@ApiOperation("todo_post")
	public String create(MemberForm form) {

		Todo todo = new Todo();
		todo.setUsername(form.getName());
		todoService.saveTodo(todo);

		return "redirect:/hello/todo/"+todo.getUsername();
	}


}

