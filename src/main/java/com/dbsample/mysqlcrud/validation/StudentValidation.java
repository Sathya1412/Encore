package com.dbsample.mysqlcrud.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

import com.dbsample.mysqlcrud.entity.Student;

@Component
public class StudentValidation {

	public String validateStudent(Student s) {
		StringBuffer errorMessages = new StringBuffer();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Student>> violations = validator.validate(s);
		for (ConstraintViolation<Student> violation : violations) {
			errorMessages.append(violation.getMessage());
		}
		return errorMessages.toString();				
	}

}
