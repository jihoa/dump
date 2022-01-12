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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.swagger2.mappers.ModelMapper;


@Controller
@Api("TodoCon")
public class TodoController {
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

	@GetMapping("/hello/todo/members")
	@ApiOperation("HELLO_MEMBERS")
	public String list(Model model) {
		List<Todo> todoList = todoService.findAll();
		model.addAttribute("todoByUser", todoList);
		return "members/todoList";
	}


	@ApiOperation(value = "TODO", notes="GET 통한 조회 화면")
	@GetMapping("/hello/todo/{name}")
	public String list(Model model, @PathVariable String name) {

		List<Todo> todosByUser = todoService.getTodosByUser(name);
		model.addAttribute("todosByUser", todosByUser);
		return "members/todoList";
	}


	@ApiOperation(value = "TODO_GET", notes="input을 통한 생성 화면")
	@GetMapping("/hello/todo/members/new")
	public String createForm() {
		return "members/createTodoForm";
	}


	@ApiOperation(value="TODO_POST", notes="input을 통해 포스트 저장")
	@PostMapping("/hello/todo/members/new")
	public String create(MemberForm form) {

		Todo todo = new Todo();
		todo.setUsername(form.getName());
		todoService.saveTodo(todo);

		return "redirect:/hello/todo/"+todo.getUsername();
	}



//	@ApiOperation(value = "delete todo", notes = "투두 삭제")
//	@GetMapping("/hello/todo/deleteTodo")
//	public String deleteTodo(@RequestParam long id) {
//		todoService.deleteTodo(id);
//
//		return "redirect:/todoList";
//	}

//	@ApiOperation(value = "goto update form", notes = "투두 수정 화면")
//	@GetMapping("/updateTodo")
//	public String showUpdateTodoPage(@RequestParam long id, ModelMap model) {
//		Todo todo = todoService.getTodoById(id).get();
////        RequestDto requestDto = new RequestDto();
////        requestDto.setUsername(todo.getUsername() );
////        requestDto.setDescription(todo.getDescription() );
////        requestDto.setTargetDate(todo.getTargetDate() );
//
//		model.put("requestDto", todo.toDto() );
//
//		return "Todo";
//	}
//
//	@ApiOperation(value = "update todo", notes = "투두 수정")
//	@PostMapping("/updateTodo")
//	public String updateTodo(ModelMap model, @Valid RequestDto requestDto, BindingResult result) {
//		if (result.hasErrors()) {
//			return "Todo";
//		}
//		requestDto.setUsername(getLoggedInUserName(model));
//		// modelmapper를 이용한 DTO -> Model 객체 변환
//		Todo todo = userMapper.map(requestDto, Todo.class);
//		todoService.updateTodo(todo);
//
//		return "redirect:/todoList";
//	}




}

