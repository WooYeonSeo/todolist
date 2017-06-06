package kr.or.connect.todo.api;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.domain.TodoItem;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos") // api 경로 설정
public class TodoController {
	private final TodoService service;
	private final Logger log = LoggerFactory.getLogger(TodoController.class); // org.slf4j

	// 앞에서 작성한 BookService를 주입받도록 사용한다.
	@Autowired
	public TodoController(TodoService service) {
		this.service = service;
	}

	@GetMapping
	Collection<TodoItem> readList() {
		return service.findAll();
	}
	
	@GetMapping("/active")
	Collection<TodoItem> readActive() {
		log.info("active Item ");
		return service.loadActive();
	}

	@GetMapping("/{id}") // id 로 받게 한다
	TodoItem read(@PathVariable Integer id) {
		System.out.println("SEARCH BY ID");
		return service.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	TodoItem create(@RequestBody TodoItem todoItem) {
		// return service.create(book);
		System.out.println("created");
		TodoItem newTodoItem = service.create(todoItem);
		log.info("book created : {}", newTodoItem);
		return newTodoItem;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void update(@PathVariable Integer id, @RequestBody TodoItem todoItem) {
		System.out.println("UPDATE BY ID");	
		todoItem.setId(id);

		service.update(todoItem);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable Integer id) {
		log.info("delete item id : {}", id);
		service.delete(id);
	}
	
	@GetMapping("/count")
	int countTodo() {
		log.info("count Item ");
		return service.CountUncompleted();
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete() {
		service.deleteCompleted();
	}

}
