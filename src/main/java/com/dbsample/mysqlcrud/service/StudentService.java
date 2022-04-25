package com.dbsample.mysqlcrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbsample.mysqlcrud.entity.Message;
import com.dbsample.mysqlcrud.entity.Student;
import com.dbsample.mysqlcrud.errors.ErrorMessageCodes;
import com.dbsample.mysqlcrud.exception.RequestException;
import com.dbsample.mysqlcrud.repository.StudentRepository;
import com.dbsample.mysqlcrud.validation.StudentValidation;

@Service
public class StudentService {
	@Autowired
	private StudentRepository repository;

	@Autowired
	private StudentValidation studentValidator;

	public Student saveStudent(Student student) throws RequestException {
		String errorMessages = studentValidator.validateStudent(student);

		if (errorMessages.length() == 0) {
			try {
				repository.save(student);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RequestException studentRequestException = new RequestException(errorMessages);
			throw studentRequestException;
		}
		return student;
	}

	public List<Student> saveStudents(List<Student> students) {
		return repository.saveAll(students);
	}

	public List<Student> getStudents() {
		return repository.findAll();
	}

	public Student getStudentById(int id) {
		Student student = null;
		try {
			student = repository.findById(id).orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (student == null) {
			RequestException studentRequestException = new RequestException(ErrorMessageCodes.ID_DNE);
			throw studentRequestException;
		}
		return student;
	}

	public Student getStudentByName(String name) {
		Student student = null;
		try {
			student = repository.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (student == null) {
			RequestException studentRequestException = new RequestException(ErrorMessageCodes.NAME_DNE);
			throw studentRequestException;
		}
		return student;
	}

	public Student deleteStudent(int id) {
		Student s = repository.findById(id).orElse(null);
		if(s == null) {
			RequestException studentRequestException = new RequestException(ErrorMessageCodes.ID_DNE);
			throw studentRequestException;
		}
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public Student updateStudent(Student newStudent) {
		Student student = repository.findById(newStudent.getId()).orElse(null);
		if (student == null) {
			RequestException studentRequestException = new RequestException(ErrorMessageCodes.ID_DNE);
			throw studentRequestException;
		}

		student.setName(newStudent.getName());
		student.setGrade(newStudent.getGrade());
		student.setEmail(newStudent.getEmail());
		student.setPhone(newStudent.getPhone());
		student.setAddress(newStudent.getAddress());
		student.setMessages(newStudent.getMessages());
		String errorMessages = studentValidator.validateStudent(student);
		if (errorMessages.length() == 0) {
			repository.save(student);
		} else {
			RequestException studentRequestException = new RequestException(errorMessages);
			throw studentRequestException;
		}
		return student;
	}

	public Student saveMessage(int id, Message message) {
		Student student = getStudentById(id);
		if(message == null) {
			RequestException messageRequestException = new RequestException(ErrorMessageCodes.MESSAGE_IS_BLANK);
			throw messageRequestException;
		}
		student.getMessages().add(message);
		return repository.save(student);
	}

}
