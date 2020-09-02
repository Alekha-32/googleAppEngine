package org.alekha.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

	private static List<Todo> todos = new ArrayList<Todo>();

	static {
		todos.add(new Todo(1, "Alekha", "Learn Spring MVC", new Date(), false));
		todos.add(new Todo(2, "Alekha", "Learn Struts", new Date(), false));
		todos.add(new Todo(3, "Alekha", "Learn Hibernate", new Date(), false));
	}

	public List<Todo> retrieveTodos(String name) {
		List<Todo> filteredTodos = new ArrayList<Todo>();
		for (Todo todo : todos) {
			if (todo.getName().equals(name))

				filteredTodos.add(todo);
		}
		return filteredTodos;

	}

	public Todo retrieveTodo(int id) {
		for (Todo todo : todos) {
			if (todo.getId() == id)
				return todo;
		}
		return null;
	}

	public void updateTodo(Todo todo) {
		for (Todo oldtodo : todos) {
			if (oldtodo.getId() == todo.getId()) {
				oldtodo = todo;
			}
		}
	}
	public void addTodo(Todo task) {
		task.setName("Alekha");
		task.setId(todos.size() + 1);
		todos.add(task);
	}

	public void deleteTodo(int id) {
		Iterator<Todo> iterator = todos.iterator();
		while (iterator.hasNext()) {
			Todo todo = iterator.next();
			if (todo.getId() == id) {
				iterator.remove();
			}
		}
	}
}