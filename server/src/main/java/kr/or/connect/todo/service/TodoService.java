package kr.or.connect.todo.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Service;

import kr.or.connect.todo.domain.TodoItem;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoService {

	// private ConcurrentMap<Integer, TodoItem> repo = new
	// ConcurrentHashMap<>(); // 여기에
	// private AtomicInteger maxId = new AtomicInteger(0); // 자동으로 id 증가시켜 준다.

	private TodoDao dao;

	public TodoService(TodoDao dao) {
		this.dao = dao;
	}

	public TodoItem findById(Integer id) {
		return dao.selectById(id);
	}


	public Collection<TodoItem> findAll() {
		return dao.selectAll();
	}

	public TodoItem create(TodoItem todoItem) {
		// todoItem.setDate(new Date(date.getTime()));
		Date date = new Date();
		todoItem.setDate(new Date(date.getTime()));
		Integer id = dao.insert(todoItem);
		todoItem.setId(id);

		return todoItem;
	}

	public boolean update(TodoItem todoItem) {
		System.out.println("service.updeate");
		if (todoItem.getCompleted() == 0)
			todoItem.setCompleted(1);
		else
			todoItem.setCompleted(0);

		int affected = dao.update(todoItem);
		return affected == 1;
	}

	public boolean delete(Integer id) {
		int affected = dao.deleteById(id);
		return affected == 1;
	}
	public int CountUncompleted() {
		return dao.countTodo();
	}
	
	public Collection<TodoItem> loadActive() {
		return dao.selectActive();
	}
	
	public boolean deleteCompleted() {
		int affected = dao.deleteCompleted();
		return affected == 1;
	}
	
}
