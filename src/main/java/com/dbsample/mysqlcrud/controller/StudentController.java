package com.dbsample.mysqlcrud.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.dbsample.mysqlcrud.dto.PostRequest;
import com.dbsample.mysqlcrud.entity.Message;
import com.dbsample.mysqlcrud.entity.Student;
import com.dbsample.mysqlcrud.errors.ErrorMessageCodes;
import com.dbsample.mysqlcrud.exception.RequestException;
import com.dbsample.mysqlcrud.service.MessageService;
import com.dbsample.mysqlcrud.service.StudentService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class StudentController {
	@Autowired
	private StudentService service;

	@Autowired
	private MessageService mService;

	@GetMapping("/")
	public MappingJacksonValue homePage(
			@RequestHeader(name = "Accept-Language", required = false) final Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("message", locale);
		String hello = bundle.getString("HelloMessage");
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(hello);
		return mappingJacksonValue;
	}

	@PostMapping("/addStudent")
	public MappingJacksonValue addStudent(@RequestBody Student student) {
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAll();
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);
		Student s = service.saveStudent(student);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(s);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

	@GetMapping(value = "/students", produces = { "application/hal+json" })
	public MappingJacksonValue findAllStudents() {
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("id", "grade",
				"phone", "address", "messages");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);

		List<Student> students = service.getStudents();
		students.forEach(student -> {
			Link link = WebMvcLinkBuilder.linkTo(StudentController.class).slash("student").slash(student.getName())
					.withSelfRel();
			student.add(link);
		});
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(students);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

	@GetMapping(value = "/studentById/{id}", produces = { "application/hal+json" })
	public MappingJacksonValue findStudentById(@PathVariable int id) {
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAll();
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(service.getStudentById(id));
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

	@GetMapping(value = "/student/{name}", produces = { "application/hal+json" })
	public MappingJacksonValue findStudentByName(@PathVariable String name) {
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("id", "grade",
				"phone", "address");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(service.getStudentByName(name));
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

	@PutMapping("/update")
	public MappingJacksonValue updateStudent(@RequestBody Student student) {
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAll();
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(service.updateStudent(student));
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

	@DeleteMapping("/delete/{id}")
	public MappingJacksonValue deleteStudent(@PathVariable int id) {
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAll();
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
				"Student Deleted: " + service.deleteStudent(id));
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

	@GetMapping("/student/{id}/viewMessages")
	public MappingJacksonValue viewMessages(@PathVariable int id) {
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("id", "name",
				"gender", "grade", "email", "phone", "address");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(service.getStudentById(id));
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

	@GetMapping("/student/{id}/viewMessageById/{mid}")
	public MappingJacksonValue viewMessagesById(@PathVariable int id, @PathVariable int mid) {
		if (!isStudentsMessage(id, mid)) {
			RequestException messageRequestException = new RequestException(ErrorMessageCodes.STUDENT_MESSAGE_MISMATCH);
			throw messageRequestException;
		}
		return new MappingJacksonValue(mService.getMessageById(mid));
	}

	@DeleteMapping("/student/{id}/deleteMessage/{mid}")
	public MappingJacksonValue deleteMessage(@PathVariable int id, @PathVariable int mid) {
//		if (!isStudentsMessage(id, mid)) {
//			RequestException messageRequestException = new RequestException(ErrorMessageCodes.STUDENT_MESSAGE_MISMATCH);
//			throw messageRequestException;
//		}
		return new MappingJacksonValue(mService.deleteMessage(mid));
	}

	@PutMapping("/student/{id}/postMessage")
	public MappingJacksonValue postMessage(@PathVariable int id, @RequestBody PostRequest request) {
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("id", "gender",
				"grade", "email", "phone", "address");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("studentFilter", simpleBeanPropertyFilter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
				service.saveMessage(id, request.getMessage()));
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

	private boolean isStudentsMessage(int id, int mid) {
		Student s = service.getStudentById(id);
		List<Message> list = s.getMessages();
		Message m = mService.getMessageById(mid);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(m)) {
				return true;
			}
		}
		return false;
	}

}
