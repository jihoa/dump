package org.springframework.samples.petclinic.sample;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/todo")
public class TodoController {
    final TodoService todoService ;
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Multiaction 기능.
    @GetMapping
    public ModelAndView getTodoAll() {
        ModelAndView modelAndView = new ModelAndView();
        List<Todo> todoList = todoService.findAll();

        modelAndView.addObject("todoList", todoList);
        modelAndView.setViewName("thymeleaf/TodoList");

        return modelAndView;
    }

    // ant 패턴을 이용한 매칭. 문자 +'-'+ 숫자
    @GetMapping("/{username:[\\w]+[-][\\d]+}")//
    public ModelAndView getTodoByUsername(@PathVariable(name = "username", required = false) final String username) {
        System.out.println("getTodoByUsername start");
        ModelAndView modelAndView = new ModelAndView();
        List<Todo> todoList = todoService.getTodoByUsername(username);
        System.out.println(todoList.size() + ": " + username );
        modelAndView.addObject("todoList", todoList);
        modelAndView.setViewName("thymeleaf/TodoList");

        return modelAndView;
    }

    @GetMapping("/{id:[\\d]+}")
    public ModelAndView getTodoById(@PathVariable(name = "id") final long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Todo> todo = todoService.getTodoById(id);

        modelAndView.addObject("todo", todo.orElseThrow(() -> new NoSuchElementException("Not Found.")) );
        modelAndView.setViewName("thymeleaf/Todo");

        return modelAndView;
    }


}
