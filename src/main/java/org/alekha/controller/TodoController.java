package org.alekha.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.alekha.dao.Todo;
import org.alekha.dao.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@SessionAttributes("name")
public class TodoController {

	@Autowired
	TodoService service;

	@GetMapping("/list")
	public ResponseEntity<List<Todo>> getCompanyList() {
		return new ResponseEntity<List<Todo>>(service.retrieveTodos("Alekha"), HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Todo> saveOrUpdateCompany(@RequestBody Todo task) {
		service.addTodo(task);
		return new ResponseEntity<Todo>(task, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Todo> update(@RequestBody Todo todo) {
		todo.setName("Alekha");
		service.updateTodo(todo);
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteCompany(@PathVariable Integer id) {
		service.deleteTodo(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
