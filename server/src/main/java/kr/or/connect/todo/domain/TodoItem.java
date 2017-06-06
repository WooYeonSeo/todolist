package kr.or.connect.todo.domain;

import java.util.Date;

public class TodoItem {
	private Integer id;
	private String todo;
	private Integer completed;
	private Date date;
	//private Integ
	

	public TodoItem(){
	}

	public TodoItem( String todo, Integer completed, Date date) {
		this.todo = todo;
		this.completed = completed;
		this.date = date;
	}

	public TodoItem( Integer id, String todo, Integer completed, Date date) {
		this(todo, completed, date);
		this.id = id;
	}

	// getter setter .. 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todoItem) {
		this.todo = todoItem;
	}

	public Integer getCompleted() {
		return completed;
	}

	public void setCompleted(Integer unitCompleted) {
		this.completed = unitCompleted;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
